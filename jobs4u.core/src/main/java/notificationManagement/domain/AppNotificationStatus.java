package notificationManagement.domain;

import lombok.Getter;

@Getter
public enum AppNotificationStatus {
    PENDING("PENDING", "Notification app is pending to be fetched."),
    UPDATED("UPDATED", "Notification app has been updated and ready to be fetched."),
    READ("READ", "Notification app has been read.");

    private final String displayName;
    private final String message;

    AppNotificationStatus(String displayName, String message) {
        this.displayName = displayName;
        this.message = message;
    }
}
