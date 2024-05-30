package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.domain.dto.WordCount;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.HashMap;

public class ListWordCountController {

    ApplicationRepository repo = PersistenceContext.repositories().applications();

    public WordCount[] findTopWordCountMap(int topNumber, Application application) {
        String path = application.getApplicationFilesPath();
        HashMap<String, WordCount> map = CountingWordsService.countWordsOnFiles(path);
        return OrderingService.makeTopList(topNumber, map);
    }
}
