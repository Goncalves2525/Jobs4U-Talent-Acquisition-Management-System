package applicationManagement.application;

import applicationManagement.application.threads.AssignLines;
import applicationManagement.domain.dto.WordCountMap;

import java.io.*;
import java.util.ArrayList;

public class CountingWordsService {

    public CountingWordsService() {
    }

    /**
     * Assigns work per thread for each file contained on the path received through parameter
     *
     * @param path
     * @return an WordCountMap object.
     */
    public synchronized WordCountMap countWordsOnFiles(String path) {

        WordCountMap map = new WordCountMap();
        ArrayList<Thread> assignLinesThreadArrayList = new ArrayList<>(); // Array of threads
        Object fileLock = new Object();

        // Get list of files within the path and start a thread for each file ending in ".txt"
        File[] fileTest = new File(path).listFiles();
        if (fileTest != null) {

            int fileIdx = 0;
            while (fileIdx < fileTest.length) {

                // Use fileLock to ensure the correct file is passed on to the thread
                try {
                    synchronized (fileLock) {
                        Thread thread = new Thread(new AssignLines(fileTest[fileIdx], map, fileLock));
                        thread.start();
                        assignLinesThreadArrayList.add(thread);
                        fileLock.wait();
                    }
                    fileIdx++;
                } catch (InterruptedException handler) {
                    handler.printStackTrace(); // TESTING
                }
            }

            // Wait for all threads to finish
            for (Thread thread : assignLinesThreadArrayList) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace(); // TESTING
                }
            }
        }

        // Return the map with all words counted on all files
        return map;
    }
}
