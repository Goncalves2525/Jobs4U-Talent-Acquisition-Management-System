package jpa;

import appUserManagement.domain.*;
import appUserManagement.domain.dto.AppUserDTO;
import appUserManagement.repositories.UserRepository;
import console.ConsoleUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository {

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

    @Override
    public Optional<String> createAppUser(String email, Role role) {
        Email userEmail = Email.valueOf(email);
        Password userPwd = new Password();
        String userPwdGenerated = userPwd.generatePassword();
        AppUser appUser = new AppUser(userEmail, userPwd, role);
        save(appUser);
        return Optional.of(userPwdGenerated);
    }

    @Override
    public Optional<String> createAppUser(String email, Role role, Role creatorRole) {
        Email userEmail = Email.valueOf(email);
        Password userPwd = new Password();
        String userPwdGenerated = userPwd.generatePassword();
        if (role.showBackofficeAppAccess() && creatorRole.equals(Role.ADMIN)) {
            AppUser appUser = new AppUser(userEmail, userPwd, role);
            save(appUser);
            return Optional.of(userPwdGenerated);
        }
        return Optional.empty();
    }

    @Override
    public boolean swapAbility(String email, Role managerRole) {
        if (managerRole.equals(Role.ADMIN)) {
            AppUser appUser = ofIdentity(Email.valueOf(email)).get();
            appUser.swapAbility();
            update(appUser);
            return true;
        }
        return false;
    }

    @Override
    public Optional<String> authenticate(String email, String password) {
        // Create a valid session user in memory:
        Optional<AppUser> sessionUser = createSessionUser(email, password);
        if (!sessionUser.isPresent()) {
            ConsoleUtils.showMessageColor("Bad user or password!", AnsiColor.RED);
            return Optional.empty();
        }

        // Query to get a user with matching email and password, while being enabled:
        String jpql = "SELECT tb.token FROM AppUser tb WHERE tb.email.email = :email AND tb.password.value = :password AND tb.ability = :ability";
        EntityManager em = getEntityManager();
        Query query = em.createQuery(jpql)
                .setParameter("email", email)
                .setParameter("password", password)
                .setParameter("ability", Ability.ENABLED);
        List tokens = query.getResultList();
        em.close();

        if (tokens.isEmpty()) {
            ConsoleUtils.showMessageColor("Invalid user or password!", AnsiColor.RED);
            return Optional.empty();
        }

        // Validate if there is an existing session:
        if (tokens.get(0) != null) {
            ConsoleUtils.showMessageColor("Invalid session! User already logged in.", AnsiColor.RED);
            return Optional.empty();
        }

        // Generate a session token:
        Token token = new Token();
        String sessionToken = token.generateToken(email);
        // add token to user
        sessionUser = ofIdentity(sessionUser.get().identity());
        sessionUser.get().addToken(token);
        update(sessionUser.get());
        return Optional.of(sessionToken);
    }

    public boolean authorized(String sessionToken, Role roleRequired) {
        Optional<AppUser> sessionUser = withToken(sessionToken);
        if (sessionUser.isPresent()) {
            if (sessionUser.get().getRole().equals(roleRequired)) {
                return true;
            }
        }
        return false;
    }

    public Role getValidBackofficeRole(String sessionToken) {
        Optional<AppUser> sessionUser = withToken(sessionToken);
        if (sessionUser.isPresent()) {
            if (sessionUser.get().getRole().showBackofficeAppAccess()) {
                return sessionUser.get().getRole();
            }
        }
        return Role.DEFAULT;
    }

    private Optional<AppUser> createSessionUser(String email, String password) {
        Email userEmail = Email.valueOf(email);
        Password userPassword = new Password();
        boolean valid = userPassword.createPassword(password);
        AppUser sessionUser = new AppUser(userEmail, userPassword, Role.DEFAULT);
        return Optional.of(sessionUser);
    }

    public boolean endSession(String sessionToken) {
        Token token = new Token();
        Optional<AppUser> sessionUser = withToken(sessionToken);
        if (sessionUser.isPresent()) {
            sessionUser.get().addToken(token);
            update(sessionUser.get());
            ConsoleUtils.showMessageColor("Logged out.", AnsiColor.GREEN);
            return true;
        }
        ConsoleUtils.showMessageColor("Log out failed! Please, contact admin.", AnsiColor.RED);
        return false;
    }

    @Override
    public boolean exists(Email email) {
        Iterable<AppUser> users = findAll();
        for (AppUser appUser : users) {
            if (appUser.identity().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <S extends AppUser> S save(S entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public <S extends AppUser> S update(S entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        entity = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    @Override
    public Iterable<AppUser> findAll() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM AppUser u");
        List<AppUser> users = query.getResultList();
        em.close();
        return users;
    }

    @Override
    public Optional<AppUser> ofIdentity(Email id) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.email.email LIKE '" + id.toString() + "'");
        AppUser user = (AppUser) query.getResultList().get(0);
        em.close();
        return Optional.of(user);
    }

    private Optional<AppUser> withToken(String sessionToken) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.token.value LIKE '" + sessionToken + "'");
        if (query.getResultList().isEmpty()) {
            return Optional.empty();
        }
        AppUser user = (AppUser) query.getResultList().get(0);
        em.close();
        return Optional.of(user);
    }

    public Optional<List<AppUserDTO>> buildListByRole(List<Role> requiredRoles, Role managerRole) {
        EntityManager em = getEntityManager();

        List<AppUser> appUserList = new ArrayList<>();
        for (Role role : requiredRoles) {

            // validates if a non-manager is trying to build a list with backoffice users
            if(role.showBackofficeAppAccess() && !managerRole.equals(Role.ADMIN)) {
                return Optional.empty();
            }

            // queries for users with role selected
            Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.role = :role");
            query.setParameter("role", role);
            appUserList.addAll(query.getResultList());
        }

        em.close();

        if (appUserList.isEmpty()) {
            return Optional.empty();
        }

        List<AppUserDTO> dtoList = new ArrayList<>();
        for (AppUser user : appUserList) {
            AppUserDTO userDto = new AppUserDTO(user.getEmail().toString(),
                    user.getRole(), user.getAbility());
            dtoList.add(userDto);
        }

        return Optional.of(dtoList);
    }

    @Override
    public void delete(AppUser entity) {
        ConsoleUtils.showMessageColor("It is not allowed to delete AppUsers.", AnsiColor.RED);
    }

    @Override
    public void deleteOfIdentity(Email entityId) {
        ConsoleUtils.showMessageColor("It is not allowed to delete AppUsers.", AnsiColor.RED);
    }

    @Override
    public long count() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT COUNT(u) FROM AppUser u");
        return query.getFirstResult();
    }
}
