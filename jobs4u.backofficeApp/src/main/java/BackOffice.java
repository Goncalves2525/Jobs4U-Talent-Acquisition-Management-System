import authzManagement.domain.Role;
import authzManagement.presentation.AuthzUI;
import console.ConsoleUtils;
import presentation.Admin.AdminUI;
import presentation.CustomerManager.CustomerManagerUI;
import presentation.Operator.OperatorUI;
import textformat.AnsiColor;

public class BackOffice {

    static Role roleInUse;
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

        roleInUse = authzUI.getValidBackofficeRole();



        //roleInUse = Role.CUSTOMERMANAGER;

        switch (roleInUse){
            case ADMIN:
                ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
                ConsoleUtils.readLineFromConsole("Press enter to continue.");
                ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Admin");
                AdminUI adminUI = new AdminUI();
                adminUI.doShow();
                break;
            case CUSTOMERMANAGER:
                ConsoleUtils.showMessageColor("User authorized.", AnsiColor.GREEN);
                ConsoleUtils.readLineFromConsole("Press enter to continue.");
                ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Customer Manager");
                CustomerManagerUI customerManagerUI = new CustomerManagerUI();
                // TODO: rever este, em vez de usar o AbstractUI?!
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

        authzUI.doLogout();

        // if in bootstrap mode, then drop all database objects
        if(BOOTSTRAPMODE) {
            // DatabaseUtility.dropAllDataBaseObjects();
        }
    }
}
