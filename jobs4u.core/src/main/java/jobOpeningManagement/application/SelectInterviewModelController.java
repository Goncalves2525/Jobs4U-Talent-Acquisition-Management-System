package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import plugins.Plugin;
import plugins.PluginLoader;

import java.util.List;



public class SelectInterviewModelController {
    JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();
    PluginLoader pluginLoader = new PluginLoader();
    String INTERVIEW_PLUGINS_DIRECTORY = "plugins/interview/jar";

    public JobOpening findJobOpeningById(String id){
        return repo.ofIdentity(id).get();
    }

    public boolean checkIfJobOpeningHasInterviewModel(JobOpening jobOpening){
        return jobOpening.checkIfJobOpeningHasInterviewModel();
    }

    public List<Plugin> getAllInterviewModels(){
        return pluginLoader.loadPlugins(INTERVIEW_PLUGINS_DIRECTORY);
    }

    public boolean associateInterviewModelToJobOpening(JobOpening jobOpening, String interviewModelPath){
        boolean success = false;
        success = jobOpening.associateInterviewModelToJobOpening(interviewModelPath);
        if(success){
            repo.update(jobOpening);
            return true;
        }
        return false;
    }

}
