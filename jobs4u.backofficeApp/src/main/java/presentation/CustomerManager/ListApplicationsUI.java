package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.application.ListWordCountController;
import applicationManagement.domain.Application;
import applicationManagement.domain.dto.WordCount;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

import java.util.*;

public class ListApplicationsUI {

    ListApplicationsController ctrl = new ListApplicationsController();
    ListJobOpeningsController ctrlJobOpening = new ListJobOpeningsController();
    ListWordCountController ctrlWordCount = new ListWordCountController();

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
                System.out.println("Job Opening: " + jobOpening.getId() + " | Job reference: " + jobOpening.getJobReference() + " | Title: " + jobOpening.getTitle() + " | Description: " + jobOpening.getDescription() + " | State: " + jobOpening.getState());
            }

            String jobReference = ConsoleUtils.readLineFromConsole("Insert the Job Reference:");

            System.out.println("Applications for the Job Reference inserted:");
            Iterable<Application> applications = ctrl.listApplications();

            if (!applications.iterator().hasNext()) {
                ConsoleUtils.showMessageColor("No applications found.", AnsiColor.RED);
            } else {
                List<Application> applicationsOfJobReference = new ArrayList<>();
                for (Application application : applications) {
                    if (application.getJobReference().equals(jobReference)) {
                        applicationsOfJobReference.add(application);
                    }
                }

                Application applicationOfJobReference;
                do {
                    applicationOfJobReference = (Application) ConsoleUtils.showAndSelectOne(applicationsOfJobReference, "Select the application to analyze:", "Exit");
                    if (applicationOfJobReference != null) {
                        int topNumber = 20;
                        WordCount[] wordCountList = ctrlWordCount.findTopWordCountMap(topNumber, applicationOfJobReference);
                        if(wordCountList == null){
                            ConsoleUtils.showMessageColor("There are no files for the application.", AnsiColor.RED);
                        } else {
                            ConsoleUtils.showMessageColor("***** APPLICATION TOP " + topNumber + " WORDS *****", AnsiColor.CYAN);
                            int i = 0;
                            for (WordCount wc : wordCountList) {
                                i++;
                                System.out.printf("%3d | ", i);
                                System.out.printf("%15s | ", wc.getWord());
                                System.out.printf("%4d | ", wc.getWordCount());
                                System.out.printf("%s ", wc.getFiles());
                            }
                        }
                    }
                } while (applicationOfJobReference != null && ConsoleUtils.confirm("Do you want to analyze another application? (y/n)"));
            }

        }
    }
}
