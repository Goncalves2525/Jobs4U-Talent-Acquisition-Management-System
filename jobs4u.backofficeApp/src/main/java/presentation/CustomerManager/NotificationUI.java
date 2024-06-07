package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import notificationManagement.application.NotificationCustomerManagerController;
import notificationManagement.domain.Notification;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class NotificationUI {

    static Role managerRole;

    ListJobOpeningsController listJobOpeningCtrl = new ListJobOpeningsController();
    ListApplicationsController listApplicationCtrl = new ListApplicationsController();
    NotificationCustomerManagerController notificationCmCtrl = new NotificationCustomerManagerController();

    public NotificationUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();
        options.add("List Job Openings : Result Phase");        // 1
        options.add("List Email Notifications Status");         // 2
        options.add("List App Notifications Status");           // 3
        String message = "What do you want to do?";
        String exit = "Exit";

        // run options menu
        do {
            // build UI header:
            ConsoleUtils.buildUiHeader("Customer Manager Notification Menu");
            // display menu:
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case 0:
                    break;
                case 1:
                    listJobOpeningResultPhase();
                    break;
                case 2:
                    // TODO
                    ConsoleUtils.readLineFromConsole("To be implemented.\nPress ENTER to continue...");
                    break;
                case 3:
                    // TODO
                    ConsoleUtils.readLineFromConsole("To be implemented.\nPress ENTER to continue...");
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
            }
        } while (option != 0);
    }

    private void listJobOpeningResultPhase() {
        System.out.println("** Job Openings : Result Phase **");
        Iterable<JobOpening> jobOpenings = listJobOpeningCtrl.listJobOpeningsResultPhase();

        // Check if jobOpenings is empty
        if (!jobOpenings.iterator().hasNext()) {
            System.out.println("No job openings in RESULT phase were found.");
        } else {
            JobOpening selectedJobOpening = (JobOpening) ConsoleUtils.showAndSelectOne((List) jobOpenings, "Choose one Job Opening to be notified:", "Exit");
            notifyStakeholders(selectedJobOpening);
            ConsoleUtils.showMessageColor("Notifications have been sent to - " +
                            selectedJobOpening.jobReference() + " : " + selectedJobOpening.getTitle() +
                            " - related stakeholders (Customer and Candidates).",
                    AnsiColor.CYAN);
        }

        ConsoleUtils.readLineFromConsole("Press ENTER to continue...");
    }

    private void notifyStakeholders(JobOpening selectedJobOpening) {
        ArrayList<Notification> notifications = notificationCmCtrl.findJobReferenceNotifications(selectedJobOpening.jobReference());

        // deal with customer notification
        Notification customerNotification = new Notification(selectedJobOpening.getCompany().getEmail().toString(),
                Role.CUSTOMER, null, selectedJobOpening.jobReference());
        if (notifications.contains(selectedJobOpening.getCompany().getEmail().toString())){
            // do nothing
        } else {
            notificationCmCtrl.addNewNotification(customerNotification);
        }

        // deal with candidates notification
        Iterable<Application> applications = listApplicationCtrl.listApplicationsOfJobRefence(selectedJobOpening.jobReference());
        Notification candidateNotification;
        for (Application application : applications) {
            candidateNotification = new Notification(application.candidate().email(),
                    Role.CANDIDATE, application.getStatus(), selectedJobOpening.jobReference());
            if (notifications.contains(application.candidate().email())) {
                // do nothing
            } else {
                notificationCmCtrl.addNewNotification(candidateNotification);
            }
        }
    }
}
