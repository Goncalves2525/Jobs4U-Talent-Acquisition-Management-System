package jpa;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import jakarta.persistence.*;
import notificationManagement.domain.EmailNotificationStatus;
import notificationManagement.domain.Notification;
import notificationManagement.repositories.NotificationRepository;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaNotificationRepository implements NotificationRepository {

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }

    @Override
    public <S extends Notification> S save(S entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        em.close();
        return entity;
    }

    public void update(Notification entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(entity);
        tx.commit();
        em.close();
    }

    @Override
    public Iterable<Notification> findAll() {
        System.out.println("Not implemented yet.");
        return null;
    }

    @Override
    public Optional<Notification> ofIdentity(Long id) {
        System.out.println("Not implemented yet.");
        return Optional.empty();
    }

    @Override
    public boolean containsOfIdentity(Long id) {
        System.out.println("Not implemented yet.");
        return NotificationRepository.super.containsOfIdentity(id);
    }

    @Override
    public boolean contains(Notification entity) {
        System.out.println("Not implemented yet.");
        return NotificationRepository.super.contains(entity);
    }

    @Override
    public void delete(Notification entity) {
        System.out.println("Not implemented yet.");
    }

    @Override
    public void deleteOfIdentity(Long entityId) {
        System.out.println("Not implemented yet.");
    }

    @Override
    public long count() {
        Query query = getEntityManager().createQuery(
                "SELECT COUNT(e) FROM Notification e");
        return (long) query.getSingleResult();
    }

    @Override
    public long size() {
        System.out.println("Not implemented yet.");
        return NotificationRepository.super.size();
    }

    @Override
    public void remove(Notification entity) {
        ConsoleUtils.showMessageColor("It is not possible to remove a Notification.", AnsiColor.RED);
    }

    @Override
    public void removeOfIdentity(Long entityId) {
        ConsoleUtils.showMessageColor("It is not possible to remove a Notification.", AnsiColor.RED);
    }

    @Override
    public ArrayList<Notification> findAllByJobReference(String jobReference) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Notification e WHERE e.jobOpeningReference LIKE :jobReference");
        query.setParameter("jobReference", jobReference);
        List notifications = query.getResultList();
        if(notifications.isEmpty()) {
            return new ArrayList<>(); // list with no objects
        }
        return new ArrayList<Notification>(notifications);
    }

    @Override
    public ArrayList<Notification> findAllReadyToEmail() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM Notification e WHERE e.emailNotificationStatus = :ready");
        query.setParameter("ready", EmailNotificationStatus.READY);
        List notifications = query.getResultList();
        if(notifications.isEmpty()) {
            return new ArrayList<>(); // list with no objects
        }
        return new ArrayList<Notification>(notifications);
    }

    @Override
    public ArrayList<Notification> findAllNewNotificationsOfCandidate(String candidateEmail, Role role) {
        if (role.showCandidateAppAccess()) {
            Query query = getEntityManager().createQuery(
                    "SELECT e FROM Notification e WHERE e.notifyTo LIKE :notifyToCandidate and e.roleTo = :candidateRole");
            query.setParameter("notifyToCandidate", candidateEmail);
            query.setParameter("candidateRole", role);
            List notifications = query.getResultList();
            if (notifications.isEmpty()) {
                return new ArrayList<>(); // list with no objects
            }
            return new ArrayList<Notification>(notifications);
        }
        return new ArrayList<>(); // list with no objects
    }

    @Override
    public ArrayList<Notification> findAllNewNotificationsOfCustomer(String customerEmail, Role role) {
        if (role.showCustomerAppAccess()) {
            Query query = getEntityManager().createQuery(
                    "SELECT e FROM Notification e WHERE e.notifyTo LIKE :notifyToCustomer and e.roleTo = :customerRole");
            query.setParameter("notifyToCustomer", customerEmail);
            query.setParameter("customerRole", role);
            List notifications = query.getResultList();
            if (notifications.isEmpty()) {
                return new ArrayList<>(); // list with no objects
            }
            return new ArrayList<Notification>(notifications);
        }
        return new ArrayList<>(); // list with no objects
    }
}
