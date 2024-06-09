package applicationManagement.domain;

import lombok.Getter;

@Getter
public enum ApplicationStatus {
    SUBMITTED("Submitted"),
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    INTERVIEW_SCHEDULED("Interview Scheduled"),
    INTERVIEW_DONE("Interview Done"),
    HIRED("Hired");

    private final String displayName;

    ApplicationStatus(String displayName) {
        this.displayName = displayName;
    }

}
