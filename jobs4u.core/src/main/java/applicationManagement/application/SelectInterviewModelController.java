package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import plugins.PluginLoader;

import java.util.List;



public class SelectInterviewModelController {
    ApplicationRepository repo = PersistenceContext.repositories().applications();
    PluginLoader pluginLoader = new PluginLoader();
    String INTERVIEW_PLUGINS_DIRECTORY = "plugins/interview/jar";

    public Application findApplicationById(String id){
        return repo.ofIdentity(id).get();
    }

    public boolean checkIfApplicationHasInterviewModel(Application application){
        return application.checkIfApplicationHasInterviewModel();
    }

    public List<Object> getAllInterviewModels(){
        return pluginLoader.loadPlugins(INTERVIEW_PLUGINS_DIRECTORY);
    }

    public boolean associateInterviewModelToApplication(Application application, Object interviewModel){
        return application.associateInterviewModelToApplication(interviewModel);
    }

}
