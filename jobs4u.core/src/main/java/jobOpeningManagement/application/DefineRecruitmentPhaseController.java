package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class DefineRecruitmentPhaseController {

    JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public JobOpening findJobOpeningById(String id){
        return repo.ofIdentity(id).get();
    }
}
