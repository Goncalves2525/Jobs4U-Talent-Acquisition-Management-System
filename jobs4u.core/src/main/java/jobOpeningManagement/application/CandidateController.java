package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.Application;
import jobOpeningManagement.domain.Candidate;
import jobOpeningManagement.domain.dto.ApplicationDTO;
import jobOpeningManagement.repositories.ApplicationRepository;
import jobOpeningManagement.repositories.CandidateRepository;


public class CandidateController {
    private CandidateRepository repo = PersistenceContext.repositories().candidates();
    private ListCandidatesService svc = new ListCandidatesService();


//    public boolean registerApplication(ApplicationDTO dto) {
//        Application application = new Application(dto.jobReference(),dto.candidate(),dto.jobOpening());
//        application = repo.save(application);
//        return application != null;
//    }

    public Iterable<Candidate> allCandidates() {
        return svc.allCandidates();
    }
}
