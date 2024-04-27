import appUserManagement.domain.Role;
import infrastructure.authz.AuthzUI;
import console.ConsoleUtils;
import presentation.CandidateUI;
import textformat.AnsiColor;

public class Candidate {
    static Role roleRequired = Role.CANDIDATE;
    final static boolean BOOTSTRAPMODE = true;

    public static void main(String[] args) {

        // if in bootstrap mode, launch bootstrapper
        if (BOOTSTRAPMODE) {
            // TODO: launch bootstrapper
        }

        AuthzUI authzUI = new AuthzUI();
        if (!authzUI.doLogin()) {
            ConsoleUtils.showMessageColor("Log in failed.", AnsiColor.RED);
            return;
        }

        if (!authzUI.validateAccess(roleRequired)) {
            ConsoleUtils.showMessageColor("Unauthorized access.", AnsiColor.RED);
            authzUI.forceLogout();
            return;
        }

        ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
        ConsoleUtils.readLineFromConsole("Press enter to continue.");
        ConsoleUtils.buildUiHeader("Customer App");
        CandidateUI ui = new CandidateUI();
        ui.doShow();
        authzUI.doLogout();

        // if in bootstrap mode, then drop all database objects
        if(BOOTSTRAPMODE) {
            // DatabaseUtility.dropAllDataBaseObjects();
        }
    }
}
