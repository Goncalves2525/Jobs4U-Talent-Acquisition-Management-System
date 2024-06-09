package applicationManagement.domain;

public enum RequirementsResult {
    APPROVED("Approved"),
    REJECTED("Rejected");






    private final String displayName;

    RequirementsResult(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
