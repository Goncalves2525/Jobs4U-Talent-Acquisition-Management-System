package presentation.Operator;

import appUserManagement.application.AuthzController;
import appUserManagement.domain.dto.AppUserDTO;
import appUserManagement.repositories.UserRepository;
import applicationManagement.application.CandidateController;
import applicationManagement.application.ManageCandidateController;
import appUserManagement.domain.Role;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ManageCandidateUI {
    private static Role operatorRole;
    private CandidateController candidateController;
    private ManageCandidateController manageCandidateController;

    public ManageCandidateUI(UserRepository userRepo, AuthzController authzController) {
        this.candidateController = new CandidateController();
        this.manageCandidateController = new ManageCandidateController(userRepo, authzController);
    }

    protected boolean doShow(AuthzUI authzUI) {
        // Get user role, to be used as parameter on restricted user actions
        operatorRole = authzUI.getValidBackofficeRole();
        if (!operatorRole.equals(Role.OPERATOR)) {
            ConsoleUtils.showMessageColor("You don't have permissions to enable/disable a candidate.", AnsiColor.RED);
            return false;
        }

        int action;
        List<String> managementActions = new ArrayList<>();
        managementActions.add("Enable/Disable");

        do {
            Iterable<Candidate> candidates = candidateController.allCandidatesSortedByName();
            if (candidates == null) {
                ConsoleUtils.showMessageColor("No users to be presented.", AnsiColor.RED);
                return false;
            }

            int i = 1;
            System.out.println("== CANDIDATES ==");
            for (Candidate candidate : candidates) {
                String email = candidate.email();

                Optional<AppUserDTO> candidateUser = manageCandidateController.findByEmail(email);
                String ability = candidateUser.map(user -> user.getAbility().getAbilityName().toUpperCase()).orElse("Unknown");
                System.out.println(i + " - " + candidate.name() + " <" + ability + ">");
                i++;
            }
            int option = ConsoleUtils.readIntegerFromConsole("Select the candidate you want to manage: ");
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
            Optional<AppUserDTO> candidateUser;

            action = ConsoleUtils.showAndSelectIndex(managementActions, "Select an action:", "Exit");
            switch (action) {
                case 0:
                    break;
                case 1:
                    Optional<String> sessionToken = authzUI.getSessionToken();// Assuming sessionToken is retrieved from authzUI
                    if (sessionToken.isPresent() && manageCandidateController.swapCandidateAbility(email, operatorRole, sessionToken.get())) {
                        System.out.println();
                        ConsoleUtils.showMessageColor("Success!", AnsiColor.GREEN);
                    } else {
                        System.out.println();
                        ConsoleUtils.showMessageColor("Failed!", AnsiColor.RED);
                    }

                    candidateUser = manageCandidateController.findByEmail(email);

                    if (candidateUser.isPresent()) {
                        AppUserDTO user = candidateUser.get();
                        System.out.println("Selected candidate -> Name: "+ selectedCandidate.name() +" | Email: "+ user.getEmail() + " | Ability: " + user.getAbility());
                    } else {
                        ConsoleUtils.showMessageColor("User not found.", AnsiColor.RED);
                    }
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option!", AnsiColor.RED);
                    break;
            }


        } while (ConsoleUtils.confirm("Do you want to manage another candidate? (y/n)"));
        return true;
    }
}
