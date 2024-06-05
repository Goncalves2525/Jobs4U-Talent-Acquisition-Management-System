package appUserManagement.domain;

public enum Role {

    ADMIN("admin", 1, true, true, true),
    CUSTOMERMANAGER("customer manager", 2, true, false, false),
    OPERATOR("operator", 3, true, false, false),
    CUSTOMER("customer", 2, false, true, false),
    CANDIDATE("candidate", 2, false, false, true),
    DEFAULT("default", 0, false, false, false);

    private final String roleName;
    private final int accessLevel;
    private final boolean backofficeAppAccess;
    private final boolean customerAppAccess;
    private final boolean candidateAppAccess;

    Role(String roleName, int accessLevel, boolean backofficeApp, boolean customerApp, boolean candidateApp) {
        this.roleName = roleName;
        this.accessLevel = accessLevel;
        this.backofficeAppAccess = backofficeApp;
        this.customerAppAccess = customerApp;
        candidateAppAccess = candidateApp;
    }

    public int showAccessLevel() {
        return this.accessLevel;
    }

    public boolean showBackofficeAppAccess() {
        return this.backofficeAppAccess;
    }

    public boolean showCustomerAppAccess() {
        return this.customerAppAccess;
    }

    public boolean showCandidateAppAccess() {
        return this.candidateAppAccess;
    }

    public static Role fromRole(String roleName) {
        return Role.valueOf(roleName);
    }
}
