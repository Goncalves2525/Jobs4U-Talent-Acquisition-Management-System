package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class ListJobOpeningsController {
    private JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public Iterable<JobOpening> listJobOpenings() {
        return repo.findAll();
    }
}
