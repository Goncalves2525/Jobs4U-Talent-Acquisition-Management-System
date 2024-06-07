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

    public void addToFilesList(String fileName) { this.files.add(fileName); }

    public void addManyToFilesList(ArrayList<String> files) {
        for(String file : files) {
            if(!this.files.contains(file)){
                addToFilesList(file);
            }
        }
    }

    public void printWord(){
        System.out.printf(" | %30s | %5d | %s\n", this.word, this.wordCount, this.files);
    }
}
