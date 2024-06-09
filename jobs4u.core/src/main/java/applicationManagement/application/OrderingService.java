package applicationManagement.application;

import applicationManagement.domain.dto.WordCount;
import applicationManagement.domain.dto.WordCountMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class is responsible for ordering WordCount objects.
 */
public class OrderingService {

    /**
     * This method creates a top list of WordCount objects based on their word count.
     * The list is sorted in descending order of word count.
     *
     * @param topNumber the number of top WordCount objects to include in the list.
     * @param map the WordCountMap object that contains the WordCount objects to be sorted.
     * @return an array of WordCount objects sorted in descending order of word count.
     */
    public static WordCount[] makeTopList(int topNumber, WordCountMap map) {
        WordCount[] list = new WordCount[topNumber];
        List<WordCount> sortedWordCounts = new ArrayList<>(map.getMap().values());
        sortedWordCounts.sort(Comparator.comparingInt(WordCount::getWordCount).reversed());

        for (int i = 0; i < topNumber && i < sortedWordCounts.size(); i++) {
            list[i] = sortedWordCounts.get(i);
        }
        return list;
    }
}