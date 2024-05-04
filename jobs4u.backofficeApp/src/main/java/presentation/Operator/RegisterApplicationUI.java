package presentation.Operator;

import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.ApplicationDTO;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;
import applicationManagement.application.CandidateController;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import applicationManagement.application.RegisterApplicationController;
import jobOpeningManagement.domain.*;
import presentation.CustomerManager.SelectInterviewModelUI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;


public class RegisterApplicationUI extends AbstractUI{

    private RegisterApplicationController ctrl = new RegisterApplicationController();
    private ListJobOpeningsController ctrlListJobOpening = new ListJobOpeningsController();
    private CandidateController ctrlCandidate = new CandidateController();

    @Override
    protected boolean doShow() {
        Candidate selectedCandidate = selectCandidate();
        if(selectedCandidate == null){
            System.out.println("Candidate not available in the system!");
            return false;
        }
        JobOpening jobOpening = selectJobOpening();
        if(jobOpening == null){
            System.out.println("No Job Openings Available at the time!");
            return false;
        }
        String jobReference = jobOpening.jobReference();
        ApplicationStatus status = ApplicationStatus.SUBMITTED;
        String comment = ConsoleUtils.readLineFromConsole("Comment: ");

        String filePathString = "scomp/output/" + jobReference + "/" + selectedCandidate.email();
        String folderPath = "";
        Path path = Paths.get(filePathString);

        if (Files.exists(path)) {
            folderPath = "scomp/output/" + jobReference + "/" + selectedCandidate.email();
        }

        ApplicationDTO applicationDTO = new ApplicationDTO(jobReference, selectedCandidate, jobOpening, comment, new Date(),null, null, status,"", folderPath);
        boolean success = ctrl.registerApplication(applicationDTO);

        System.out.println("Deseja associar um interview model a esta candidatura?");
        boolean associateInterviewModel = ConsoleUtils.confirm("Associar Interview Model? (Y/N)");
        if(associateInterviewModel){
            AuthzUI authzUI = new AuthzUI();
            SelectInterviewModelUI selectInterviewModelUI = new SelectInterviewModelUI();
            selectInterviewModelUI.doShow(authzUI);
        }
        if (success){
            System.out.println("Application registered successfully");
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
            i++;
        }
        System.out.println("0 - To Cancel the Application Registration");
        int option = ConsoleUtils.readIntegerFromConsole("Select a Candidate: ");
        if (i==0){
            return null;
        }
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

}
