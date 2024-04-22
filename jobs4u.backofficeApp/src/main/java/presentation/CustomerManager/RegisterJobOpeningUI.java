package presentation.CustomerManager;

import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.RegisterJobOpeningController;
import jobOpeningManagement.domain.*;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import utils.Utils;
import java.util.Iterator;


public class RegisterJobOpeningUI extends AbstractUI{

    private RegisterJobOpeningController ctrl = new RegisterJobOpeningController();

    @Override
    protected boolean doShow() {
        int option = 0;
        String title;
        do{
            title = Utils.readLineFromConsole("Title: ");
        }while(title.equals(""));
        ContractType contractType = selectContractType();
        JobMode mode = selectJobMode();
        System.out.println("-ADDRESS-");
        String street, city, postalCode;
        do{
            street = Utils.readLineFromConsole(" Street: ");
            city = Utils.readLineFromConsole(" City: ");
            postalCode = Utils.readLineFromConsole(" Postal Code: ");
        }while(street.equals("") || city.equals("") || postalCode.equals(""));
        Address address = new Address(street, city, postalCode);
        Customer company = selectCompany();
        if(company == null){
            return false;
        }
        int numberOfVacancies = Utils.readIntegerFromConsole("Number of Vacancies: ");
        String description = Utils.readLineFromConsole("Description: ");
        Requirements requirements = null;
        RecruitmentState state = RecruitmentState.APPLICATION;



        printJobOpenings(title, contractType, mode, address, company, numberOfVacancies, description, state);
        System.out.println("1 - Confirm");
        System.out.println("0 - Exit");
        option = Utils.readIntegerFromConsole("Option: ");
        if(option == 0){
            return false;
        }



        JobOpeningDTO jobOpeningDTO = new JobOpeningDTO(title, contractType, mode, address, company, numberOfVacancies, description, requirements, state);
        boolean success = ctrl.registerJobOpening(jobOpeningDTO);
        if (success){
            System.out.println("Job Opening registered successfully");
            return true;
        }else{
            System.out.println("Error registering Job Opening");
            return false;
        }
    }

    @Override
    public String headline() {
        return "JOB OPENING REGISTRATION";
    }

    private ContractType selectContractType(){
        int i = 1;
        System.out.println("CONTRACT TYPES");
        for (ContractType contractType : ContractType.values()) {
            System.out.println(i + " - " + contractType);
            i++;
        }
        int option = Utils.readIntegerFromConsole("Select Contract Type: ");
        return ContractType.values()[option - 1];
    }

    private JobMode selectJobMode(){
        int i = 1;
        System.out.println("JOB MODES");
        for (JobMode jobMode : JobMode.values()) {
            System.out.println(i + " - " + jobMode);
            i++;
        }
        int option = Utils.readIntegerFromConsole("Select Job Mode: ");
        return JobMode.values()[option - 1];
    }

    private Customer selectCompany(){
        Iterable<Customer> customers = ctrl.allCustomers();
        if(customers == null){
            System.out.println("No companies registered");
            return null;
        }
        int i = 1;
        System.out.println("COMPANIES");
        for (Customer customer : customers) {
            System.out.println(i + " - " + customer.getCode());
        }
        int option = Utils.readIntegerFromConsole("Select Company: ");
        Iterator<Customer> iterator = customers.iterator();
        for (int j = 0; j < option - 1; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    private RecruitmentState selectRecruitmentState(){
        int i = 1;
        System.out.println("RECRUITMENT STATES");
        for (RecruitmentState recruitmentState : RecruitmentState.values()) {
            System.out.println(i + " - " + recruitmentState);
            i++;
        }
        int option = Utils.readIntegerFromConsole("Select Recruitment State: ");
        return RecruitmentState.values()[option - 1];
    }

    private void printJobOpenings(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, RecruitmentState state){
        System.out.println("Title: " + title);
        System.out.println("Contract Type: " + contractType);
        System.out.println("Mode: " + mode);
        System.out.println(address);
        System.out.println("Company: " + company.getCode());
        System.out.println("Number of Vacancies: " + numberOfVacancies);
        System.out.println("Description: " + description);
        System.out.println("State: " + state);
        System.out.println();
    }
}
