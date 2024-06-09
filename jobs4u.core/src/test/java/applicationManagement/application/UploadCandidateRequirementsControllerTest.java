package applicationManagement.application;

import jobOpeningManagement.application.UploadCandidateRequirementsController;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UploadCandidateRequirementsControllerTest {

    @Mock
    private CandidateRepository candidateRepository;
    private UploadCandidateRequirementsController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = spy(new UploadCandidateRequirementsController(candidateRepository));
    }

    @Test
    void testUploadCandidateRequirementsFile_CandidateDoesNotExist() {
        // Arrange
        when(candidateRepository.ofIdentity("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act
        boolean result = controller.uploadCandidateRequirementsFile("nonexistent@example.com", "path/to/file.txt");

        // Assert
        assertFalse(result);
    }

    @Test
    void testUploadCandidateRequirementsFile_InvalidFile() {
        // Arrange
        Candidate candidate = new Candidate("test@example.com", "123456789", "Name");
        when(candidateRepository.ofIdentity("test@example.com")).thenReturn(Optional.of(candidate));
        doReturn(false).when(controller).isValidFile("path/to/invalid/file.txt");

        // Act
        boolean result = controller.uploadCandidateRequirementsFile("test@example.com", "path/to/invalid/file.txt");

        // Assert
        assertFalse(result);
    }

    @Test
    void testUploadCandidateRequirementsFile_SuccessfulUpload() {
        // Arrange
        Candidate candidate = new Candidate("test@example.com", "123456789", "Name");
        when(candidateRepository.ofIdentity("test@example.com")).thenReturn(Optional.of(candidate));
        doReturn(true).when(controller).isValidFile("path/to/valid/file.txt");

        // Act
        boolean result = controller.uploadCandidateRequirementsFile("test@example.com", "path/to/valid/file.txt");

        // Assert
        assertTrue(result);
        assertEquals("path/to/valid/file.txt", candidate.requirementsFilePath());
        verify(candidateRepository, times(1)).save(candidate);
    }

    @Test
    void testUploadCandidateRequirementsFile_SaveException() {
        // Arrange
        Candidate candidate = new Candidate("test@example.com", "123456789", "Name");
        when(candidateRepository.ofIdentity("test@example.com")).thenReturn(Optional.of(candidate));
        doReturn(true).when(controller).isValidFile("path/to/valid/file.txt");
        doThrow(new RuntimeException("Save error")).when(candidateRepository).save(candidate);

        // Act
        boolean result = controller.uploadCandidateRequirementsFile("test@example.com", "path/to/valid/file.txt");

        // Assert
        assertFalse(result);
    }

    @Test
    void testUploadCandidateRequirementsFileForNonExistentCandidate() {
        when(candidateRepository.ofIdentity(eq("nonexistent@example.com"))).thenReturn(Optional.empty());
        boolean result = controller.uploadCandidateRequirementsFile("nonexistent@example.com", "path/to/file.txt");
        assertFalse(result);
    }
}
