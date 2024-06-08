package fus;

import appUserManagement.domain.Role;
import applicationManagement.domain.ApplicationStatus;
import console.ConsoleUtils;
import jpa.JpaNotificationRepository;
import notificationManagement.domain.EmailNotificationStatus;
import notificationManagement.domain.Notification;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientMailFetcher implements Runnable {

    String TESTING_EMAIL_ADDRESS = "1212047@isep.ipp.pt";

    @Override
    public void run() {

        String hostname = "frodo.dei.isep.ipp.pt";
        int port = 25;
        int sleepingTime = 30000; // 30sec (minimum)

        while (!Thread.interrupted()) {

            // Thread sleeping for a minimum amount of time
            try {
                Thread.sleep(sleepingTime);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace(); // TESTING
                return;
            }

            ArrayList<Notification> notifications = new JpaNotificationRepository().findAllReadyToEmail();

            // Loop through all the notifications pending, send e-mail and change persistence status
            for (Notification notification : notifications) {

                // Send e-mail to all pending notifications on the list
                try (Socket socket = new Socket(hostname, port)) {

                    // Setting up variables
                    OutputStream output = socket.getOutputStream();
                    InputStream input = socket.getInputStream();
                    PrintWriter outputWriter;
                    BufferedReader inputReader;

                    inputReader = new BufferedReader(new InputStreamReader(input));
                    System.out.println("Server says: " + inputReader.readLine());

                    // Dialogue sequence start
                    outputWriter = new PrintWriter(output, true);
                    outputWriter.println("HELO frodo.dei.isep.ipp.pt");

                    inputReader = new BufferedReader(new InputStreamReader(input));
                    System.out.println("Server says: " + inputReader.readLine());

                    // Send e-mail and update notification e-mail status accordingly
                    if (sendEmail(socket, output, input, notification)) {
                        notification.changeEmailNotificationStatus(EmailNotificationStatus.SENT);
                        new JpaNotificationRepository().update(notification);
                    } else {
                        notification.changeEmailNotificationStatus(EmailNotificationStatus.FAILED);
                        new JpaNotificationRepository().update(notification);
                    }

                    // Dialogue sequence end
                    outputWriter = new PrintWriter(output, true);
                    outputWriter.println("QUIT");

                    inputReader = new BufferedReader(new InputStreamReader(input));
                    System.out.println("Server says: " + inputReader.readLine());

                } catch (IOException ex) {
                    System.out.println("Client exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

        }
    }

    private boolean sendEmail(Socket socket, OutputStream output, InputStream input, Notification notification)
            throws IOException {

        // Setting up variables
        PrintWriter outputWriter;
        BufferedReader inputReader;

        // Building e-mail message based on ROLE
        String emailMessage = null;
        switch (Role.fromRole(notification.getRoleTo().name())) {
            case CANDIDATE:
                // Build email message based on the application status (for HIRED and REJECTED only)
                if (notification.getApplicationStatus().equals(ApplicationStatus.HIRED)) {
                    emailMessage = buildHiredEmailMessage(notification);
                } else if (notification.getApplicationStatus().equals(ApplicationStatus.REJECTED)) {
                    emailMessage = buildRejectedEmailMessage(notification);
                } else {
                    return false;
                }
                break;
            case CUSTOMER:
                emailMessage = buildCustomerMessage(notification);
                break;
            default:
                return false;
        }

        // If placed only for testing purposes
        if (ConsoleUtils.confirm("Are you ready to send e-mail to " + notification.getNotifyTo() + "? (y/n)")) {

            // Dialogue sequence to send e-mail
            outputWriter = new PrintWriter(output, true);
            outputWriter.println("MAIL FROM:<1212047@dei.isep.ipp.pt>");

            inputReader = new BufferedReader(new InputStreamReader(input));
            System.out.println("Server says: " + inputReader.readLine());

            outputWriter = new PrintWriter(output, true);
            outputWriter.println("RCPT TO:<" + TESTING_EMAIL_ADDRESS + ">");
//            outputWriter.println("RCPT TO:<" + notification.getNotifyTo() + ">");

            inputReader = new BufferedReader(new InputStreamReader(input));
            String reply = inputReader.readLine();
            System.out.println("Server says: " + reply);
            if (reply.startsWith("550")) {
                return false;
            }

            outputWriter = new PrintWriter(output, true);
            outputWriter.println("DATA");

            inputReader = new BufferedReader(new InputStreamReader(input));
            System.out.println("Server says: " + inputReader.readLine());

            outputWriter = new PrintWriter(output, true);
            outputWriter.println(emailMessage);

            inputReader = new BufferedReader(new InputStreamReader(input));
            System.out.println("Server says: " + inputReader.readLine());
            return true;
        }
        return false;
    }

    private String buildHiredEmailMessage(Notification notification) {

        String emailTo = notification.getNotifyTo();
        String jobReference = notification.getJobOpeningReference();

        return "From: \"Rafael Carolo\" <1212047@dei.isep.ipp.pt>\n" +
                "To: <" + TESTING_EMAIL_ADDRESS + ">\n" +
                "Subject: Job Ref. " + jobReference + " : CONGRATULATIONS! YOU HAVE BEEN HIRED\n\n" +
                "Hello, \n\n" +
                "Hope to find you well. \n\n" +
                "We are pleased to announce that you have been hired to the Job Opening " + jobReference + ".\n\n" +
                "Welcome to the TEAM.\n\n" +
                "Kind regards.\n" +
                ".\n";
    }

    private String buildRejectedEmailMessage(Notification notification) {

        String emailTo = notification.getNotifyTo();
        String jobReference = notification.getJobOpeningReference();

        return "From: \"Rafael Carolo\" <1212047@dei.isep.ipp.pt>\n" +
                "To: <" + TESTING_EMAIL_ADDRESS + ">\n" +
                "Subject: Job Ref. " + jobReference + " : HOPE TO SEE YOU ON FUTURE RECRUITMENT PROCESSES\n\n" +
                "Hello, \n\n" +
                "Hope to find you well. \n\n" +
                "We hereby inform that we have considered another application to the Job Opening " + jobReference + ".\n\n" +
                "Keep on following our job offers!\n\n" +
                "If you agree, your data will be kept on our database for future recruitment processes.\n\n" +
                "Kind regards.\n" +
                ".\n";
    }

    private String buildCustomerMessage(Notification notification) {

        String emailTo = notification.getNotifyTo();
        String jobReference = notification.getJobOpeningReference();

        ArrayList<Notification> list = new JpaNotificationRepository().findAllByJobReference(jobReference);

        StringBuilder notificationListed = new StringBuilder();
        for (Notification notificationOnList : list) {
            if (notificationOnList.getRoleTo().equals(Role.CANDIDATE)) {
                notificationListed.append(notificationOnList.getNotifyTo() +
                        " | " + notificationOnList.getApplicationStatus() + "\n");
            }
        }

        return "From: \"Rafael Carolo\" <1212047@dei.isep.ipp.pt>\n" +
                "To: <" + TESTING_EMAIL_ADDRESS + ">\n" +
                "Subject: Job Ref. " + jobReference + " : RECRUITMENT STATUS\n\n" +
                "Dear Customer, \n\n" +
                "Hope to find you well. \n\n" +
                "We hereby inform you on the RECRUITMENT STATUS of the Job Opening " + jobReference + ", that has been set to the final phase.\n\n" +
                notificationListed +
                "\n\n" +
                "Kind regards.\n" +
                ".\n";
    }
}