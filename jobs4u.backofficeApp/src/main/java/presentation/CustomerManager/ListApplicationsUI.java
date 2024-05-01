package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

import java.util.Scanner;

public class ListApplicationsUI {

    ListApplicationsController ctrl = new ListApplicationsController();
    ListJobOpeningsController ctrlJobOpening = new ListJobOpeningsController();

    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("List Applications");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        System.out.println("Job Openings:");
        Iterable<JobOpening> jobOpenings = ctrlJobOpening.listJobOpenings();
        for(JobOpening jobOpening : jobOpenings){
            System.out.println(jobOpening.toString());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the Job Reference:");
        String jobReference = scanner.nextLine();
        scanner.close();

        System.out.println("Applications for the Job Reference inserted:");
        Iterable<Application> applications = ctrl.listApplications();
        for(Application application : applications){
            if (application.getJobReference().equals(jobReference))
           System.out.println(application.toString());
        }
    }

}
