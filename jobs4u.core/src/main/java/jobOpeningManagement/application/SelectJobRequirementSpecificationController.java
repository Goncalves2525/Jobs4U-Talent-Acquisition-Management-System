package jobOpeningManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import plugins.Plugin;
import plugins.PluginLoader;

import java.util.List;

public class SelectJobRequirementSpecificationController {
    JobOpeningRepository repo;
    PluginLoader pluginLoader = new PluginLoader();
    String JOBREQUIREMENTSPECIFICATION_PLUGINS_DIRECTORY = "plugins/jobRequirements/jar";

    public SelectJobRequirementSpecificationController(JobOpeningRepository repo) {
        this.repo = repo;
    }

    public JobOpening findJobOpeningById(String id){
        return repo.ofIdentity(id).get();
    }


    public boolean checkIfJobOpeningHasJobRequirementSpecification(JobOpening jobOpening) {
        return jobOpening.checkIfJobOpeningHasJobRequirementSpecification();
    }

    public List<Plugin> getAllJobRequirementSpecification(){
        return pluginLoader.loadPlugins(JOBREQUIREMENTSPECIFICATION_PLUGINS_DIRECTORY);
    }

    public boolean associateJobRequirementSpecificationToJobOpening(JobOpening jobOpening, String allJobRequirementSpecification){
        boolean success = false;
        success = jobOpening.associateJobRequirementSpecificationToJobOpening(allJobRequirementSpecification);
        if(success){
            repo.update(jobOpening);
            return true;
        }
        return false;
    }
}
