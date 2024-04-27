package presentation.Admin.AdminUIs;

import appUserManagement.application.SignUpController;
import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import infrastructure.authz.AuthzUI;
import console.ConsoleUtils;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegisterBackofficeUserUI {

    static Role userRole;
    SignUpController signUpController = new SignUpController();

    public RegisterBackofficeUserUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // get user role, to be used as parameter on restricted user actions
        userRole = authzUI.getValidBackofficeRole();
        if(!userRole.equals(Role.ADMIN)){
            ConsoleUtils.showMessageColor("You don't have permissions to register Backoffice users.", AnsiColor.RED);
            return;
        }

        // set list of backoffice roles
        List<Role> backofficeRoles = new ArrayList<>();
        backofficeRoles.add(Role.CUSTOMERMANAGER);
        backofficeRoles.add(Role.OPERATOR);
        String message = "Choose the user role:";

        do {
            ConsoleUtils.buildUiTitle("Register Backoffice User");
            String email = ConsoleUtils.readLineFromConsole("Type the user e-mail:");
            Role role = (Role) ConsoleUtils.showAndSelectOneNoCancel(backofficeRoles, message);
            Optional<String> password = signUpController.signUp(new Email(email), role, userRole);
            if (password.isEmpty()){
                System.out.println();
                ConsoleUtils.showMessageColor("Failed to create user!", AnsiColor.RED);
            } else {
                System.out.println();
                ConsoleUtils.showMessageColor("User created with password: " + password.get(), AnsiColor.GREEN);
            }
            System.out.println();
        } while(ConsoleUtils.confirm("Do you want to register another user? (y/n)"));
    }
}
