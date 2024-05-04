package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import plugins.Plugin;
import plugins.PluginLoader;

import java.util.List;

public class SelectJobRequirementSpecificationController {

    ApplicationRepository repo = PersistenceContext.repositories().applications();
    PluginLoader pluginLoader = new PluginLoader();
    String JOBREQUIREMENTSPECIFICATION_PLUGINS_DIRECTORY = "plugins/JobRequirements/jar";

    public Application findApplicationById(String id){
        return repo.ofIdentity(id).get();
    }


    public boolean checkIfApplicationHasJobRequirementSpecification(Application application) {
        return application.checkIfApplicationHasJobRequirementSpecification();
    }

    public List<Plugin> getAllJobRequirementSpecification(){
        return pluginLoader.loadPlugins(JOBREQUIREMENTSPECIFICATION_PLUGINS_DIRECTORY);
    }

    public boolean associateJobRequirementSpecificationToApplication(Application application, Object allJobRequirementSpecification){
        boolean success = false;
        success = application.associateJobRequirementSpecificationToApplication(allJobRequirementSpecification);
        if(success){
            repo.update(application);
            return true;
        }
        return false;
    }
}
