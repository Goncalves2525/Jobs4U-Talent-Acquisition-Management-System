package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.Application;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.dto.ApplicationDTO;
import jobOpeningManagement.repositories.ApplicationRepository;


public class RegisterApplicationController {
    private ApplicationRepository repo = PersistenceContext.repositories().applications();


    public boolean registerApplication(ApplicationDTO dto) {
        //Application application = new Application(dto.title(), dto.contractType(), dto.mode(), dto.address(), dto.company(), dto.numberOfVacancies(), dto.description(), dto.requirements());
        Application application = new Application(dto.jobReference(),dto.candidate(),dto.jobOpening());
        application = repo.save(application);
        return application != null;
    }

}
