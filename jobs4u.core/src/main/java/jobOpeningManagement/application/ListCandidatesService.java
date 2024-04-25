package jobOpeningManagement.application;

import eapli.framework.application.ApplicationService;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.Candidate;
import jobOpeningManagement.repositories.CandidateRepository;

@ApplicationService
public class ListCandidatesService {
    private CandidateRepository candidateRepository = PersistenceContext.repositories().candidates();

    public Iterable<Candidate> allCandidates() {
        return candidateRepository.findAll();
    }
}
