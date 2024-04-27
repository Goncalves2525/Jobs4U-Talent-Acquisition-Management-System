package jpa;

import authzManagement.domain.*;
import authzManagement.repositories.UserRepository;
import console.ConsoleUtils;
import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import textformat.AnsiColor;

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
        Email userEmail = new Email(email);
        Password userPwd = new Password();
        String userPwdGenerated = userPwd.generatePassword();
        AppUser appUser = new AppUser(userEmail, userPwd, role);
        save(appUser);
        return Optional.of(userPwdGenerated);
    }

    @Override
    public Optional<String> createAppUser(String email, Role role, Role creatorRole) {
        Email userEmail = new Email(email);
        Password userPwd = new Password();
        String userPwdGenerated = userPwd.generatePassword();
        if(role.showBackofficeAppAccess() && creatorRole.equals(Role.ADMIN)){
            AppUser appUser = new AppUser(userEmail, userPwd, role);
            save(appUser);
            return Optional.of(userPwdGenerated);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> authenticate(String email, String password) {
        // Create a valid session user in memory:
        Optional<AppUser> sessionUser = createSessionUser(email, password);
        if (!sessionUser.isPresent()) {
            ConsoleUtils.showMessageColor("Bad user or password!", AnsiColor.RED);
            return Optional.empty();
        }

        // Query to get a user with matching email and password:
        String select = "SELECT tb.token FROM AppUser tb"
                + " WHERE tb.email.email LIKE '" + email
                + "' AND tb.password.value LIKE '" + password + "'";
        EntityManager em = getEntityManager();
        List list = em.createQuery(select).getResultList();
        em.close();
        if (list.isEmpty()) {
            ConsoleUtils.showMessageColor("Invalid user or password!", AnsiColor.RED);
            return Optional.empty();
        }

        // Validate if there is an existing session:
        if (list.get(0) != null) {
            ConsoleUtils.showMessageColor("Invalid session! User already logged in.", AnsiColor.RED);
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
            if(sessionUser.get().getRole().equals(roleRequired)){
                return true;
            }
        }
        return false;
    }

    public Role getValidBackofficeRole(String sessionToken) {
        Optional<AppUser> sessionUser = withToken(sessionToken);
        if (sessionUser.isPresent()) {
            if(sessionUser.get().getRole().showBackofficeAppAccess()){
                return sessionUser.get().getRole();
            }
        }
        return Role.DEFAULT;
    }

    private Optional<AppUser> createSessionUser(String email, String password) {
        EmailAddress userEmail = new Email(email);
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
    public boolean exists(EmailAddress email) {
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
    public Optional<AppUser> ofIdentity(EmailAddress id) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.email.email LIKE '" + id.toString() + "'" );
        AppUser user = (AppUser) query.getResultList().get(0);
        em.close();
        return Optional.of(user);
    }

    private Optional<AppUser> withToken(String sessionToken){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM AppUser u WHERE u.token.value LIKE '" + sessionToken + "'" );
        if (query.getResultList().isEmpty()) {
            return Optional.empty();
        }
        AppUser user = (AppUser) query.getResultList().get(0);
        em.close();
        return Optional.of(user);
    }

    @Override
    public void delete(AppUser entity) {
        // TODO
        ConsoleUtils.showMessageColor("To be implemented", AnsiColor.RED);
    }

    @Override
    public void deleteOfIdentity(EmailAddress entityId) {
        // TODO
        ConsoleUtils.showMessageColor("To be implemented", AnsiColor.RED);
    }

    @Override
    public long count() {
        // TODO
        ConsoleUtils.showMessageColor("To be implemented", AnsiColor.RED);
        return 0;
    }
}
