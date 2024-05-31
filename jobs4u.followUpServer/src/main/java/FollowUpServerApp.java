import console.ConsoleUtils;
import fus.Client;
import fus.Server;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class FollowUpServerApp {
    public static void main(String[] args) {

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();
        options.add("Run Client");      // 1
        options.add("Run Server");      // 2
        String message = "Select option:";
        String exit = "Exit";

        // run menu
        do {

            // build UI header
            ConsoleUtils.buildUiHeader("Jobs4U Follow-Up Server");

            // read option selected
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case (0):
                    ConsoleUtils.showMessageColor("Closing Follow-Up Server.", AnsiColor.CYAN);
                    // TODO: close any connection that may be running. To be implemented when threads are implemented.
                    break;
                case (1):
                    // TODO: implement thread creation to run Client.run()
                    Client.run();
                    break;
                case (2):
                    // TODO: implement thread creation to run Server.run()
                    Server.run();
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
            }
        } while (option != 0);
    }
}
