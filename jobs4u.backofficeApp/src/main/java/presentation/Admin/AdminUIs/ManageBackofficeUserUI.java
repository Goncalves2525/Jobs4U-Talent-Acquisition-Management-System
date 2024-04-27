package presentation.Admin.AdminUIs;

import appUserManagement.application.ManageBackofficeUserController;
import appUserManagement.domain.Role;
import appUserManagement.domain.dto.AppUserDTO;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ManageBackofficeUserUI {

    static Role managerRole;
    ManageBackofficeUserController ctrl = new ManageBackofficeUserController();

    public ManageBackofficeUserUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.equals(Role.ADMIN)) {
            ConsoleUtils.showMessageColor("You don't have permissions to register Backoffice users.", AnsiColor.RED);
            return;
        }

        // set variables and list of available management actions
        AppUserDTO userDTO;
        int action;
        List<String> managementActions = new ArrayList<>();
        managementActions.add("Enable/Disable");
        // (add more, if needed)

        do {
            // get list of Backoffice AppUsers
            Optional<List<AppUserDTO>> backofficeUsers = ctrl.getListBackofficeUsers(managerRole);

            if (backofficeUsers.isEmpty()) {
                ConsoleUtils.showMessageColor("No users to be presented.", AnsiColor.RED);
                return;
            }

            // set title message and list Backoffice AppUsers
            String message1 = "Select the Backoffice AppUsers you want to manage:";
            userDTO = (AppUserDTO) ConsoleUtils.showAndSelectOneNoCancel(backofficeUsers.orElse(Collections.emptyList()), message1);

            // set title message and list of management options
            String message2 = "Choose what you want to manage on user " + userDTO.getEmail();
            String exit = "Exit";
            action = ConsoleUtils.showAndSelectIndex(managementActions, message2, exit);
            switch (action){
                case 0:
                    break;
                case 1:
                    if(ctrl.swapAbility(userDTO.getEmail(), managerRole)){
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
        } while (ConsoleUtils.confirm("Do you want to manage another user? (y/n)"));
    }
}
