package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.RecruitmentState;
import plugins.Plugin;
import plugins.PluginLoader;

import java.util.Date;
import java.util.List;


public class RegisterInterviewDateController {
    ApplicationRepository repo = PersistenceContext.repositories().applications();

    public Application findApplicationById(String id){
        return repo.ofIdentity(id).get();
    }

    public boolean checkIfApplicationHasInterviewDate(Application application){
        return application.checkIfApplicationHasInterviewDate();
    }

    public boolean registerInterviewDateToApplication(Application application, Date interviewDate){
        boolean success = false;
        success = application.registerInterviewDateToApplication(interviewDate);
        if(success){
            repo.update(application);
            return true;
        }
        return false;
    }

}
