package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.RegisterInterviewDateController;
import applicationManagement.application.SelectInterviewModelController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import plugins.Plugin;
import textformat.AnsiColor;

import java.util.Date;
import java.util.List;

public class RegisterInterviewDateUI {
    private RegisterInterviewDateController ctrl = new RegisterInterviewDateController();
    static Role managerRole;

    public void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Register Interview Date");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        boolean success = false;
        String appID = "";
        appID = ConsoleUtils.readLineFromConsole("Application ID: ");
        Application application = ctrl.findApplicationById(appID);
        if (application == null) {
            System.out.println("Application not found");
        }
        success = ctrl.checkIfApplicationHasInterviewDate(application);
        if (success) {
            System.out.println("Application already has an Interview scheduled. You are about to replace it.");
        }
        Date interviewDate = ConsoleUtils.readDateFromConsole("Interview Date: ");
        success = ctrl.registerInterviewDateToApplication(application, interviewDate);
        if (success) {
            System.out.println("Interview Date Registered Successfully!");
        } else {
            System.out.println("Error registering Interview Date to Application");
        }
    }
}