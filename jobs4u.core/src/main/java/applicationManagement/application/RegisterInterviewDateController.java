package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import java.util.Date;
import java.util.Optional;


public class RegisterInterviewDateController {
    ApplicationRepository repo;

    public RegisterInterviewDateController(ApplicationRepository repo) {
        this.repo = repo;
    }

    public Optional<Application> findApplicationById(String id){
        return repo.ofIdentity(id);
    }

    public boolean checkIfApplicationHasInterviewDate(Application application){
        return application.checkIfApplicationHasInterviewDate();
    }

    public boolean registerInterviewDateToApplication(Application application, Date interviewDate){
        if (interviewDate.before(new Date())) {
            // Interview date is in the past, reject it
            return false;
        }
        boolean success = application.registerInterviewDateToApplication(interviewDate);
        if(success){
            repo.update(application);
            return true;
        }
        return false;
    }

}
