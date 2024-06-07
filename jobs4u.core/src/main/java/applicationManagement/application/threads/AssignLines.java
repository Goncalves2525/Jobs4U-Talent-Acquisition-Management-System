package applicationManagement.application.threads;

import applicationManagement.domain.dto.WordCountMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AssignLines implements Runnable {

    private final int NUMBER_OF_LINES = 50; // Number of lines per thread / block of words
    private File file;
    private WordCountMap map;
    private final Object fileLock;

    public AssignLines(File file, WordCountMap map, Object fileLock) {
        this.file = file;
        this.map = map;
        this.fileLock = fileLock;
    }

    /**
     * Assigns a set of words (X number of lines from the file) to a thread.
     */
    @Override
    public void run() {
        ArrayList<Thread> countWordsThreadArrayList = new ArrayList<>(); // Array of threads

        try {
            // Scan file
            Scanner sc = new Scanner(file);

            // Libertar o lock
            synchronized (fileLock) {
                fileLock.notify();
            }

            // Assign a block of text to each new thread created
            while (sc.hasNextLine()) {
                StringBuilder blockOfText = new StringBuilder();
                for (int idx = 0; idx < NUMBER_OF_LINES; idx++) {
                    if (sc.hasNextLine()) {
                        blockOfText.append(sc.nextLine());
                        blockOfText.append(" ");
                    } else {
                        break;
                    }
                }
                Thread thread = new Thread(new CountWords(map, blockOfText, file.getName()));
                thread.start();
                countWordsThreadArrayList.add(thread);
            }

            // Wait for all threads to finish
            for (Thread thread : countWordsThreadArrayList) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace(); // TESTING
                }
            }
        } catch (IOException error) {
            error.printStackTrace(); // TESTING
        }
    }
}
