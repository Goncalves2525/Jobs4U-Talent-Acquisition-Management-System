package jobOpeningManagement.domain;

public enum ContractType {
    FULL_TIME("Full Time"),
    PART_TIME("Part time");






    private final String displayName;

    ContractType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
