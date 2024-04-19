package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import jobOpeningManagement.repositories.JobOpeningRepository;


public class RegisterJobOpeningController {

    private JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public void registerJobOpening(JobOpeningDTO dto) {
        JobOpening jobOpening = new JobOpening(dto.title(), dto.contractType(), dto.mode(), dto.address(), dto.company(), dto.numberOfVacancies(), dto.description(), dto.requirements(), dto.state());
        repo.save(jobOpening);
    }
}
