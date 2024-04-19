package jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.repositories.CustomerRepository;

import java.util.Optional;

public class JpaCustomerRepository implements CustomerRepository {

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

    @Override
    public <S extends Customer> S save(S entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    @Override
    public Iterable<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> ofIdentity(CompanyCode id) {
        return Optional.empty();
    }

    @Override
    public void delete(Customer entity) {

    }

    @Override
    public void deleteOfIdentity(CompanyCode entityId) {

    }

    @Override
    public long count() {
        return 0;
    }
}
