import appUserManagement.domain.Role;
import console.ConsoleUtils;
import presentation.CandidateUI;
import tcpMessage.TcpMessage;
import textformat.AnsiColor;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Candidate {
    final static Role ROLE_REQUIRED = Role.CANDIDATE;
    final static String HOSTNAME = "localhost";
    final static int PORT = 99;

    public static void main(String[] args) {

        do {
            // Establish follow-up server connection
            try (Socket socket = new Socket(HOSTNAME, PORT)) {

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                boolean valid = true;
                int loginAttempts = 3;

                do {

                    // Send message
                    ArrayList<String> messages = new ArrayList<>();
                    messages.add("Hello, server!");
                    byte[] message = TcpMessage.buildTcpMessageTESTING(messages);
                    outputStream.write(message);

                    // Read response
                    int[] header = TcpMessage.readTcpMessageHeader(inputStream);
                    valid = processRequest(inputStream, outputStream, header);

                    if (!valid) {
                        ConsoleUtils.showMessageColor("Hello message failed.", AnsiColor.RED);
                        break;
                    }

                    // Send AUTHENTICATION request
                    ArrayList<String> auth = new ArrayList<>();
                    ConsoleUtils.buildUiHeader("LOGIN");
                    auth.add(ConsoleUtils.readLineFromConsole("USER:"));
                    auth.add(ConsoleUtils.readLineFromConsole("PASSWORD:"));
                    message = TcpMessage.buildTcpMessageAUTH(auth);
                    outputStream.write(message);

                    // Read AUTHENTICATION response
                    header = TcpMessage.readTcpMessageHeader(inputStream);
                    if (header[1] == 2) {
                        // Run Candidate UI
                        new CandidateUI().doShow();
                        // Request logout
                        outputStream.write(TcpMessage.buildTcpMessageLOGOUT());
                        break;
                    } else if (header[1] == 3) {
                        // Decrement login attempts
                        loginAttempts--;
                        ArrayList<String> authError = TcpMessage.readTcpMessageContent(inputStream, header[2], header[3]);
                        authError.add("You have " + loginAttempts + " left.");
                        TcpMessage.printErrorMessages(authError);
                    } else {
                        ConsoleUtils.showMessageColor("Unknown server response!", AnsiColor.RED);
                    }

                    // Validate if maximum attempts have been reached
                    if (loginAttempts == 0) {
                        ConsoleUtils.showMessageColor("Maximum login attempts reached.", AnsiColor.RED);
                    }

                } while (loginAttempts > 0);

                // Request disconnect to server and disconnect client
                outputStream.write(TcpMessage.buildTcpMessageDISCONN());
                socket.close();

            } catch (IOException ex) {
                System.out.println("Client exception: " + ex.getMessage());
                //ex.printStackTrace(); // [TESTING]
            }
        } while (ConsoleUtils.confirm("Do you want to login with another user? (y/n)"));
    }

    private static boolean processRequest(InputStream inputStream, OutputStream outputStream, int[] header) throws IOException {

        int version = header[0];
        int code = header[1];
        int data1_len_l = header[2];
        int data1_len_m = header[3];

        switch (code) {
            case (2):
                ConsoleUtils.showMessageColor("ACK received!", AnsiColor.CYAN);
                break;
            case (3):
                ArrayList<String> errors = TcpMessage.readTcpMessageContent(inputStream, data1_len_l, data1_len_m);
                TcpMessage.printMessages(errors);
                outputStream.write(TcpMessage.buildTcpMessageACK());
                break;
            case (99):
                ArrayList<String> genericMessages = TcpMessage.readTcpMessageContent(inputStream, data1_len_l, data1_len_m);
                TcpMessage.printMessages(genericMessages);
                break;
            default:
                outputStream.write("Unrecognized code!\n".getBytes());
        }
        return true;
    }
}
