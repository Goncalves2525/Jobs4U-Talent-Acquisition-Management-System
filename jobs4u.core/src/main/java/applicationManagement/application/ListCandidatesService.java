package applicationManagement.application;

import eapli.framework.application.ApplicationService;
import infrastructure.persistance.PersistenceContext;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;

@ApplicationService
public class ListCandidatesService {
    private CandidateRepository candidateRepository = PersistenceContext.repositories().candidates();

    public Iterable<Candidate> allCandidates() {
        return candidateRepository.findAll();
    }
}
