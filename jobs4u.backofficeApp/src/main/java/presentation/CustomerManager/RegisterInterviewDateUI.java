package presentation.CustomerManager;

import appUserManagement.application.AuthzController;
import appUserManagement.domain.Role;
import appUserManagement.repositories.UserRepository;
import applicationManagement.application.CandidateController;
import applicationManagement.application.ManageCandidateController;
import applicationManagement.application.RegisterInterviewDateController;
import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import infrastructure.persistance.PersistenceContext;
import textformat.AnsiColor;

import java.util.Date;
import java.util.Optional;

public class RegisterInterviewDateUI {
    private RegisterInterviewDateController ctrl;
    static Role managerRole;
    ApplicationRepository appRepo = PersistenceContext.repositories().applications();

    public RegisterInterviewDateUI() {
        this.ctrl = new RegisterInterviewDateController(appRepo);
    }

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
        Optional<Application> application = ctrl.findApplicationById(appID);
        if (application == null) {
            System.out.println("Application not found");
        }
        success = ctrl.checkIfApplicationHasInterviewDate(application.get());
        if (success) {
            System.out.println("Application already has an Interview scheduled. You are about to replace it.");
        }
        Date interviewDate = ConsoleUtils.readDateFromConsole("Interview Date (dd-MM-yyyy): ");
        success = ctrl.registerInterviewDateToApplication(application.get(), interviewDate);
        if (success) {
            System.out.println("Interview Date Registered Successfully!");
        } else {
            System.out.println("Error registering Interview Date to Application");
        }
    }
}