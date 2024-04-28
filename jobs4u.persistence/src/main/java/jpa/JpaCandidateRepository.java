package jpa;

import applicationManagement.domain.dto.CandidateDTO;
import jakarta.persistence.*;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;

import java.util.List;
import java.util.Optional;

public class JpaCandidateRepository implements CandidateRepository {
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

    public boolean createCandidate(CandidateDTO dto){
        Candidate candidate = new Candidate(dto.getEmail(),
                dto.getPhone(), dto.getName());
        save(candidate);
        return true;
    }

    @Override
    public <S extends Candidate> S save(S entity) {
        if(correctCandidate(entity)){
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

    private boolean correctCandidate(Candidate candidate) {
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

    public List<Candidate> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Candidate e");
        List<Candidate> list = query.getResultList();
        return list;
    }

    @Override
    public Optional<Candidate> ofIdentity(String id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Candidate e WHERE e.id = :id");
        query.setParameter("id", id);
        Candidate candidate = (Candidate) query.getSingleResult();
        return Optional.of(candidate);
    }

    @Override
    public void delete(Candidate entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(entity);
        tx.commit();
        em.close();
    }

    @Override
    public void deleteOfIdentity(String email) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("DELETE FROM Candidate e WHERE e.email = :email");
        query.setParameter("email", email);
        query.executeUpdate();
        tx.commit();
        em.close();

    }

    @Override
    public long count() {
        Query query = getEntityManager().createQuery(
                "SELECT COUNT(e) FROM Candidate e");
        return (long) query.getSingleResult();
    }


}
