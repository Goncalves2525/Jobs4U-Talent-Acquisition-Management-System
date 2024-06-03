package presentation.CustomerManager;

import appUserManagement.domain.Role;
import jobOpeningManagement.application.SelectInterviewModelController;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.domain.JobOpening;
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
        String jobID = "";
        System.out.println("Insert Job Opening ID: ");
        jobID = ConsoleUtils.readLineFromConsole("Job Opening ID: ");
        JobOpening jobOpening = ctrl.findJobOpeningById(jobID);
        if (jobOpening == null) {
            System.out.println("Job Opening not found");
        }
        success = ctrl.checkIfJobOpeningHasInterviewModel(jobOpening);
        if (success) {
            System.out.println("Job Opening already has Interview Model. You are about to replace it.");
        }
        List<Plugin> interviewModels = ctrl.getAllInterviewModels();
        int choice = selectInterviewModel(interviewModels);
        success = ctrl.associateInterviewModelToJobOpening(jobOpening, interviewModels.get(choice - 1).getPath());
        if (success) {
            System.out.println("Interview Model associated to Job Opening");
        } else {
            System.out.println("Error associating Interview Model to Job Opening");
        }
    }


    private int selectInterviewModel(List<Plugin> interviewModels) {
        int i = 1;
        System.out.println("== INTERVIEW MODELS ==");
        for (Plugin interviewModel : interviewModels) {
            System.out.println(i + ". " + interviewModel.toString());
            i++;
        }
        int choice;
        do {
            choice = ConsoleUtils.readIntegerFromConsole("Choose a model (enter the number): ");
        } while (choice < 1 || choice > interviewModels.size());
        return choice;
    }
}