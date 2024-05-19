package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class EditJobOpeningController {
    JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public JobOpening getJobOpening(String id) {
        JobOpening jobOpening = repo.ofIdentity(id).get();
        return jobOpening;
    }

    public boolean updateJopOpening(JobOpening jobOpening) {
        return repo.update(jobOpening);
    }
}
