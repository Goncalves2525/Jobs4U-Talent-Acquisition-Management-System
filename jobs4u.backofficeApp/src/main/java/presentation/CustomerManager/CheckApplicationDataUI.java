package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import static java.lang.Long.parseLong;

public class CheckApplicationDataUI {

    ListApplicationsController ctrl = new ListApplicationsController();

    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Check Application Data");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        boolean check;
        long applicationID = 0;
        do {
            String appID = ConsoleUtils.readLineFromConsole("Insert the Application ID:");
            if (appID == null) {
                check = false;
            }else {
                check = true;
                applicationID = parseLong(appID);
            }
        } while (!check);


        System.out.println("\033[1mAll stored information about chosen Application\033[0m\n");
        Iterable<Application> applications = ctrl.listApplications();

        // Check if application is empty
        if (!applications.iterator().hasNext()) {
            System.out.println("Applications not found!");
        } else {
            // Iterate over applications if it's not empty
            for (Application application : applications) {
                if (applicationID == application.getId())
                    System.out.println("Application ID: " + application.getId() +
                            "\nApplication Status: " + application.getStatus() +
                            "\n\n\u001B[4mCandidate Information\u001B[0m \n" + application.getCandidate() +
                            "\n\n\u001B[4mJob Opening Information\u001B[0m" +
                            "\nJob Reference: " + application.getJobOpening().getJobReference() +
                            "\nTitle: " + application.getJobOpening().getTitle() +
                            "\nContract Type: " + application.getJobOpening().getContractType() +
                            "\nMode: " + application.getJobOpening().getMode() +
                            "\n" + application.getJobOpening().getAddress() +
                            "\nCompany: " + application.getJobOpening().getCompany().getName() +
                            "\nNumber of Vacancies: " + application.getJobOpening().getNumberOfVacancies() +
                            "\nJob Specifications: " + application.getJobOpening().getJobSpecifications() +
                            "\nJob Description: " + application.getJobOpening().getDescription() +
                            "\nJob Requirements: " + application.getJobOpening().getRequirements() +
                            "\nJob Recruitment State: " +application.getJobOpening().getState() +
                            "\n\nApplication Date: " + application.getApplicationDate() +
                            "\nInterview Model: " + application.getInterviewModel() +
                            "\nInterview Model Path: " + application.filePath() +
                            "\nApplication Files Path: " + application.applicationFilesPath());
            }
        }
    }
}

