package jobOpeningManagement.domain;

import appUserManagement.domain.Email;
import jobOpeningManagement.application.SelectJobRequirementSpecificationController;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class selectJobRequirementSpecificatonControllerTest {

    private SelectJobRequirementSpecificationController controller;

    @Mock
    private JobOpeningRepository repo;

    private JobOpening hasjobOpeningRequirement;
    private JobOpening hasNotjobOpeningRequirement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new SelectJobRequirementSpecificationController(repo);
        Requirements requirements = new Requirements("DevOps");
        Address companyAddress = new Address("Rua dos Testes", "Test", "1234");
        Email companyEmail = Email.valueOf("geral@company.com");
        CompanyCode companyCode = new CompanyCode("123");
        Customer company = new Customer(companyCode, "Company", companyEmail, companyAddress);
        hasjobOpeningRequirement = new JobOpening("DevOps", ContractType.FULL_TIME, JobMode.HYBRID, companyAddress, company, 1, "teste","hh","teste", requirements);
        hasNotjobOpeningRequirement = new JobOpening("DevOps", ContractType.FULL_TIME, JobMode.HYBRID, companyAddress, company, 1, null,"","teste2", requirements);

    }

    @Test
    public void testCheckIfJobOpeningHasJobRequirementSpecification_True() {
        // Arrange
        when(repo.ofIdentity("testId")).thenReturn(Optional.of(hasjobOpeningRequirement));

        // Act
        boolean hasRequirementSpecification = controller.checkIfJobOpeningHasJobRequirementSpecification(hasjobOpeningRequirement);

        // Assert
        assertTrue(hasRequirementSpecification);
    }

    @Test
    public void testCheckIfJobOpeningHasJobRequirementSpecification_False() {
        when(repo.ofIdentity("testId")).thenReturn(Optional.of(hasNotjobOpeningRequirement));

        // Act
        boolean hasRequirementSpecification = controller.checkIfJobOpeningHasJobRequirementSpecification(hasNotjobOpeningRequirement);

        // Assert
        assertFalse(hasRequirementSpecification);
    }

    @Test
    void testAssociateJobRequirementSpecificationToJobOpening_Success() {
        JobOpening jobOpening = mock(JobOpening.class);

        SelectJobRequirementSpecificationController controller = new SelectJobRequirementSpecificationController(repo);

        when(jobOpening.associateJobRequirementSpecificationToJobOpening(anyString())).thenReturn(true);

        boolean result = controller.associateJobRequirementSpecificationToJobOpening(jobOpening, "testSpecification");

        // Verify that the repository's update method was called
        verify(repo, times(1)).update(jobOpening);

        assertTrue(result);
    }

    @Test
    void testAssociateJobRequirementSpecificationToJobOpening_Failure() {
        JobOpeningRepository repo = mock(JobOpeningRepository.class);

        JobOpening jobOpening = mock(JobOpening.class);

        SelectJobRequirementSpecificationController controller = new SelectJobRequirementSpecificationController(repo);

        when(jobOpening.associateJobRequirementSpecificationToJobOpening(anyString())).thenReturn(false);

        boolean result = controller.associateJobRequirementSpecificationToJobOpening(jobOpening, "testSpecification");

        verify(repo, never()).update(jobOpening);

        assertFalse(result);
    }
}
