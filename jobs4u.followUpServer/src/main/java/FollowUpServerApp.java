import console.ConsoleUtils;
import fus.ClientMailFetcher;
import fus.Server;
import textformat.AnsiColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class FollowUpServerApp {

    static ServerSocket sock;
    static int port = 1027;

    public static void main(String[] args) {

        // Initialize array of threads
        ArrayList<Thread> threadArrayList = new ArrayList<>();

        // Client Mail Fetcher
        Thread threadClientMailFetcher = new Thread(new ClientMailFetcher());
        threadClientMailFetcher.start();
        threadArrayList.add(threadClientMailFetcher);

        // Client Notification Fetcher
//        Thread threadClientNotificationFetcher = new Thread(new ClientNotificationFetcher());
//        threadClientNotificationFetcher.start();
//        threadArrayList.add(threadClientNotificationFetcher);

        // Server
        try {
            Socket cliSock;
            sock = new ServerSocket(port);
            ConsoleUtils.showMessageColor("Server is listening on port " + port, AnsiColor.CYAN);

            // Keep opening server sockets ready for connection
            while (true) {
                // Waiting for new connection
                ConsoleUtils.showMessageColor("Waiting for new connection.", AnsiColor.CYAN);
                cliSock = sock.accept();
                new Thread(new Server(cliSock)).start();
            }
        } catch(IOException ex) {
            System.out.println("Failed to open server socket"); System.exit(1);
        }

        // Close all threads
        try {
            for (Thread thread : threadArrayList) {
                // signal thread to end!
                thread.join();
            }
            ConsoleUtils.showMessageColor("Closing Follow-Up Server.", AnsiColor.CYAN);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace(); // TESTING
        }
    }
}
