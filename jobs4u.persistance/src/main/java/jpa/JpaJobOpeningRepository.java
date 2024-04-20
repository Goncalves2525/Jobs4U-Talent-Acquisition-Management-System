package jpa;

import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalRepository;
import jakarta.persistence.*;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.List;
import java.util.Optional;

import static org.eclipse.persistence.jpa.JpaHelper.getEntityManager;

public class JpaJobOpeningRepository implements JobOpeningRepository {
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }


    public JobOpening add(JobOpening jobOpening) {
        if (jobOpening == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(jobOpening);
        tx.commit();
        em.close();

        return jobOpening;
    }

    @Override
    public <S extends JobOpening> S save(S entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        em.close();

        return entity;
    }

    public List<JobOpening> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e");
        List<JobOpening> list = query.getResultList();
        return list;
    }

    @Override
    public Optional<JobOpening> ofIdentity(String id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e WHERE e.id = :id");
        query.setParameter("id", id);
        JobOpening jobOpening = (JobOpening) query.getSingleResult();
        return Optional.of(jobOpening);
    }

    @Override
    public void delete(JobOpening entity) {

    }

    @Override
    public void deleteOfIdentity(String entityId) {

    }

    @Override
    public long count() {
        return 0;
    }
}
