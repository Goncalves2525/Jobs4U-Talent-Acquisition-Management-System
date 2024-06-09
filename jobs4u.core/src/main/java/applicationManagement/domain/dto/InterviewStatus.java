package applicationManagement.domain.dto;

public enum InterviewStatus {
    APPROVED("Approved"),
    REJECTED("Rejected");

    private String displayName;

    void RequirementsResult(String displayName) {
        this.displayName = displayName;
    }

    InterviewStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}