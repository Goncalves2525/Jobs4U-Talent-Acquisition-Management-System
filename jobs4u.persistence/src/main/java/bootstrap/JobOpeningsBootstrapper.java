package bootstrap;

import appUserManagement.application.SignUpController;
import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import console.ConsoleUtils;
import jobOpeningManagement.application.ListCustomersService;
import jobOpeningManagement.application.RegisterCustomerController;
import jobOpeningManagement.application.RegisterJobOpeningController;
import jobOpeningManagement.domain.*;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import textformat.AnsiColor;

import java.util.Optional;

public class JobOpeningsBootstrapper {

    SignUpController signUpController = new SignUpController();
    RegisterCustomerController registerCustomerController = new RegisterCustomerController();
    ListCustomersService listCustomersService = new ListCustomersService();
    RegisterJobOpeningController ctrl = new RegisterJobOpeningController();


    public JobOpeningsBootstrapper() {
    }

    public void execute() {
        ConsoleUtils.showMessageColor("** Job Openings created **", AnsiColor.CYAN);

        // creater customer email
        Email email1 = Email.valueOf("company1@mail.pt");
        Email email2 = Email.valueOf("company2@mail.pt");

        // create customer user
        Optional<String> pwd1 = signUpController.signUp(email1, Role.CUSTOMER);
        Optional<String> pwd2 = signUpController.signUp(email2, Role.CUSTOMER);

        // return customer access details
        System.out.println("User: " + email1.toString() + " | Password: " + pwd1.get());
        System.out.println("User: " + email2.toString() + " | Password: " + pwd2.get());

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
        ctrl.registerJobOpening(dto1);

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
        ctrl.registerJobOpening(dto2);

        // print job opening data
        System.out.println(dto2.toString());
    }
}
