import appUserManagement.domain.Role;
import console.ConsoleUtils;
import presentation.CustomerUI;
import tcpMessage.TcpCode;
import tcpMessage.TcpMessage;
import textformat.AnsiColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class CustomerApp {
    final static Role ROLE_REQUIRED = Role.CUSTOMER;
    final static String HOSTNAME = "localhost";
    //final static String HOSTNAME = "labs-vsrv27.dei.isep.ipp.pt";  // ISEP server
    static InetAddress serverIP;
    final static int PORT = 1027;
    final static int TIMEOUT = 30000;

    public static void main(String[] args) {

        do {
            // Get serverIP
            try {
                serverIP = InetAddress.getByName(HOSTNAME);
            }
            catch(UnknownHostException ex) {
                System.out.println("Invalid server specified: " + HOSTNAME);
                return;
            }

            // Establish follow-up server connection
            try (Socket socket = new Socket(serverIP, PORT)) {

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
                    if (header[1] == TcpCode.ACK.getCode()) {
                        // Run Candidate UI
                        new CustomerUI().doShow(inputStream, outputStream, ROLE_REQUIRED);
                        // Request logout
                        outputStream.write(TcpMessage.buildTcpMessageLOGOUT());
                        break;
                    } else if (header[1] == TcpCode.ERR.getCode()) {
                        // Decrement login attempts
                        loginAttempts--;
                        ArrayList<String> authError = TcpMessage.readTcpMessageContent(inputStream, header[2], header[3]);
                        authError.add("You have " + loginAttempts + " attempts left.");
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

        switch (TcpCode.fromCode(code)) {
            case ACK:
                ConsoleUtils.showMessageColor("ACK received!", AnsiColor.CYAN);
                break;
            case ERR:
                ArrayList<String> errors = TcpMessage.readTcpMessageContent(inputStream, data1_len_l, data1_len_m);
                TcpMessage.printMessages(errors);
                outputStream.write(TcpMessage.buildTcpMessageACK());
                break;
            case TESTING:
                ArrayList<String> genericMessages = TcpMessage.readTcpMessageContent(inputStream, data1_len_l, data1_len_m);
                TcpMessage.printMessages(genericMessages);
                break;
            default:
                outputStream.write("Unrecognized code!\n".getBytes());
        }
        return true;
    }
}
