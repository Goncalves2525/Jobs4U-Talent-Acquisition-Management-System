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
                new Date(), "", null, "", "", RequirementsResult.APPROVED);
    }

    @Test
    void ensureApplicationDoesNotHaveInterviewModel() {
        // Act
        boolean result = application.checkIfApplicationHasInterviewModel();
        // Assert
        Assertions.assertFalse(result);
    }
}
