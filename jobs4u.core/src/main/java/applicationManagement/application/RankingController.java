package applicationManagement.application;

import applicationManagement.domain.Candidate;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;

public class RankingController {

    private ApplicationRepository repo = PersistenceContext.repositories().applications();

    public boolean defineRanking(Candidate candidate, String jobReference, int rank) {
        return repo.defineRanking(candidate, jobReference, rank);
    }
}
