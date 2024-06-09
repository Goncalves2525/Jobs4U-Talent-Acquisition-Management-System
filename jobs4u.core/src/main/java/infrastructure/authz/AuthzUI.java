package infrastructure.authz;

import appUserManagement.application.AuthzController;
import appUserManagement.domain.Role;
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
        System.out.println();

        this.sessionToken = authzController.doLogin(user, pwd);
        if (this.sessionToken.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean fusLogin(String user, String pwd) {
        this.sessionToken = authzController.doLogin(user, pwd);
        if (this.sessionToken.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean validateAccess(Role roleRequired) {
        //ConsoleUtils.showMessageColor("Validating access.", AnsiColor.CYAN);
        return authzController.validateAccess(this.sessionToken.get(), roleRequired);
    }

    public Role getValidBackofficeRole() {
        //ConsoleUtils.showMessageColor("Getting role.", AnsiColor.CYAN);
        return authzController.getValidBackofficeRole(this.sessionToken.get());
    }

    public boolean doLogout() {
        System.out.println();
        ConsoleUtils.showMessageColor("Goodbye. Hope to see you soon.", AnsiColor.CYAN);
        return authzController.doLogout(this.sessionToken.get());
    }

    public boolean forceLogout() {
        return authzController.doLogout(this.sessionToken.get());
    }

    public String findCurrentUserEmail() {
        if (this.sessionToken.isEmpty()) {
            return null;
        }
        return authzController.findCurrentUserEmail(this.sessionToken.get());
    }
    public Optional<String> getSessionToken() {
        return this.sessionToken;
    }
}
