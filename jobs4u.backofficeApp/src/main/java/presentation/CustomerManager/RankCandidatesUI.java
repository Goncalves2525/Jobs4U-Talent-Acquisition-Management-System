package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.RankingController;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;


public class RankCandidatesUI {

    RankingController ctrl = new RankingController();

    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Ranking Candidates");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        String jobReference;
        boolean closedStatusCheck;
        do {
            jobReference = ConsoleUtils.readLineFromConsole("Insert Job Reference: ");
            closedStatusCheck = ctrl.validateIfJobOpeningClosed(jobReference);
        }while (jobReference == null || closedStatusCheck);

        String rank;
        do {
            rank = ConsoleUtils.readLineFromConsole("Insert the email of the Candidates separated by a semicolon(;) and ordered by their ranking: ");
        }while (rank == null);

        ctrl.rankingCandidatesOfJobOpening(jobReference, rank);
    }
}

