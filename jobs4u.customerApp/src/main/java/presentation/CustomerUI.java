package presentation;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import tcpMessage.TcpCode;
import tcpMessage.TcpMessage;
import textformat.AnsiColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomerUI {

    public CustomerUI() {
    }

    public void doShow(InputStream inputStream, OutputStream outputStream, Role role) throws IOException {

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();
        options.add("List Assigned Job Openings");        // 1
        String message = "What do you want to do?";
        String exit = "Exit";

        // run options menu
        do {

            // build UI header:
            ConsoleUtils.buildUiHeader("Jobs4U CustomerApp");

            // read option selected
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case 0:
                    break;
                case 1:
                    // send request
                    outputStream.write(TcpMessage.buildTcpMessageREQUESTCustomerAssignedJobOpenings());
                    // read response
                    int[] headerAssignedJobOpenings= TcpMessage.readTcpMessageHeader(inputStream);
                    if (headerAssignedJobOpenings[1] == TcpCode.RESPONSE_CUST_ASSIG_JO.getCode()) {
                        ArrayList<String> messageAssignedJobOpenings = TcpMessage.readTcpMessageContent(inputStream, headerAssignedJobOpenings[2], headerAssignedJobOpenings[3]);
                        ConsoleUtils.buildUiTitle("Assigned Job Openings");
                        TcpMessage.printMessages(messageAssignedJobOpenings);
                    } else if (headerAssignedJobOpenings[1] == TcpCode.ERR.getCode()) {
                        ArrayList<String> messageAssignedJobOpeningsErrors = TcpMessage.readTcpMessageContent(inputStream, headerAssignedJobOpenings[2], headerAssignedJobOpenings[3]);
                        TcpMessage.printErrorMessages(messageAssignedJobOpeningsErrors);
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
