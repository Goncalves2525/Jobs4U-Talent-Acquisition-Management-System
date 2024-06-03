package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import static java.lang.Long.parseLong;

public class CheckApplicationDataUI {

    ListApplicationsController ctrl = new ListApplicationsController();

    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Check Application Data");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        boolean check;
        long applicationID = 0;
        do {
            String appID = ConsoleUtils.readLineFromConsole("Insert the Application ID:");
            if (appID == null) {
                check = false;
            }else {
                check = true;
                try {
                    applicationID = parseLong(appID);
                } catch (NumberFormatException e) {
                    check = false;
                }
            }
        } while (!check);


        System.out.println("\033[1mAll stored information about chosen Application\033[0m\n");
        ctrl.getApplication(applicationID);
    }
}

