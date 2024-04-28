package jpa;

import applicationManagement.domain.Candidate;
import jakarta.persistence.*;
import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaApplicationRepository implements ApplicationRepository {
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

    @Override
    public <S extends Application> S save(S entity) {
        if(correctApplication(entity)){
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

    private boolean correctApplication(Application application) {
        boolean correct = true;
//        if (jobOpening.title() == null || jobOpening.title().isEmpty()) {
//            correct = false;
//        }
//        if (jobOpening.contractType() == null) {
//            correct = false;
//        }
//        if (jobOpening.mode() == null) {
//            correct = false;
//        }
//        if (jobOpening.address() == null) {
//            correct = false;
//        }
//        if (jobOpening.company() == null) {
//            correct = false;
//        }
//        if (jobOpening.numberOfVacancies() <= 0) {
//            correct = false;
//        }
//        if (jobOpening.description() == null || jobOpening.description().isEmpty()) {
//            correct = false;
//        }
//        //We are letting "requirements" be null because Customer Manager can choose later
//        if (jobOpening.state() == null || jobOpening.state() != RecruitmentState.APPLICATION) {
//            correct = false;
//        }
        return correct;
    }

    public List<Application> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Application e");
        List<Application> list = query.getResultList();
        return list;
    }

    @Override
    public Optional<Application> ofIdentity(String id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Application e WHERE e.id = :id");
        query.setParameter("id", id);
        Application application = (Application) query.getSingleResult();
        return Optional.of(application);
    }

    @Override
    public List<Application> ofCandidate(Candidate candidate) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Application e WHERE e.candidate = :candidate");
        query.setParameter("candidate", candidate);
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public void delete(Application entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(entity);
        tx.commit();
        em.close();
    }

    @Override
    public void deleteOfIdentity(String id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("DELETE FROM Application e WHERE e.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        tx.commit();
        em.close();

    }

    @Override
    public long count() {
        Query query = getEntityManager().createQuery(
                "SELECT COUNT(e) FROM Application e");
        return (long) query.getSingleResult();
    }


}
