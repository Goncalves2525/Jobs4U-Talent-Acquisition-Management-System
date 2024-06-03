package presentation;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import tcpMessage.TcpMessage;
import textformat.AnsiColor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateUI {

    public CandidateUI() {
    }

    public void doShow(InputStream inputStream, OutputStream outputStream, Role role) throws IOException {

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();
        options.add("List My Applications Status");        // 1
        String message = "What do you want to do?";
        String exit = "Exit";

        // run options menu
        do {

            // build UI header:
            ConsoleUtils.buildUiHeader("Jobs4U CandidateApp");

            // read option selected
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case 0:
                    break;
                case 1:
                    // send request
                    outputStream.write(TcpMessage.buildTcpMessageREQUESTCandidateApplicationStatus());
                    // read response
                    int[] headerCandidateApplicationStatus = TcpMessage.readTcpMessageHeader(inputStream);
                    if (headerCandidateApplicationStatus[1] == 7) {
                        ArrayList<String> messageCandidateApplicationStatus = TcpMessage.readTcpMessageContent(inputStream, headerCandidateApplicationStatus[2], headerCandidateApplicationStatus[3]);
                        ConsoleUtils.buildUiTitle("My Application(s) Status");
                        TcpMessage.printMessages(messageCandidateApplicationStatus);
                    } else if (headerCandidateApplicationStatus[1] == 3) {
                        ArrayList<String> messageCandidateApplicationStatusErrors = TcpMessage.readTcpMessageContent(inputStream, headerCandidateApplicationStatus[2], headerCandidateApplicationStatus[3]);
                        TcpMessage.printErrorMessages(messageCandidateApplicationStatusErrors);
                    } else {
                        inputStream.readAllBytes(); // used to clean any leftovers on the inputStream
                        ConsoleUtils.showMessageColor("Invalid server response!", AnsiColor.RED);
                    }
                    ConsoleUtils.readLineFromConsole("Press Enter to continue...");
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
            }

        } while (option != 0);
    }
}
