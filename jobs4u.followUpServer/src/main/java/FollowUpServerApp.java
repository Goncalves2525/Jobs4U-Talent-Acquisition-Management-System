import console.ConsoleUtils;
import fus.ClientMailFetcher;
import fus.ClientNotificationFetcher;
import fus.Server;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class FollowUpServerApp {

    public static void main(String[] args) {

        // Initialize array of threads
        ArrayList<Thread> threadArrayList = new ArrayList<>();

        // Client Mail Fetcher
        Thread threadClientMailFetcher = new Thread(new ClientMailFetcher());
        threadClientMailFetcher.start();
        threadArrayList.add(threadClientMailFetcher);

        // Client Notification Fetcher
        Thread threadClientNotificationFetcher = new Thread(new ClientNotificationFetcher());
        threadClientNotificationFetcher.start();
        threadArrayList.add(threadClientNotificationFetcher);

        // Server
        boolean serverUp = true;
        do {
            Thread threadServer = new Thread(new Server());
            threadServer.start();
            while (threadServer.isInterrupted()) {
                serverUp = false;
            }
            try {
                threadServer.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace(); // TESTING
                break;
            }
        } while (serverUp);

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
