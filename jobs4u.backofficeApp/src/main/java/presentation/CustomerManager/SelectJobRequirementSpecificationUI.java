package presentation.CustomerManager;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.SelectJobRequirementSpecificationController;
import jobOpeningManagement.domain.JobOpening;
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
        String jobID = "";
        System.out.println("Insert Job Opening ID: ");
        jobID = ConsoleUtils.readLineFromConsole("Job Opening ID: ");
        JobOpening jobOpening = ctrl.findJobOpeningById(jobID);
        if (jobOpening == null) {
            System.out.println("Job Opening not found");
        }
        success = ctrl.checkIfJobOpeningHasJobRequirementSpecification(jobOpening);
        if (success) {
            System.out.println("Job Opening already has Job Requirement Specification");
        }
        List<Plugin> allJobRequirementSpecification = ctrl.getAllJobRequirementSpecification();
        int choice = selectJobRequirementSpecification(allJobRequirementSpecification);
        success = ctrl.associateJobRequirementSpecificationToJobOpening(jobOpening, allJobRequirementSpecification.get(choice).getPath());
        if (success) {
            System.out.println("Job Requirement Specification associated to Job Opening");
        } else {
            System.out.println("Error associating Job Requirement Specification to Job Opening");
        }
    }

    private int selectJobRequirementSpecification(List<Plugin> jobRequirements) {
        int i = 0;
        System.out.println("== JOB REQUIREMENT SPECIFICATIONS ==");
        for (Plugin interviewModel : jobRequirements) {
            System.out.println(i + ". " + interviewModel.toString());
            i++;
        }
        int choice;
        do {
            choice = ConsoleUtils.readIntegerFromConsole("Choose a model (enter the number): ");
        } while (choice < 0 || choice >= jobRequirements.size());
        return choice;
    }
}
