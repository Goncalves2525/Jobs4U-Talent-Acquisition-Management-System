package fus;

import applicationManagement.domain.Application;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jpa.JpaApplicationRepository;
import jpa.JpaCandidateRepository;
import tcpMessage.TcpMessage;
import textformat.AnsiColor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server {
    public static synchronized void run() {

        final int port = 99;

        // TODO: implement threads from this point on.

        // Start server
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ConsoleUtils.showMessageColor("Server is listening on port " + port, AnsiColor.CYAN);

            AuthzUI authzUI = new AuthzUI();

            // Waiting for new connection
            Socket socket = serverSocket.accept();
            boolean connectionStatus = true;
            ConsoleUtils.showMessageColor("Client connected", AnsiColor.GREEN);

            // Get input and output streams
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Deal with all current connection requests
            while (connectionStatus) {
                int[] header = TcpMessage.readTcpMessageHeader(inputStream);
                connectionStatus = processRequest(inputStream, outputStream, header, authzUI);
            }

            // Terminar conexão
            socket.close();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            //ex.printStackTrace(); // [TESTING]
        }
    }

    private static synchronized boolean processRequest(InputStream inputStream, OutputStream outputStream, int[] header, AuthzUI authzUI) throws IOException {

        int version = header[0];
        int code = header[1];
        int data1_len_l = header[2];
        int data1_len_m = header[3];

        switch (code) {
            case (0):
                outputStream.write(TcpMessage.buildTcpMessageACK());
                break;
            case (1):
                outputStream.write(TcpMessage.buildTcpMessageACK());
                return false;
            case (4):
                ArrayList<String> authMessages = TcpMessage.readTcpMessageContent(inputStream, data1_len_l, data1_len_m);
                //TcpMessage.printMessages(authMessages); // [TESTING]
                if (authzUI.fusLogin(authMessages.get(0), authMessages.get(1))) {
                    outputStream.write(TcpMessage.buildTcpMessageACK());
                    ConsoleUtils.showMessageColor("Log in successful.", AnsiColor.GREEN);
                } else {
                    ArrayList<String> errors = new ArrayList<>();
                    errors.add("Log in failed");
                    outputStream.write(TcpMessage.buildTcpMessageERR(errors));
                }
                break;
            case (5):
                if (authzUI.doLogout()) {
                    outputStream.write(TcpMessage.buildTcpMessageACK());
                } else {
                    ConsoleUtils.showMessageColor("Logout failed!", AnsiColor.RED);
                }
                break;
            case (6):
                String currentUserEmail = authzUI.findCurrentUserEmail();
                Optional<Candidate> candidate = new JpaCandidateRepository().ofIdentity(currentUserEmail);
                if (candidate.isPresent()) {
                    List<Application> listApplications = new JpaApplicationRepository().ofCandidate(candidate.get());
                    ArrayList<String> applicationsStatus = new ArrayList<>();
                    for (Application application : listApplications) {
                        String jobReference = application.getJobReference();
                        String applicationStatus = application.getStatus().getDisplayName();
                        String qtyApplicants = new JpaApplicationRepository().countApplicants(jobReference);
                        applicationsStatus.add("Job Opening Reference: " + jobReference + "\n"
                                + "Current Status: " + applicationStatus + "\n"
                                + "# Applicants: " + qtyApplicants + " candidates applied\n");
                    }
                    outputStream.write(TcpMessage.buildTcpMessageRESPONSECandidateApplicationStatus(applicationsStatus));
                } else {
                    ArrayList<String> invalidCandidate = new ArrayList<>();
                    invalidCandidate.add("You have not yet enrolled as a candidate in any job opening.");
                    outputStream.write(TcpMessage.buildTcpMessageERR(invalidCandidate));
                }
                break;
            case (99):
                ArrayList<String> genericMessages = TcpMessage.readTcpMessageContent(inputStream, data1_len_l, data1_len_m);
                //TcpMessage.printMessages(genericMessages); // [TESTING]
                outputStream.write(TcpMessage.buildTcpMessageACK());
                break;
            default:
                outputStream.write("Unrecognized code!\n".getBytes());
        }
        return true;
    }
}
