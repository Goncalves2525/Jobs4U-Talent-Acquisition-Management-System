package presentation.Operator;

import applicationManagement.application.ListCandidatesService;
import applicationManagement.application.ManageCandidateController;
import appUserManagement.domain.Role;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.CandidateDTO;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManageCandidateUI {

    static Role managerRole;
    ListCandidatesService svc = new ListCandidatesService();
    ManageCandidateController ctrl = new ManageCandidateController();

    public ManageCandidateUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.equals(Role.OPERATOR)) {
            ConsoleUtils.showMessageColor("You don't have permissions to enable/disable a candidate.", AnsiColor.RED);
            return;
        }

        int action;
        List<String> managementActions = new ArrayList<>();
        managementActions.add("Enable/Disable");
        Iterable<Candidate> candidates = svc.allCandidatesSortedByName();

        do {
            if (candidates==null) {
                ConsoleUtils.showMessageColor("No users to be presented.", AnsiColor.RED);
                return;
            }

            int i = 1;
            System.out.println("== CANDIDATES ==");
            for (Candidate candidate : candidates) {
                System.out.println(i + " - " + candidate.name() + " " + candidate.getAbility().toString());
                i++;
            }
            int option = ConsoleUtils.readIntegerFromConsole("Select a Candidate: ");
            if (option < 1 || option > i - 1) {
                ConsoleUtils.showMessageColor("Invalid candidate selection!", AnsiColor.RED);
                continue;
            }


            Iterator<Candidate> iterator = candidates.iterator();
            Candidate selectedCandidate = null;
            for (int j = 0; j < option; j++) {
                selectedCandidate = iterator.next();
            }

            // Get the email from the selected candidate
            String email = selectedCandidate.email();

            action = ConsoleUtils.showAndSelectIndex(managementActions,"Select an action:", "Exit");
            switch (action){
                case 0:
                    break;
                case 1:
                    if(ctrl.swapAbility(email, managerRole)){
                        System.out.println();
                        ConsoleUtils.showMessageColor("Success!", AnsiColor.GREEN);
                    } else {
                        System.out.println();
                        ConsoleUtils.showMessageColor("Failed!", AnsiColor.RED);
                    }
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option!", AnsiColor.RED);
                    break;
            }


        } while (ConsoleUtils.confirm("Do you want to manage another candidate? (y/n)"));
    }
}
