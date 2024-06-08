package applicationManagement.application;

import appUserManagement.domain.Email;
import applicationManagement.domain.Application;
import applicationManagement.domain.*;
import applicationManagement.repositories.ApplicationRepository;
import jobOpeningManagement.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    private RegisterInterviewDateController controller;
    private Application application;

    @Mock
    private ApplicationRepository appRepo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Candidate candidate = new Candidate("joao@email.com", "123456789", "Joao Silva");
        CompanyCode companyCode = new CompanyCode("123");
        Address companyAddress = new Address("Rua dos Testes", "Test", "1234");
        Email companyEmail = Email.valueOf("geral@company.com");
        Customer company = new Customer(companyCode, "Company",companyEmail ,companyAddress);
        Requirements requirements = new Requirements("DevOps");
        JobOpening jobOpening = new JobOpening("DevOps", ContractType.FULL_TIME, JobMode.HYBRID, companyAddress, company, 1, "teste", requirements);

        // Assign the created application to the class-level variable
        application = new Application("JobRef123", candidate
                , jobOpening, ApplicationStatus.SUBMITTED,
                new Date(), "", "", "", "", RequirementsResult.APPROVED);

    }

    @Test
    public void testApplicationStatusChange() {
        // Arrange
        application.changeStatus(ApplicationStatus.PENDING);
        assertEquals(ApplicationStatus.PENDING, application.getStatus());
    }

    @Test
    public void testFindApplicationById() {
        controller=new RegisterInterviewDateController(appRepo);
        Optional<Application> optionalApplication = controller.findApplicationById(String.valueOf(application.getId()));
        if (optionalApplication.isPresent()) {
            Application foundApplication = optionalApplication.get();
            assertNotNull(foundApplication);
            assertEquals(application.getId(), foundApplication.getId());
        }
    }

    @Test
    public void testCheckIfApplicationHasInterviewDate_NoInterviewDate() {
        controller=new RegisterInterviewDateController(appRepo);
        boolean hasInterviewDate = controller.checkIfApplicationHasInterviewDate(application);
        assertFalse(hasInterviewDate);
    }

    @Test
    public void testCheckIfApplicationHasInterviewDate_WithInterviewDate() {
        Date interviewDate = new Date();
        application.registerInterviewDateToApplication(interviewDate);
        controller=new RegisterInterviewDateController(appRepo);
        boolean hasInterviewDate = controller.checkIfApplicationHasInterviewDate(application);
        assertTrue(hasInterviewDate);
    }


    // Test for Acceptance Criteria 1014.1: Validating the date and time of the interview
    @Test
    public void testValidateInterviewDateAndTime() {
        Date invalidInterviewDate = new Date(System.currentTimeMillis() - 100000); // A past date
        controller=new RegisterInterviewDateController(appRepo);
        boolean success = controller.registerInterviewDateToApplication(application, invalidInterviewDate);
        assertFalse("The system should not allow recording an invalid interview date and time.", success);

        Date validInterviewDate = new Date(System.currentTimeMillis() + 100000); // A future date
        success = controller.registerInterviewDateToApplication(application, validInterviewDate);
        assertTrue("The system should allow recording a valid interview date and time.", success);
    }

}
