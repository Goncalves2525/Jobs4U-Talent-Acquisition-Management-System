import presentation.CustomerManager.CustomerManagerUI;
import utils.Utils;

public class BackOffice {
    public static void main(String[] args) {
        System.out.println("Welcome to Jobs4U backoffice");
        System.out.println("==================================");
        System.out.println();
        Utils.readLineFromConsole("Press Enter to continue...");
        CustomerManagerUI customerManagerUI = new CustomerManagerUI();
        customerManagerUI.show();
    }
}
