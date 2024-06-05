package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.domain.dto.WordCount;
import applicationManagement.domain.dto.WordCountMap;
import applicationManagement.repositories.ApplicationRepository;
import console.ConsoleUtils;
import infrastructure.persistance.PersistenceContext;

public class ListWordCountController {

    ApplicationRepository repo = PersistenceContext.repositories().applications();

    public WordCount[] findTopWordCountMap(int topNumber, Application application) {
        String path = application.getApplicationFilesPath();
        WordCountMap map = new CountingWordsService().countWordsOnFiles(path);

        // TESTING COUNTING WORDS SERVICE BLOCK
        /*
        ConsoleUtils.readLineFromConsole("Map created. I will now print all the words.\nPress ENTER to continue...");
        map.printWordCountMap();
        ConsoleUtils.readLineFromConsole("Press ENTER to continue...");
        */
        // TESTING COUNTING WORDS SERVICE BLOCK

        return new OrderingService().makeTopList(topNumber, map);
    }
}
