package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class EditJobOpeningController {
    JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public JobOpening getJobOpening(String jobReference) {
        JobOpening jobOpening = repo.ofIdentity(jobReference).get();
        return jobOpening;
    }

    public boolean updateJopOpening(JobOpening jobOpening) {
        return repo.update(jobOpening);
    }
}
