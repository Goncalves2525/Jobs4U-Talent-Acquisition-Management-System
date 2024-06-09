package notificationManagement;

import notificationManagement.application.NotificationCustomerManagerController;
import notificationManagement.domain.Notification;
import notificationManagement.repositories.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class notificationCustomerManagerControllerTest {

    @Mock
    private NotificationRepository repo;

    private NotificationCustomerManagerController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new NotificationCustomerManagerController(repo);
    }

    @Test
    public void testFindJobReferenceNotifications() {
        String jobReference = "JOB123";
        ArrayList<Notification> mockNotifications = new ArrayList<>();
        Notification mockNotification = mock(Notification.class);
        mockNotifications.add(mockNotification);

        when(repo.findAllByJobReference(jobReference)).thenReturn(mockNotifications);

        ArrayList<Notification> notifications = controller.findJobReferenceNotifications(jobReference);

        assertEquals(1, notifications.size());
        verify(repo, times(1)).findAllByJobReference(jobReference);
    }

    @Test
    public void testAddNewNotification() {
        Notification mockNotification = mock(Notification.class);

        when(repo.save(mockNotification)).thenReturn(mockNotification);

        Notification savedNotification = controller.addNewNotification(mockNotification);

        assertEquals(mockNotification, savedNotification);
        verify(repo, times(1)).save(mockNotification);
    }
}
