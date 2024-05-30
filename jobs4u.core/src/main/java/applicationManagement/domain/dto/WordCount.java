package applicationManagement.domain.dto;

import lombok.Getter;

import java.util.ArrayList;

public class WordCount {

    @Getter
    String word;
    @Getter
    int wordCount;
    @Getter
    ArrayList<String> files;

    public WordCount(String word) {
        this.word = word;
        this.wordCount = 1;
        this.files = new ArrayList<>();
    }

    public void addOneToWordCount() {
        this.wordCount = this.wordCount + 1;
    }

    public void addManyToWordCount(int qty) {
        this.wordCount = this.wordCount + qty;
    }

    public void addToFilesList(String fileName) {
        this.files.add(fileName);
    }
}
