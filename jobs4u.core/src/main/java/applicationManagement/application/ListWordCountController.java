package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.domain.dto.WordCount;
import applicationManagement.domain.dto.WordCountMap;
import applicationManagement.repositories.ApplicationRepository;
import console.ConsoleUtils;
import infrastructure.persistance.PersistenceContext;

/**
 * This class is responsible for listing word counts in an application.
 *
 */
public class ListWordCountController {

    ApplicationRepository repo = PersistenceContext.repositories().applications();

    /**
     * This method finds the top word counts in an application.
     * It uses the CountingWordsService to count the words in the application files
     * and the OrderingService to order the word counts.
     *
     * @param topNumber the number of top word counts to return
     * @param application the application to count words in
     * @return an array of WordCount objects representing the top word counts
     */
    public WordCount[] findTopWordCountMap(int topNumber, Application application) {
        String path = application.getApplicationFilesPath();
        WordCountMap map = new CountingWordsService().countWordsOnFiles(path);

        // TESTING COUNTING WORDS SERVICE BLOCK
//        ConsoleUtils.readLineFromConsole("Map created. I will now print all the words.\nPress ENTER to continue...");
//        map.printWordCountMap();
//        ConsoleUtils.readLineFromConsole("Press ENTER to continue...");
        // TESTING COUNTING WORDS SERVICE BLOCK

        return OrderingService.makeTopList(topNumber, map);
    }
}
