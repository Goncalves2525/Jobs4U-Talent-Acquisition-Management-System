package presentation.CustomerManager;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

public class ListJobOpeningsUI{
    ListJobOpeningsController ctrl = new ListJobOpeningsController();
    static Role managerRole;


    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("List Job Openings");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        System.out.println("Job Openings:");
        Iterable<JobOpening> jobOpenings = ctrl.listJobOpenings();
        for(JobOpening jobOpening : jobOpenings){
            System.out.println(jobOpening.toString());
        }
    }

}
