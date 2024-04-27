package presentation.Admin.AdminUIs;

import authzManagement.application.SignUpController;
import authzManagement.domain.Email;
import authzManagement.domain.Role;
import authzManagement.presentation.AuthzUI;
import console.ConsoleUtils;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

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
            String password = String.valueOf(signUpController.signUp(new Email(email), role, userRole));
            if (password.isEmpty()){
                ConsoleUtils.showMessageColor("Failed to create user!", AnsiColor.RED);
            } else {
                ConsoleUtils.showMessageColor("User created with password: " + password, AnsiColor.GREEN);
            }
        } while(ConsoleUtils.confirm("Do you wan to register another user? (y/n)"));
    }
}
