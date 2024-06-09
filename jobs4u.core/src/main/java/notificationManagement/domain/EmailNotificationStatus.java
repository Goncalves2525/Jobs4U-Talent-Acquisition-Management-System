package notificationManagement.domain;

import lombok.Getter;

@Getter
public enum EmailNotificationStatus {
    READY("READY", "Notification e-mail is ready to be sent."),
    SENT("SENT", "Notification e-mail has been sent."),
    FAILED("FAILED", "Notification e-mail failed to be sent.");

    private final String displayName;
    private final String message;

    EmailNotificationStatus(String displayName, String message) {
        this.displayName = displayName;
        this.message = message;
    }
}
