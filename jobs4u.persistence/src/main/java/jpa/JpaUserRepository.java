package jpa;

import authzManagement.domain.Email;
import authzManagement.domain.Role;
import authzManagement.domain.User;
import authzManagement.repositories.UserRepository;
import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

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
    public boolean exists(EmailAddress email) {
        Iterable<User> users = findAll();
        for (User user : users) {
            if (user.identity().equals(email)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String createUser(String name, String email) {
        return "";
    }

    @Override
    public boolean authenticateUser(String email, String password, Role validRole) {
        return false;
    }

    @Override
    public <S extends User> S save(S entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    @Override
    public Iterable<User> findAll() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u");
        List<User> users = query.getResultList();
        em.close();
        return users;
    }

    @Override
    public Optional<User> ofIdentity(EmailAddress id) {
        return Optional.empty();
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteOfIdentity(EmailAddress entityId) {

    }

    @Override
    public long count() {
        return 0;
    }
}
