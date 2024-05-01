package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

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

        // Check if jobOpenings is empty
        if (!jobOpenings.iterator().hasNext()) {
            System.out.println("No job openings found.");
        } else {
            // Iterate over jobOpenings if it's not empty
            for (JobOpening jobOpening : jobOpenings) {
                System.out.println(jobOpening.toString());
            }
        }

        String jobReference = ConsoleUtils.readLineFromConsole("Insert the Job Reference:");

        System.out.println("Applications for the Job Reference inserted:");
        Iterable<Application> applications = ctrl.listApplications();

        // Check if application is empty
        if (!applications.iterator().hasNext()) {
            System.out.println("No applications found.");
        } else {
            // Iterate over applications if it's not empty
            for (Application application : applications) {
                if (application.getJobReference().equals(jobReference))
                    System.out.println(application.toString());
            }
        }
    }
}
