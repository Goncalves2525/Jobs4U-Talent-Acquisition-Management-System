import appUserManagement.domain.Role;
import bootstrap.Bootstrappers;
import infrastructure.authz.AuthzUI;
import console.ConsoleUtils;
import infrastructure.persistance.DatabaseUtility;
import presentation.Admin.AdminUI;
import presentation.CustomerManager.CustomerManagerUI;
import presentation.Operator.OperatorUI;
import textformat.AnsiColor;

public class BackOffice {

    static Role roleInUse;
    static Bootstrappers bootstrappers = new Bootstrappers();
    final static boolean BOOTSTRAPMODE = true;

    public static void main(String[] args) {

        // if in bootstrap mode, launch bootstrapper
        if (BOOTSTRAPMODE) {
            DatabaseUtility.dropAllDataBaseObjects();
            DatabaseUtility.clearAllTables();
            bootstrappers.execute();
        }

        // perform authorized login
        AuthzUI authzUI = new AuthzUI();
        if (!authzUI.doLogin()) {
            ConsoleUtils.showMessageColor("Log in failed.", AnsiColor.RED);
            return;
        }

        // get a valid backoffice role
        roleInUse = authzUI.getValidBackofficeRole();

        switch (roleInUse){
            case ADMIN:
                ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
                ConsoleUtils.readLineFromConsole("Press enter to continue.");
                AdminUI adminUI = new AdminUI();
                adminUI.doShow(authzUI);
                break;
            case CUSTOMERMANAGER:
                ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
                ConsoleUtils.readLineFromConsole("Press enter to continue.");
                ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Customer Manager");
                CustomerManagerUI customerManagerUI = new CustomerManagerUI();
                customerManagerUI.doShow();
                break;
            case OPERATOR:
                ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
                ConsoleUtils.readLineFromConsole("Press enter to continue.");
                ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Operator");
                OperatorUI operatorUI = new OperatorUI();
                operatorUI.doShow();
                break;
            default:
                ConsoleUtils.showMessageColor("Unauthorized access.", AnsiColor.RED);
                authzUI.forceLogout();
                return;
        }

        // perform clean logout, clearing user session token
        authzUI.doLogout();
    }
}
