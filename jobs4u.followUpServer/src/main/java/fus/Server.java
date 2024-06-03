package fus;

import applicationManagement.domain.Application;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;
import jpa.JpaApplicationRepository;
import jpa.JpaCandidateRepository;
import jpa.JpaCustomerRepository;
import jpa.JpaJobOpeningRepository;
import tcpMessage.TcpCode;
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

        switch (TcpCode.fromCode(code)) {
            case COMMTEST:
                outputStream.write(TcpMessage.buildTcpMessageACK());
                break;
            case DISCONN:
                outputStream.write(TcpMessage.buildTcpMessageACK());
                return false;
            case AUTH:
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
            case LOGOUT:
                if (authzUI.doLogout()) {
                    outputStream.write(TcpMessage.buildTcpMessageACK());
                } else {
                    ConsoleUtils.showMessageColor("Logout failed!", AnsiColor.RED);
                }
                break;
            case REQUEST_CAND_APP_STAT:
                String candiadteUserEmail = authzUI.findCurrentUserEmail();
                Optional<Candidate> candidate = new JpaCandidateRepository().ofIdentity(candiadteUserEmail);
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
            case REQUEST_CUST_ASSIG_JO:
                String customerUserEmail = authzUI.findCurrentUserEmail();
                Optional<Customer> customer = new JpaCustomerRepository().withEmail(customerUserEmail);
                if (customer.isPresent()) {
                    List<JobOpening> listActiveJobOpenings = new JpaJobOpeningRepository().findAllActiveJobOpenings(customer.get());
                    if (listActiveJobOpenings.isEmpty()) {
                        ArrayList<String> noActiveJobOpenings = new ArrayList<>();
                        noActiveJobOpenings.add("You do not have job openings to be listed.");
                        outputStream.write(TcpMessage.buildTcpMessageERR(noActiveJobOpenings));
                    } else {
                        ArrayList<String> activeJobOpeningsDetail = new ArrayList<>();
                        for (JobOpening jobOpening : listActiveJobOpenings) {
                            String qtyApplicants = new JpaApplicationRepository().countApplicants(jobOpening.getJobReference());
                            activeJobOpeningsDetail.add("Job Opening: " + jobOpening.getJobReference() + " " + jobOpening.getTitle() + "\n"
                                    + "Active Since: " + jobOpening.getStartDate().toString() + "\n"
                                    + "# Applicants: " + qtyApplicants + " candidates applied\n");
                        }
                        outputStream.write(TcpMessage.buildTcpMessageRESPONSECustomerAssignedJobOpenings(activeJobOpeningsDetail));
                    }
                } else {
                    ArrayList<String> invalidCustomer = new ArrayList<>();
                    invalidCustomer.add("You have not yet enrolled as a customer in any job opening.");
                    outputStream.write(TcpMessage.buildTcpMessageERR(invalidCustomer));
                }
                break;
            case TESTING:
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
