package authzManagement.presentation;

import authzManagement.application.AuthzController;
import authzManagement.domain.Role;
import authzManagement.domain.Token;
import console.ConsoleUtils;
import textformat.AnsiColor;

import java.util.Optional;

public class AuthzUI {

    AuthzController authzController = new AuthzController();
    Optional<String> sessionToken;

    public AuthzUI() {
    }

    public boolean doLogin() {
        ConsoleUtils.buildUiHeader("LOGIN");
        String user = ConsoleUtils.readLineFromConsole("USER:");
        String pwd = ConsoleUtils.readLineFromConsole("PASSWORD:");
        this.sessionToken = authzController.doLogin(user, pwd);
        if(!this.sessionToken.get().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean validateAccess(Role roleRequired){
        ConsoleUtils.showMessageColor("Validating access.", AnsiColor.CYAN);
        return authzController.validateAccess(this.sessionToken.get(), roleRequired);
    }

    public Role getValidBackofficeRole(){
        ConsoleUtils.showMessageColor("Getting role.", AnsiColor.CYAN);
        return authzController.getValidBackofficeRole(this.sessionToken.get());
    }

    public boolean doLogout() {
        ConsoleUtils.showMessageColor("Goodbye. Hope to see you soon.", AnsiColor.CYAN);
        return authzController.doLogout(this.sessionToken.get());
    }

    public boolean forceLogout() {
        return authzController.doLogout(this.sessionToken.get());
    }
}
