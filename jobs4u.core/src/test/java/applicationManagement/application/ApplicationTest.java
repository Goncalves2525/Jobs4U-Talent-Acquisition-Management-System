package applicationManagement.application;

import appUserManagement.domain.Email;
import applicationManagement.domain.*;
import jobOpeningManagement.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    private RegisterInterviewDateController controller;
    private Application application;

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
        new Date(), "", "", "", RequirementsResult.APPROVED);

        application.changeStatus(ApplicationStatus.PENDING);

        assertEquals(ApplicationStatus.PENDING, application.getStatus());
    }

//    @Test
//    public void testFindApplicationById() {
//        Application foundApplication = controller.findApplicationById(String.valueOf(application.getId()));
//        assertNotNull(foundApplication);
//        assertEquals(application.getId(), foundApplication.getId());
//    }
//
//    @Test
//    public void testCheckIfApplicationHasInterviewDate_NoInterviewDate() {
//        boolean hasInterviewDate = controller.checkIfApplicationHasInterviewDate(application);
//        assertFalse(hasInterviewDate);
//    }
//
//    @Test
//    public void testCheckIfApplicationHasInterviewDate_WithInterviewDate() {
//        Date interviewDate = new Date();
//        application.registerInterviewDateToApplication(interviewDate);
//        boolean hasInterviewDate = controller.checkIfApplicationHasInterviewDate(application);
//        assertTrue(hasInterviewDate);
//    }


    // Test for Acceptance Criteria 1014.1: Validating the date and time of the interview
//    @Test
//    public void testValidateInterviewDateAndTime() {
//        Date invalidInterviewDate = new Date(System.currentTimeMillis() - 100000); // A past date
//        boolean success = controller.registerInterviewDateToApplication(application, invalidInterviewDate);
//        assertFalse("The system should not allow recording an invalid interview date and time.", success);
//
//        Date validInterviewDate = new Date(System.currentTimeMillis() + 100000); // A future date
//        success = controller.registerInterviewDateToApplication(application, validInterviewDate);
//        assertTrue("The system should allow recording a valid interview date and time.", success);
//    }

}
