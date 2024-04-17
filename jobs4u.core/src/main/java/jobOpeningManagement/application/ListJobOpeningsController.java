package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class ListJobOpeningsController {
    private JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public void listJobOpenings() {
        repo.findAll().forEach(System.out::println);
    }
}
