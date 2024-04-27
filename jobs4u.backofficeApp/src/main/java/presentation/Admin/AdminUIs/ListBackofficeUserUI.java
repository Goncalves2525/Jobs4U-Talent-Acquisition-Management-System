package presentation.Admin.AdminUIs;

import appUserManagement.application.ManageBackofficeUserController;
import appUserManagement.domain.Role;
import appUserManagement.domain.dto.AppUserDTO;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import textformat.AnsiColor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ListBackofficeUserUI {

    static Role managerRole;
    ManageBackofficeUserController ctrl = new ManageBackofficeUserController();

    public ListBackofficeUserUI() {
    }

    public void doShow(AuthzUI authzUI){

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if(!managerRole.equals(Role.ADMIN)){
            ConsoleUtils.showMessageColor("You don't have permissions to register Backoffice users.", AnsiColor.RED);
            return;
        }

        // get list of Backoffice AppUsers
        Optional<List<AppUserDTO>> backofficeUsers = ctrl.getListBackofficeUsers(managerRole);

        if(backofficeUsers.isEmpty()){
            ConsoleUtils.showMessageColor("No users to be presented.", AnsiColor.RED);
            return;
        }

        // set title message and list Backoffice AppUsers
        String message = "List of Backoffice AppUsers in the system:";
        ConsoleUtils.showListNoCancel(backofficeUsers.orElse(Collections.emptyList()), message);
        ConsoleUtils.readLineFromConsole("Press Enter to continue.");
    }
}
