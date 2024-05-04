package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.SelectJobRequirementSpecificationController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import plugins.Plugin;
import textformat.AnsiColor;

import java.util.List;

public class SelectJobRequirementSpecificationUI {
    private SelectJobRequirementSpecificationController ctrl = new SelectJobRequirementSpecificationController();
    static Role managerRole;

    public void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Select Job Requirement Specification");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        boolean success = false;
        String appID = "";
        System.out.println("Insert Application ID: ");
        appID = ConsoleUtils.readLineFromConsole("Application ID: ");
        Application application = ctrl.findApplicationById(appID);
        if (application == null) {
            System.out.println("Application not found");
        }
        success = ctrl.checkIfApplicationHasJobRequirementSpecification(application);
        if (success) {
            System.out.println("Application already has Interview Model");
        }
        List<Plugin> allJobRequirementSpecification = ctrl.getAllJobRequirementSpecification();
        int choice = selectJobRequirementSpecification(allJobRequirementSpecification);
        success = ctrl.associateJobRequirementSpecificationToApplication(application, allJobRequirementSpecification.get(choice).getPath());
        if (success) {
            System.out.println("Interview Model associated to Application");
        } else {
            System.out.println("Error associating Interview Model to Application");
        }
    }

    private int selectJobRequirementSpecification(List<Plugin> interviewModels) {
        int i = 0;
        System.out.println("== INTERVIEW MODELS ==");
        for (Plugin interviewModel : interviewModels) {
            System.out.println(i + ". " + interviewModel.toString());
            i++;
        }
        int choice;
        do {
            choice = ConsoleUtils.readIntegerFromConsole("Choose a model (enter the number): ");
        } while (choice < 0 || choice >= interviewModels.size());
        return choice;
    }
}
