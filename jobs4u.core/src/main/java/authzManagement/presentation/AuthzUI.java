package authzManagement.presentation;

import authzManagement.application.AuthzController;
import console.ConsoleUtils;

public class AuthzUI {

    AuthzController authzController = new AuthzController();

    public AuthzUI() {
    }

    public boolean doLogin (){
        ConsoleUtils.buildUiHeader("LOGIN");
        String user = ConsoleUtils.readLineFromConsole("USER:");
        String pwd = ConsoleUtils.readLineFromConsole("PASSWORD:");
        return authzController.doLogin(user, pwd);
    }
}
