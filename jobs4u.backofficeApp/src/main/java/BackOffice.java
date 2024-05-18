import appUserManagement.domain.Role;
import bootstrap.Bootstrapper;
import infrastructure.authz.AuthzUI;
import console.ConsoleUtils;
import infrastructure.persistance.DatabaseUtility;
import presentation.Admin.AdminUI;
import presentation.CustomerManager.CustomerManagerUI;
import presentation.Operator.OperatorUI;
import textformat.AnsiColor;

public class BackOffice {

    static Role roleInUse;
    static Bootstrapper bootstrapper = new Bootstrapper();
    static boolean bootstrapmode = true;
    static final String BOOTSTRAP_FILE = "bootstrap.txt";

    public static void main(String[] args) {

        bootstrapmode = ConsoleUtils.confirm("Do you want to launch the app in bootstrap mode? (y/n)");

        // if in bootstrap mode, launch bootstrapper
        if (bootstrapmode) {
            //DatabaseUtility.dropAllDataBaseObjects();
            DatabaseUtility.clearAllTables();
            bootstrapper.execute();
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
                CustomerManagerUI customerManagerUI = new CustomerManagerUI();
                customerManagerUI.doShow(authzUI);
                break;
            case OPERATOR:
                ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
                ConsoleUtils.readLineFromConsole("Press enter to continue.");
                ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Operator");
                OperatorUI operatorUI = new OperatorUI();
                operatorUI.doShow(authzUI);
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
