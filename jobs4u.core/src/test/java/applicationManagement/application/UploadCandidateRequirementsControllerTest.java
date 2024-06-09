package applicationManagement.application;

import applicationManagement.domain.dto.CandidateDTO;
import eapli.framework.domain.model.AggregateRoot;
import jobOpeningManagement.application.UploadCandidateRequirementsController;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UploadCandidateRequirementsControllerTest {

    private CandidateRepository candidateRepository;
    private UploadCandidateRequirementsController controller = new UploadCandidateRequirementsController();

    // Minimal implementation of CandidateRepository for testing
    private static class TestCandidateRepository implements CandidateRepository {
        private final Map<String, Candidate> store = new HashMap<>();

        @Override
        public Iterable<Candidate> findAll() {
            return store.values();
        }

        @Override
        public Optional<Candidate> ofIdentity(String email) {
            return Optional.ofNullable(store.get(email));
        }

        @Override
        public void delete(Candidate entity) {

        }

        @Override
        public void deleteOfIdentity(String entityId) {

        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public AggregateRoot save(Candidate candidate) {
            store.put(candidate.email(), candidate);
            return null;
        }

        @Override
        public boolean createCandidate(CandidateDTO dto) {
            return false;
        }
    }

    @BeforeEach
    void setUp() {
        candidateRepository = new TestCandidateRepository();
    }

    @Test
    void testUploadValidCandidateRequirementsFile() {
        Candidate candidate = new Candidate("test@example.com","123456789","Name");
        candidateRepository.save(candidate);

        boolean result = controller.uploadCandidateRequirementsFile("test@example.com", "path/to/valid/file.txt");

        assertTrue(result);
        Optional<Candidate> updatedCandidate = candidateRepository.ofIdentity("test@example.com");
        assertTrue(updatedCandidate.isPresent());
        assertEquals("path/to/valid/file.txt", updatedCandidate.get().requirementsFilePath());
    }

    @Test
    void testUploadCandidateRequirementsFileForNonExistentCandidate() {
        boolean result = controller.uploadCandidateRequirementsFile("nonexistent@example.com", "path/to/file.txt");

        assertFalse(result);
    }
}
