package notificationManagement.repositories;

import appUserManagement.domain.Role;
import eapli.framework.domain.repositories.DomainRepository;
import notificationManagement.domain.Notification;

import java.util.ArrayList;

public interface NotificationRepository extends DomainRepository<Long, Notification> {

    ArrayList<Notification> findAllByJobReference(String jobReference);

    ArrayList<Notification> findAllReadyToEmail();

    ArrayList<Notification> findAllNewNotificationsOfCandidate(String candidateEmail, Role role);

    ArrayList<Notification> findAllNewNotificationsOfCustomer(String customerEmail, Role role);
}
