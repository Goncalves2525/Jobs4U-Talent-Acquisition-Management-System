package applicationManagement.application;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.CandidateDTO;
import applicationManagement.repositories.CandidateRepository;
import infrastructure.persistance.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CandidateTest {

    private final CandidateRepository repo = PersistenceContext.repositories().candidates();

    @Test
    void ensureCandidateListSizeIsCorrect() {
        Candidate candidate1 = new Candidate("1212039@isep.ipp.pt","911111111","Diogo");
        Candidate candidate2 = new Candidate("1212044@isep.ipp.pt","911111112","Paulo");
        Candidate candidate3 = new Candidate("1212047@isep.ipp.pt","911111113","Rafael");
        CandidateDTO candidateDTO1 = new CandidateDTO(candidate1.name(),candidate1.email(),candidate1.phoneNumber());
        CandidateDTO candidateDTO2 = new CandidateDTO(candidate2.name(),candidate2.email(),candidate2.phoneNumber());
        CandidateDTO candidateDTO3 = new CandidateDTO(candidate3.name(),candidate3.email(),candidate3.phoneNumber());


        ListCandidatesService listCandidatesService = new ListCandidatesService();
        Iterable<Candidate> candidates = listCandidatesService.allCandidates();

    }
}
