package notificationManagement.application;

import infrastructure.persistance.PersistenceContext;
import notificationManagement.domain.Notification;
import notificationManagement.repositories.NotificationRepository;

import java.util.ArrayList;

public class NotificationCustomerManagerController {

    NotificationRepository repo;

    public NotificationCustomerManagerController(NotificationRepository notificationRepository){
        this.repo=notificationRepository;
    }

    public ArrayList<Notification> findJobReferenceNotifications(String jobReference) { return repo.findAllByJobReference(jobReference); }

    public Notification addNewNotification (Notification notification) { return repo.save(notification); }
}
