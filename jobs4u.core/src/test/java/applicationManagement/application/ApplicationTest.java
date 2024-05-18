package applicationManagement.application;

import appUserManagement.domain.Email;
import applicationManagement.domain.Application;
import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.Candidate;
import jobOpeningManagement.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    @Test
    public void testApplicationStatusChange() {
        // Arrange
        Candidate candidate = new Candidate("joao@email.com", "123456789", "Joao Silva");
        CompanyCode companyCode = new CompanyCode("123");
        Address companyAddress = new Address("Rua dos Testes", "Test", "1234");
        Email companyEmail = Email.valueOf("geral@company.com");
        Customer company = new Customer(companyCode, "Company",companyEmail ,companyAddress);
        Requirements requirements = new Requirements("DevOps");
        JobOpening jobOpening = new JobOpening("DevOps", ContractType.FULL_TIME, JobMode.HYBRID, companyAddress, company, 1, "teste", requirements);
        Application application = new Application("JobRef123", candidate
                , jobOpening, ApplicationStatus.SUBMITTED,
        new Date(), "", "", "", "");

        application.changeStatus(ApplicationStatus.PENDING);

        assertEquals(ApplicationStatus.PENDING, application.getStatus());
    }

}
