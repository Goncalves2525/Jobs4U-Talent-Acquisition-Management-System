package applicationManagement.application.threads;

import applicationManagement.domain.dto.WordCount;
import applicationManagement.domain.dto.WordCountMap;

public class CountWords implements Runnable {

    private String blockOfLines;
    private WordCountMap map;

    private String blockOfLinesFile;

    public CountWords(WordCountMap map, StringBuilder blockOfLines, String blockOfLinesFile) {
        this.map = map;
        this.blockOfLines = blockOfLines.toString();
        this.blockOfLinesFile = blockOfLinesFile;
    }

    /**
     * Counts each word occurrence per block of words (file lines received).
     * Updates the information on a Map, about word count and what file the word was found.
     */
    @Override
    public void run() {

        // Uppercase words
        String uppercased = blockOfLines.toUpperCase();

        // Find valid words and add to the map
        int idx = 0;
        while (idx < uppercased.length()) {
            boolean newWord = true;
            StringBuilder word = new StringBuilder();
            while (newWord) {
                char letter = uppercased.charAt(idx);
                // if valid letter append to word
                if (letter >= 'A' && letter <= 'Z') {
                    word.append(letter);
                } else {
                    newWord = false;
                }
                idx++;
            }
            if (word.length() > 0) {
                WordCount newCompleteWord = new WordCount(word.toString());
                newCompleteWord.addToFilesList(this.blockOfLinesFile);
                map.addWordCount(newCompleteWord);
            }
        }
    }
}
