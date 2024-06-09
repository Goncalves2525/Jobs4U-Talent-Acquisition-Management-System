package applicationManagement.application;
import appUserManagement.domain.Email;
import applicationManagement.domain.Application;
import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.RequirementsResult;
import jobOpeningManagement.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;

public class SelectInterviewModelTest {

    private Application application;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Candidate candidate = new Candidate("joao@email.com", "123456789", "Joao Silva");
        CompanyCode companyCode = new CompanyCode("123");
        Address companyAddress = new Address("Rua dos Testes", "Test", "1234");
        Email companyEmail = Email.valueOf("geral@company.com");
        Customer company = new Customer(companyCode, "Company", companyEmail, companyAddress);
        Requirements requirements = new Requirements("DevOps");
        JobOpening jobOpening = new JobOpening("DevOps", ContractType.FULL_TIME, JobMode.HYBRID, companyAddress, company, 1, "teste", requirements);
        application = new Application("JobRef123", candidate
                , jobOpening, ApplicationStatus.SUBMITTED,
                new Date(), "", "/path/to/file", "/path/to/application/files", RequirementsResult.APPROVED);
    }

    @Test
    void ensureApplicationDoesNotHaveInterviewModel() {
        // Act
        boolean result = application.checkIfApplicationHasInterviewDate();
        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testAssotiateRequirementResultToApplicationApproved() {
        application.assotiateRequirementResultToApplication(1);

        assertEquals(RequirementsResult.APPROVED, application.getRequirementsResult());
    }

    @Test
    public void testAssotiateRequirementResultToApplicationRejected() {
        application.assotiateRequirementResultToApplication(0);

        assertEquals(RequirementsResult.REJECTED, application.getRequirementsResult());
    }

    @Test
    public void testRegisterInterviewDateToApplication() {
        Date interviewDate = new Date();

        boolean result = application.registerInterviewDateToApplication(interviewDate);

        assertTrue(result);
        assertEquals(interviewDate, application.getInterviewDate());
    }

    @Test
    public void testRegisterInterviewDateToApplicationNullDate() {
        boolean result = application.registerInterviewDateToApplication(null);

        assertTrue(result);
        assertNull(application.getInterviewDate());
    }

    @Test
    public void testChangeRankingNumber() {
        int newRankingNumber = 5;

        application.changeRankingNumber(newRankingNumber);

        assertEquals(newRankingNumber, application.rankNumber().getOrdinal());
    }

    @Test
    public void testChangeRankingNumberNegative() {
        int newRankingNumber = -1;

        application.changeRankingNumber(newRankingNumber);

        assertEquals(newRankingNumber, application.rankNumber().getOrdinal());
    }

    @Test
    public void testChangeRankingNumberZero() {
        int newRankingNumber = 0;

        application.changeRankingNumber(newRankingNumber);

        assertEquals(newRankingNumber, application.rankNumber().getOrdinal());
    }
}
