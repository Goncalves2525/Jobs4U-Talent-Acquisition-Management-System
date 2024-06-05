package notificationManagement.domain;

import appUserManagement.domain.Role;
import applicationManagement.domain.ApplicationStatus;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@Entity
public class Notification implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Column
    private EmailNotificationStatus emailNotificationStatus;

    @Getter
    @Column
    private Date emailNotificationDate; // date it has been sent or failed

    @Getter
    @Column
    private AppNotificationStatus appNotificationStatus;

    @Getter
    @Column
    private Date appNotificationDate; // date that notification has been published or updated

    @Getter
    @Column
    private String notifyTo; // Customer or Candidate email

    @Getter
    @Column
    private Role roleTo; // Customer or Candidate role

    @Getter
    @Column
    private ApplicationStatus applicationStatus; // Only for Candidate related objects

    @Getter
    @Column
    private String jobOpeningReference; // Stores Job Opening string reference

    protected Notification() {
        // for ORM
    }

    public Notification(String notifyTo, Role roleTo, ApplicationStatus applicationStatus, String jobOpeningReference) {
        this.emailNotificationStatus = EmailNotificationStatus.READY;
        this.emailNotificationDate = null;
        this.appNotificationStatus = AppNotificationStatus.PENDING;
        this.appNotificationDate = Date.from(Instant.now());
        this.notifyTo = notifyTo;
        this.roleTo = roleTo;
        if(roleTo.equals(Role.CANDIDATE)) {
            this.applicationStatus = applicationStatus;
        } else {
            this.applicationStatus = null;
        }
        this.jobOpeningReference = jobOpeningReference;
    }

    public boolean changeEmailNotificationStatus(EmailNotificationStatus newEmailNotificationStatus) {
        if(this.emailNotificationStatus.equals(newEmailNotificationStatus)){
            return false;
        }
        if (newEmailNotificationStatus.equals(EmailNotificationStatus.READY)) {
            this.emailNotificationStatus = newEmailNotificationStatus;
            return true;
        } else {
            this.emailNotificationStatus = newEmailNotificationStatus;
            this.emailNotificationDate = Date.from(Instant.now());
            return true;
        }
    }

    public boolean changeAppNotificationStatus(AppNotificationStatus newAppNotificationStatus) {
        if(this.appNotificationStatus.equals(newAppNotificationStatus)){
            return false;
        }
        if (newAppNotificationStatus.equals(AppNotificationStatus.READ)) {
            this.appNotificationStatus = newAppNotificationStatus;
            return true;
        } else {
            this.appNotificationStatus = newAppNotificationStatus;
            this.appNotificationDate = Date.from(Instant.now());
            return true;
        }
    }

    @Override
    public boolean sameAs(Object other) {
        // TODO: to be implemented
        return false;
    }

    @Override
    public Long identity() {
        // TODO: to be implemented
        return null;
    }
}
