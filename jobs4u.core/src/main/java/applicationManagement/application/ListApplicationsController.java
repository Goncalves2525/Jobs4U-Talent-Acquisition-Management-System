package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;

public class ListApplicationsController {
    private ApplicationRepository repo = PersistenceContext.repositories().applications();

    public Iterable<Application> listApplications() {
        return repo.findAll();
    }

    public Iterable<Application> listApplicationsOfJobRefence(String jobReference) {
        return repo.ofJobReference(jobReference);
    }
}
