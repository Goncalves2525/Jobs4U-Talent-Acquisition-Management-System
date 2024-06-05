package notificationManagement.application;

import infrastructure.persistance.PersistenceContext;
import notificationManagement.domain.Notification;
import notificationManagement.repositories.NotificationRepository;

import java.util.ArrayList;

public class NotificationCustomerManagerController {

    NotificationRepository repo = PersistenceContext.repositories().notifications();

    public ArrayList<Notification> findJobReferenceNotifications(String jobReference) { return repo.findAllByJobReference(jobReference); }

    public Notification addNewNotification (Notification notification) { return repo.save(notification); }
}
