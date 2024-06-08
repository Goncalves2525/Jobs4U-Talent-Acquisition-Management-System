package applicationManagement.application;

import applicationManagement.domain.dto.WordCount;
import applicationManagement.domain.dto.WordCountMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderingService {
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