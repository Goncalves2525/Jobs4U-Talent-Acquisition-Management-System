package applicationManagement.application;

import infrastructure.persistance.PersistenceContext;
import applicationManagement.domain.Application;
import applicationManagement.domain.dto.ApplicationDTO;
import applicationManagement.repositories.ApplicationRepository;

import java.util.Date;


public class RegisterApplicationController {
    private ApplicationRepository repo = PersistenceContext.repositories().applications();


    public boolean registerApplication(ApplicationDTO dto) {
        Application application = new Application(dto.jobReference(),dto.candidate(),dto.jobOpening(), dto.status(),dto.applicationDate(),dto.comment(),dto.interviewModel(),dto.filePath(),dto.applicationFilesPath());
        application = repo.save(application);
        return application != null;
    }

    public Application findApplicationById(String id){
        return repo.ofIdentity(id).get();
    }

}
