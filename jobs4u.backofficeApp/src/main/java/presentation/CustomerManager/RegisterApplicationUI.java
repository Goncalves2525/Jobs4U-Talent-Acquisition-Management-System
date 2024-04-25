package presentation.CustomerManager;

import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.CandidateController;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.application.RegisterApplicationController;
import jobOpeningManagement.domain.*;
import jobOpeningManagement.domain.dto.ApplicationDTO;
import utils.Utils;

import java.util.Iterator;


public class RegisterApplicationUI extends AbstractUI{

    private RegisterApplicationController ctrl = new RegisterApplicationController();
    private ListJobOpeningsController ctrlListJobOpening = new ListJobOpeningsController();
    private CandidateController ctrlCandidate = new CandidateController();

    @Override
    protected boolean doShow() {
        JobOpening jobOpening = selectJobOpening();
        if(jobOpening == null){
            System.out.println("No Job Openings Available at the time!");
            return false;
        }

        Candidate candidate = selectCandidate();

//        ApplicationDTO applicationDTO = new ApplicationDTO();
//
//        boolean success = ctrl.registerApplication(applicationDTO);
        boolean success =true;
        if (success){
            System.out.println("Job Opening registered successfully");
            return true;
        }else{
            System.out.println("Error registering Job Opening");
            return false;
        }
    }

    private JobOpening selectJobOpening() {
        Iterable<JobOpening> jobOpenings = ctrlListJobOpening.listJobOpenings();
        if(jobOpenings == null){
            System.out.println("No job openings registered");
            return null;
        }
        int i = 1;
        System.out.println("== JOB OPENINGS ==");
        for (JobOpening jobOpening : jobOpenings) {
            System.out.println(i + " - " + jobOpening.title());
        }
        int option = Utils.readIntegerFromConsole("Select a Job Opening: ");
        Iterator<JobOpening> iterator = jobOpenings.iterator();
        for (int j = 0; j < option - 1; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    private Candidate selectCandidate() {
        Iterable<Candidate> candidates = ctrlCandidate.allCandidates();
        if(candidates == null){
            System.out.println("No candidates present in the system!");
            return null;
        }
        int i = 1;
        System.out.println("== CANDIDATES ==");
        for (Candidate candidate : candidates) {
            System.out.println(i + " - " + candidate.name());
        }
        int option = Utils.readIntegerFromConsole("Select a Candidate: ");
        Iterator<Candidate> iterator = candidates.iterator();
        for (int j = 0; j < option - 1; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    @Override
    public String headline() {
        return "APPLICATION REGISTRATION";
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

//    private Customer selectCompany(){
//        Iterable<Customer> customers = ctrl.allCustomers();
//        if(customers == null){
//            System.out.println("No companies registered");
//            return null;
//        }
//        int i = 1;
//        System.out.println("COMPANIES");
//        for (Customer customer : customers) {
//            System.out.println(i + " - " + customer.getCode());
//        }
//        int option = Utils.readIntegerFromConsole("Select Company: ");
//        Iterator<Customer> iterator = customers.iterator();
//        for (int j = 0; j < option - 1; j++) {
//            iterator.next();
//        }
//        return iterator.next();
//    }

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
