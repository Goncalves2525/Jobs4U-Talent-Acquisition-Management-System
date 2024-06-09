package notificationManagement;

import appUserManagement.domain.Role;
import applicationManagement.domain.ApplicationStatus;
import notificationManagement.domain.EmailNotificationStatus;
import notificationManagement.domain.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.Assert.*;

public class NotificationTest {

    private Notification notification;

    @BeforeEach
    public void setUp() {
        String notifyTo = "test@example.com";
        Role roleTo = Role.CANDIDATE;
        ApplicationStatus applicationStatus = ApplicationStatus.PENDING;
        String jobOpeningReference = "JOB123";

        notification = new Notification(notifyTo, roleTo, applicationStatus, jobOpeningReference);
    }

    @Test
    public void testChangeEmailNotificationStatusToSameStatus() {
        // Initial status is READY
        assertFalse(notification.changeEmailNotificationStatus(EmailNotificationStatus.READY));
        assertEquals(EmailNotificationStatus.READY, notification.getEmailNotificationStatus());
        assertNull(notification.getEmailNotificationDate());
    }

    @Test
    public void testChangeEmailNotificationStatusToSent() {
        assertTrue(notification.changeEmailNotificationStatus(EmailNotificationStatus.SENT));
        assertEquals(EmailNotificationStatus.SENT, notification.getEmailNotificationStatus());
        assertNotNull(notification.getEmailNotificationDate());
    }

    @Test
    public void testChangeEmailNotificationStatusToFailed() {
        assertTrue(notification.changeEmailNotificationStatus(EmailNotificationStatus.FAILED));
        assertEquals(EmailNotificationStatus.FAILED, notification.getEmailNotificationStatus());
        assertNotNull(notification.getEmailNotificationDate());
    }

    @Test
    public void testChangeEmailNotificationStatusToReadyAfterSent() {
        notification.changeEmailNotificationStatus(EmailNotificationStatus.SENT);
        Date sentDate = notification.getEmailNotificationDate();

        assertTrue(notification.changeEmailNotificationStatus(EmailNotificationStatus.READY));
        assertEquals(EmailNotificationStatus.READY, notification.getEmailNotificationStatus());
        assertEquals(sentDate, notification.getEmailNotificationDate());
    }
}
