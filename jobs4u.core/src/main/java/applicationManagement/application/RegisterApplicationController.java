package applicationManagement.application;

import infrastructure.persistance.PersistenceContext;
import applicationManagement.domain.Application;
import applicationManagement.domain.dto.ApplicationDTO;
import applicationManagement.repositories.ApplicationRepository;


public class RegisterApplicationController {
    private ApplicationRepository repo = PersistenceContext.repositories().applications();


    public boolean registerApplication(ApplicationDTO dto) {
        //Application application = new Application(dto.title(), dto.contractType(), dto.mode(), dto.address(), dto.company(), dto.numberOfVacancies(), dto.description(), dto.requirements());
        Application application = new Application(dto.jobReference(),dto.candidate(),dto.jobOpening());
        application = repo.save(application);
        return application != null;
    }

}
