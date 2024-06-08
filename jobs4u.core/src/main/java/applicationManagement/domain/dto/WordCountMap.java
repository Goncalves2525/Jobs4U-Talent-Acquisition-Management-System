package applicationManagement.domain.dto;

import java.util.HashMap;
import java.util.Iterator;

public class WordCountMap {

    private HashMap<String, WordCount> map = new HashMap<>();

    /**
     * Getter
     * @return map
     */
    public HashMap<String, WordCount> getMap() {
        return map;
    }

    public WordCountMap() {
    }

    /**
     * Method is only accessed one thread at a time.
     * @param wordCount
     */
    public synchronized void addWordCount(WordCount wordCount) {
        if (map.containsKey(wordCount.getWord())) {
            WordCount updated = map.get(wordCount.getWord());
            updated.addManyToWordCount(wordCount.getWordCount());
            updated.addManyToFilesList(wordCount.getFiles());
            map.replace(updated.getWord(), updated);
        } else {
            map.put(wordCount.getWord(), wordCount);
        }

    }

    public void printWordCountMap() {
        int wordNumber = 0;
        Iterator<WordCount> mapValues = map.values().stream().iterator();
        while (mapValues.hasNext()) {
            WordCount word = mapValues.next();
            System.out.printf("%4d", ++wordNumber);
            word.printWord();
        }
    }
}
