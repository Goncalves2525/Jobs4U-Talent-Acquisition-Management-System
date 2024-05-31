package presentation.CustomerManager;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.DefineRecruitmentPhaseController;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import textformat.AnsiColor;

import java.util.List;

public class DefineRecruitmentPhaseUI {

    private DefineRecruitmentPhaseController ctrl = new DefineRecruitmentPhaseController();
    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("List of job Openings");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        System.out.println("Job Openings:");
        Iterable<JobOpening> jobOpenings = ctrl.listJobOpenings();
        for (JobOpening jobOpening : jobOpenings) {
            System.out.println(jobOpening.toString());
        }

        String jobID = "";
        System.out.println("Insert Job Opening ID: ");
        jobID = ConsoleUtils.readLineFromConsole("Job Opening ID: ");
        JobOpening jobOpening = ctrl.findJobOpeningById(jobID);
        if (jobOpening == null) {
            System.out.println("Job Opening not found");
        }
        System.out.println("Job Opening Selected");
        System.out.println("The current Job Opening recruitment phase is "+ctrl.findJobOpeningRecruitmentState(jobOpening)+" what stage do you want it to change to?");
        System.out.println("Remember that the preceding and subsequent phases will be closed.");
        changeJobOpeningState(jobOpening);
    }

    public void changeJobOpeningState(JobOpening jobOpening) {

        List<String> states = ctrl.getAllRecruitmentStates();
        int choice = ConsoleUtils.readIntegerFromConsole("Select the new recruitment state:");
        for (int i = 0; i < states.size(); i++) {
            System.out.println((i + 1) + ". " + states.get(i));
        }
        if (choice < 1 || choice > states.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        RecruitmentState newState = RecruitmentState.valueOf(states.get(choice - 1));
        ctrl.setJobOpeningRecruitmentState(jobOpening, newState);
        System.out.println("Recruitment state updated successfully.");
    }
}
