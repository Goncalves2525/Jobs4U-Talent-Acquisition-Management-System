import authzManagement.presentation.AuthzUI;
import console.ConsoleUtils;
import presentation.CustomerUI;

public class CustomerApp {
    public static void main(String[] args) {
        AuthzUI authzUI = new AuthzUI();
        boolean validUser = authzUI.doLogin();
        if (validUser) {
            ConsoleUtils.buildUiHeader("Customer App");
            CustomerUI ui = new CustomerUI();
            ui.show();
        }
    }
}
