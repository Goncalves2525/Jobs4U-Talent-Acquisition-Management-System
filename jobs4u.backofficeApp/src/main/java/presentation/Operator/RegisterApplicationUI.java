package presentation.Operator;

import applicationManagement.application.SelectInterviewModelController;
import applicationManagement.domain.Application;
import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.ApplicationDTO;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;
import applicationManagement.application.CandidateController;
import jobOpeningManagement.application.ListJobOpeningsController;
import applicationManagement.application.RegisterApplicationController;
import jobOpeningManagement.domain.*;
import presentation.CustomerManager.SelectInterviewModelUI;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class RegisterApplicationUI extends AbstractUI{

    private RegisterApplicationController ctrl = new RegisterApplicationController();
    private ListJobOpeningsController ctrlListJobOpening = new ListJobOpeningsController();
    private CandidateController ctrlCandidate = new CandidateController();
    private SelectInterviewModelController ctrlSelectInterviewModel = new SelectInterviewModelController();

    @Override
    protected boolean doShow() {
        Candidate selectedCandidate = selectCandidate();
//        //adicionar opção para criar candidate, se um não existir
//        if(selectedCandidate == null){
//            System.out.println("Candidate not found in the system; creating a new one");
//            CreateCandidateUI createCandidateUI = new CreateCandidateUI();
//            createCandidateUI.show();
//        }

        JobOpening jobOpening = selectJobOpening();
        if(jobOpening == null){
            System.out.println("No Job Openings Available at the time!");
            return false;
        }
        String jobReference = jobOpening.jobReference();
        ApplicationStatus status = ApplicationStatus.SUBMITTED;
        String comment = ConsoleUtils.readLineFromConsole("Comment: ");
        ApplicationDTO applicationDTO = new ApplicationDTO(jobReference, selectedCandidate, jobOpening, comment, new Date(),null, status);
        boolean success = ctrl.registerApplication(applicationDTO);
        List<Object> interviewModel = ctrlSelectInterviewModel.getAllInterviewModels();
        int choice = selectInterviewModel(interviewModel);
        Object selectedInterviewModel = interviewModel.get(choice);
        Application Application = ctrl.findApplicationById(String.valueOf(applicationDTO.id()));
        ctrlSelectInterviewModel.associateInterviewModelToApplication(Application, selectedInterviewModel);

        //get files in filebot/output
        

        if (success){
            System.out.println("Application registered successfully");
            return true;
        }else{
            System.out.println("Error registering Job Opening");
            return false;
        }
    }

    private int selectInterviewModel(List<Object> interviewModels) {
        int i = 0;
        System.out.println("== INTERVIEW MODELS ==");
        for (Object interviewModel : interviewModels) {
            System.out.println(i + ". " + interviewModel.toString());
            i++;
        }
        int choice;
        do {
            choice = ConsoleUtils.readIntegerFromConsole("Choose a model (enter the number): ");
        } while (choice < 0 || choice >= interviewModels.size());
        return choice;
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
        int option = ConsoleUtils.readIntegerFromConsole("Select a Job Opening: ");
        Iterator<JobOpening> iterator = jobOpenings.iterator();
        for (int j = 0; j < option - 1; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    private Candidate selectCandidate() {
        Iterable<Candidate> candidates = ctrlCandidate.allCandidates();
        System.out.println("== CANDIDATES ==");
        if(candidates == null || candidates.spliterator().getExactSizeIfKnown() == 0){
            System.out.println("No candidates present in the system!");
            return null;
        }
        int i = 1;
        for (Candidate candidate : candidates) {
            System.out.println(i + " - " + candidate.name() + " | " + candidate.email());
        }
        int option = ConsoleUtils.readIntegerFromConsole("Select a Candidate: ");
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
