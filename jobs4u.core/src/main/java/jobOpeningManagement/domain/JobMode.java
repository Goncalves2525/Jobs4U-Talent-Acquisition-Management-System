package jobOpeningManagement.domain;

public enum JobMode {
    REMOTE("Remote"),
    HYBRID("Hybrid"),
    ON_SITE("On Site");





    private final String displayName;

    JobMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
