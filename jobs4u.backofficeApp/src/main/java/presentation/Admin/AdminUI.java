package presentation.Admin;

import authzManagement.presentation.AuthzUI;
import console.ConsoleUtils;
import presentation.Admin.AdminUIs.ListBackofficeUserUI;
import presentation.Admin.AdminUIs.ManageBackofficeUserUI;
import presentation.Admin.AdminUIs.RegisterBackofficeUserUI;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class AdminUI {

    public AdminUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // build UI header:
        ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Admin");

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();
        options.add("Register Backoffice User");
        options.add("List All Backoffice Users");
        options.add("Manage Backoffice User");
        String message = "What do you want to do?";
        String exit = "Exit";

        // run options menu
        do {
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case 0:
                    break;
                case 1:
                    RegisterBackofficeUserUI registerBackofficeUserUI = new RegisterBackofficeUserUI();
                    registerBackofficeUserUI.doShow(authzUI);
                    break;
                case 2:
                    ListBackofficeUserUI listBackofficeUserUI = new ListBackofficeUserUI();
                    listBackofficeUserUI.doShow(authzUI);
                    break;
                case 3:
                    ManageBackofficeUserUI manageBackofficeUserUI = new ManageBackofficeUserUI();
                    manageBackofficeUserUI.doShow(authzUI);
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
                    break;
            }
        } while (option != 0);
    }
}
