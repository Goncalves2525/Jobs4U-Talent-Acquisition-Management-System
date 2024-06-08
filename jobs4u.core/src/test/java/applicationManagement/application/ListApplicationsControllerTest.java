package applicationManagement.application;

import appUserManagement.domain.Email;
import applicationManagement.domain.Application;
import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.RequirementsResult;
import applicationManagement.repositories.ApplicationRepository;
import applicationManagement.repositories.CandidateRepository;
import jobOpeningManagement.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ListApplicationsControllerTest {

    @Mock
    private ApplicationRepository applicationRepository;

    private ListApplicationsController ctrl;

    private Application application;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ctrl = new ListApplicationsController(applicationRepository);
        Candidate candidate = new Candidate("joao@email.com", "123456789", "Joao Silva");
        CompanyCode companyCode = new CompanyCode("123");
        Address companyAddress = new Address("Rua dos Testes", "Test", "1234");
        Email companyEmail = Email.valueOf("geral@company.com");
        Customer company = new Customer(companyCode, "Company", companyEmail, companyAddress);
        Requirements requirements = new Requirements("DevOps");
        JobOpening jobOpening = new JobOpening("DevOps", ContractType.FULL_TIME, JobMode.HYBRID, companyAddress, company, 1, "teste", requirements);
        application = new Application("JobRef123", candidate
                , jobOpening, ApplicationStatus.SUBMITTED,
                new Date(), "", "", "", "", RequirementsResult.APPROVED);
    }

    @Test
    void applicationFound() {
        when(applicationRepository.findAll()).thenReturn(Collections.singletonList(application));

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        System.setOut(new PrintStream(result));

        ctrl.getApplication(application.getId());

        assertTrue(result.toString().contains("Application ID: " + application.getId()));
    }

    @Test
    void applicationNotFound() {
        when(applicationRepository.findAll()).thenReturn(Collections.emptyList());

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        System.setOut(new PrintStream(result));

        ctrl.getApplication(1000L);

        assertTrue(result.toString().contains("No Applications found."));
    }
}