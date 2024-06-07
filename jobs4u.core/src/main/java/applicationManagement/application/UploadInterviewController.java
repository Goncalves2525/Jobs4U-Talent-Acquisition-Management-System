package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import plugins.PluginLoader;

public class UploadInterviewController {

    ApplicationRepository repo = PersistenceContext.repositories().applications();
    PluginLoader pluginLoader = new PluginLoader();
    String INTERVIEW_PLUGINS_DIRECTORY = "plugins/interview/jar";

    public Application findApplicationById(String id){
        return repo.ofIdentity(id).get();
    }

    public boolean checkIfApplicationIsPending(Application application) {
        return true;
    }
}
