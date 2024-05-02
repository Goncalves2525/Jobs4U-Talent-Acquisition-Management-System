package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.SelectInterviewModelController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import plugins.Plugin;
import textformat.AnsiColor;

import java.util.List;

public class SelectInterviewModelUI {
    private SelectInterviewModelController ctrl = new SelectInterviewModelController();
    static Role managerRole;

    public void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Select Interview Model");

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
        success = ctrl.checkIfApplicationHasInterviewModel(application);
        if (success) {
            System.out.println("Application already has Interview Model");
        }
        List<Plugin> interviewModels = ctrl.getAllInterviewModels();
        int choice = selectInterviewModel(interviewModels);
        success = ctrl.associateInterviewModelToApplication(application, interviewModels.get(choice).getPluginInstance());
        //success = ctrl.associateInterviewModelPathToApplication(application, interviewModels.get(choice).getPath());
        if (success) {
            System.out.println("Interview Model associated to Application");
        } else {
            System.out.println("Error associating Interview Model to Application");
        }
    }


    private int selectInterviewModel(List<Plugin> interviewModels) {
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