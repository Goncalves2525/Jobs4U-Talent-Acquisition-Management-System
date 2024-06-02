import console.ConsoleUtils;
import fus.ClientMailFetcher;
import fus.ClientNotificationFetcher;
import fus.Server;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class FollowUpServerApp {

    // NO MENU VERSION
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

        do {
            // Server
            Server.run();
        } while(ConsoleUtils.confirm("Do you want to continue? (y/n)"));

        // Close all threads
        try {
            for (Thread thread : threadArrayList) {
                // TODO: signal thread to end!
                thread.join();
            }
            ConsoleUtils.showMessageColor("Closing Follow-Up Server.", AnsiColor.CYAN);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace(); // TESTING
        }
    }
}
