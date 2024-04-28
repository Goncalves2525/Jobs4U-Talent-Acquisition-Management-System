package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.CandidateController;
import applicationManagement.domain.Application;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ListCandidatePersonalDataUI {

    static Role managerRole;

    CandidateController ctrl = new CandidateController();

    public ListCandidatePersonalDataUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        // displaying data
        do {
            String email = ConsoleUtils.readLineFromConsole("Type the e-mail of the candidate you want to list the details:");
            Optional<Candidate> candidate = ctrl.findCandidateByEmail(email);
            displayData(candidate);
        } while (ConsoleUtils.confirm("Do you want to look for another candidate details? (y/n)"));
    }

    public void showCandidatePersonalData(Application application) {

        // writing transitional message
        System.out.println();
        ConsoleUtils.showMessageColor("Gathering Appllication Candidate details", AnsiColor.CYAN);
        System.out.println();

        // displaying data
        Optional<Candidate> candidate = ctrl.findCandidateByEmail(application.getCandidate().email());
        displayData(candidate);
        ConsoleUtils.readLineFromConsole("Press Enter to continue.");
    }

    private void displayData(Optional<Candidate> candidate){
        if (candidate.isPresent()) {
            System.out.println();
            System.out.println(candidate.get());
            System.out.println();

            List<Application> listApplications = ctrl.buildApplicationList(candidate.get());
            for (Application appli : listApplications) {
                System.out.println("[Application] " + appli.getId());
                System.out.println("JobRef: " + appli.getJobOpening().jobReference());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = appli.getDate().format(formatter);
                System.out.println("Registry Date: " + formattedDate);
                System.out.println("Status: " + appli.getStatus());
                System.out.println();
            }
        } else {
            ConsoleUtils.showMessageColor("No candidate found with the e-mail provided.", AnsiColor.RED);
        }
    }
}
