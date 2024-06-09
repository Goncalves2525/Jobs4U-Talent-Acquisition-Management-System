package bootstrap;

import appUserManagement.application.SignUpController;
import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import console.ConsoleUtils;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.application.ListCustomersService;
import jobOpeningManagement.application.RegisterCustomerController;
import jobOpeningManagement.application.RegisterJobOpeningController;
import jobOpeningManagement.domain.*;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.repositories.JobOpeningRepository;
import textformat.AnsiColor;

import java.util.Optional;

public class JobOpeningsBootstrapper {

    CustomerRepository customerRepository=PersistenceContext.repositories().customers();
    SignUpController signUpController = new SignUpController();
    RegisterCustomerController registerCustomerController = new RegisterCustomerController(customerRepository);
    ListCustomersService listCustomersService = new ListCustomersService();
    RegisterJobOpeningController ctrl = new RegisterJobOpeningController();
    JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();

    public JobOpeningsBootstrapper() {
    }

    public void execute() {
        ConsoleUtils.showMessageColor("** Job Openings created **", AnsiColor.CYAN);

        // creater customer email
        Email email1 = Email.valueOf("company1@mail.pt");
        Email email2 = Email.valueOf("company2@mail.pt");
        Email email3 = Email.valueOf("ibm@mail.pt");

        // create customer user
        Optional<String> pwd1 = signUpController.signUp(email1, Role.CUSTOMER);
        Optional<String> pwd2 = signUpController.signUp(email2, Role.CUSTOMER);
        Optional<String> pwd3 = signUpController.signUp(email3, Role.CUSTOMER);

        // return customer access details
        System.out.println("User: " + email1.toString() + " | Password: " + pwd1.get());
        System.out.println("User: " + email2.toString() + " | Password: " + pwd2.get());
        System.out.println("User: " + email3.toString() + " | Password: " + pwd3.get());

        // create job opening
        String jobReference1 = "ref-001";
        String title1 = "DevOps";
        ContractType contractType1 = ContractType.FULL_TIME;
        JobMode mode1 = JobMode.HYBRID;
        Address address1 = new Address("Rua arruada", "Porto", "2000");
            // save customer
        registerCustomerController.registerCustomer(new CompanyCode("code1"), "Company1",  email1, address1);
        Customer company1 = listCustomersService.customerByCode(new CompanyCode("code1")).get();
        int numberOfVacancies1 = 5;
        String description1 = "description1";
        Requirements requirements1 = new Requirements("require1");
        RecruitmentState state1 = RecruitmentState.APPLICATION;
        JobOpeningDTO dto1 = new JobOpeningDTO(jobReference1, title1, contractType1, mode1, address1,
                company1, numberOfVacancies1, description1, requirements1, state1);
        ctrl.registerJobOpeningWithPhase(dto1);

        // print job opening data
        System.out.println(dto1.toString());

        // create job opening
        String jobReference2 = "ref-002";
        String title2 = "Recruiter";
        ContractType contractType2 = ContractType.FULL_TIME;
        JobMode mode2 = JobMode.ON_SITE;
        Address address2 = new Address("Rua arruada", "Porto", "2000");
            // save customer
        registerCustomerController.registerCustomer(new CompanyCode("code2"), "Company2",  email2, address2);
        Customer company2 = listCustomersService.customerByCode(new CompanyCode("code2")).get();
        int numberOfVacancies2 = 2;
        String description2 = "description2";
        Requirements requirements2 = new Requirements("require2");
        RecruitmentState state2 = RecruitmentState.APPLICATION;
        JobOpeningDTO dto2 = new JobOpeningDTO(jobReference2, title2, contractType2, mode2, address2,
                company2, numberOfVacancies2, description2, requirements2, state2);
        ctrl.registerJobOpeningWithPhase(dto2);

        // print job opening data
        System.out.println(dto2.toString());

        // create job opening
        String jobReference3 = "IBM-000123";
        String title3 = "Data 123 Technician";
        ContractType contractType3 = ContractType.FULL_TIME;
        JobMode mode3 = JobMode.ON_SITE;
        Address address3 = new Address("Rua arruada", "Porto", "3000");
        // save customer
        registerCustomerController.registerCustomer(new CompanyCode("code3"), "IBM",  email3, address3);
        Customer company3 = listCustomersService.customerByCode(new CompanyCode("code3")).get();
        int numberOfVacancies3 = 1;
        String description3 = "description3";
        Requirements requirements3 = new Requirements("require3");
        RecruitmentState state3 = RecruitmentState.RESULT;
        JobOpeningDTO dto3 = new JobOpeningDTO(jobReference3, title3, contractType3, mode3, address3,
                company3, numberOfVacancies3, description3, requirements3, state3);
        ctrl.registerJobOpeningWithPhase(dto3);
        repo.addInterviewModelPlugin("code3-0", "plugins/interviews/jar/job7interview.jar");

        // print job opening data
        System.out.println(dto3.toString());
    }
}
