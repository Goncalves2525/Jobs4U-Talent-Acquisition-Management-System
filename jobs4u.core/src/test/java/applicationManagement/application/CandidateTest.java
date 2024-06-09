package applicationManagement.application;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.CandidateDTO;
import applicationManagement.repositories.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CandidateTest {

    @Mock
    private ListCandidatesService listCandidatesService;

    @Mock
    private CandidateRepository candidateRepository;

    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;
    private CandidateDTO candidateDTO1;
    private CandidateDTO candidateDTO2;
    private CandidateDTO candidateDTO3;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        listCandidatesService = new ListCandidatesService(candidateRepository);
        candidate1 = new Candidate("1212039@isep.ipp.pt","911111111","Diogo");
        candidate2 = new Candidate("1212044@isep.ipp.pt","911111112","Paulo");
        candidate3 = new Candidate("1212047@isep.ipp.pt","911111113","Rafael");
        candidateDTO1 = new CandidateDTO(candidate1.name(),candidate1.email(),candidate1.phoneNumber());
        candidateDTO2 = new CandidateDTO(candidate2.name(),candidate2.email(),candidate2.phoneNumber());
        candidateDTO3 = new CandidateDTO(candidate3.name(),candidate3.email(),candidate3.phoneNumber());
    }

    @Test
    void ensureCandidateListSizeIsCorrect() {
        Iterable<Candidate> candidates = listCandidatesService.allCandidates();
        assertNotNull(candidates);
    }

    @Test
    void testValidCandidateRegistration() {
        String name = "John Doe";
        String email = "john@example.com";
        String phone = "1234567890";

        CandidateDTO candidateDTO = new CandidateDTO(name, email, phone);

        assertEquals(name, candidateDTO.getName());
        assertEquals(email, candidateDTO.getEmail());
        assertEquals(phone, candidateDTO.getPhone());
    }



}
