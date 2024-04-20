package jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.repositories.CustomerRepository;

import java.util.List;
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
    public List<Customer> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Customer e");
        List<Customer> list = query.getResultList();
        return list;
    }

    @Override
    public Optional<Customer> ofIdentity(CompanyCode id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Customer e WHERE e.code = :id");
        query.setParameter("id", id);
        Customer customer = (Customer) query.getSingleResult();
        return Optional.of(customer);
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
