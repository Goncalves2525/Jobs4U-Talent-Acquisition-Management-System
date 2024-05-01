package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import plugins.Plugin;
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

    public List<Plugin> getAllInterviewModels(){
        return pluginLoader.loadPlugins(INTERVIEW_PLUGINS_DIRECTORY);
    }

    public boolean associateInterviewModelToApplication(Application application, Object interviewModel){
        boolean success = false;
        success = application.associateInterviewModelToApplication(interviewModel);
        if(success){
            repo.update(application);
            return true;
        }
        return false;
    }

    public boolean associateInterviewModelPathToApplication(Application application, String interviewModelPath){
        boolean success = false;
        success = application.associateInterviewModelPathToApplication(interviewModelPath);
        if(success){
            repo.update(application);
            return true;
        }
        return false;
    }
}
