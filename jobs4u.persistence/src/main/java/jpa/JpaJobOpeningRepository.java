package jpa;

import jakarta.persistence.*;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.List;
import java.util.Optional;

public class JpaJobOpeningRepository implements JobOpeningRepository {
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }


    @Override
    public <S extends JobOpening> S save(S entity) {
        if(correctJobOpening(entity)){
            EntityManager em = getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            em.close();

            return entity;
        }
        return null;
    }

    private boolean correctJobOpening(JobOpening jobOpening) {
        boolean correct = true;
        if (jobOpening.title() == null || jobOpening.title().isEmpty()) {
            correct = false;
        }
        if (jobOpening.contractType() == null) {
            correct = false;
        }
        if (jobOpening.mode() == null) {
            correct = false;
        }
        if (jobOpening.address() == null) {
            correct = false;
        }
        if (jobOpening.company() == null) {
            correct = false;
        }
        if (jobOpening.numberOfVacancies() <= 0) {
            correct = false;
        }
        if (jobOpening.description() == null || jobOpening.description().isEmpty()) {
            correct = false;
        }
        //We are letting "requirements" be null because Customer Manager can choose later
//        if (jobOpening.state() == null || jobOpening.state() != RecruitmentState.APPLICATION) {
//            correct = false;
//        }
        return correct;
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
    public JobOpening findByJobReference(String jobReference) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e WHERE e.jobReference = :jobReference");
        query.setParameter("jobReference", jobReference);
        JobOpening jobOpening = (JobOpening) query.getSingleResult();
        return jobOpening;
    }



    @Override
    public void delete(JobOpening entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(entity);
        tx.commit();
        em.close();
    }

    @Override
    public void deleteOfIdentity(String jobReference) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("DELETE FROM JobOpening e WHERE e.jobReference = :jobReference");
        query.setParameter("jobReference", jobReference);
        query.executeUpdate();
        tx.commit();
        em.close();

    }

    @Override
    public long count() {
        Query query = getEntityManager().createQuery(
                "SELECT COUNT(e) FROM JobOpening e");
        return (long) query.getSingleResult();
    }


    @Override
    public boolean update(JobOpening jobOpening) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(jobOpening);
        tx.commit();
        em.close();

        return true;
    }

    @Override
    public List<JobOpening> findAllActiveJobOpenings(Customer company) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e WHERE e.company = :company AND e.endDate is null");
        query.setParameter("company", company);
        List<JobOpening> list = query.getResultList();
        return list;
    }

    @Override
    public Iterable<JobOpening> findAllActiveJobOpeningsResultPhase() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e WHERE e.state = :state AND e.endDate is null");
        query.setParameter("state", RecruitmentState.RESULT);
        List<JobOpening> list = query.getResultList();
        return list;
    }

    @Override
    public List<JobOpening> findAllActiveJobOpenings() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e WHERE e.endDate is null");
        List<JobOpening> list = query.getResultList();
        return list;
    }

}
