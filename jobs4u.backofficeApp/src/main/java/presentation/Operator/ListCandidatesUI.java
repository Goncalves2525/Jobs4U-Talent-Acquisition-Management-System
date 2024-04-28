package presentation.Operator;

import applicationManagement.application.CandidateController;
import applicationManagement.application.RegisterApplicationController;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.*;

import java.util.Iterator;


public class ListCandidatesUI extends AbstractUI{

    private CandidateController ctrlCandidate = new CandidateController();

    @Override
    protected boolean doShow() {
        Iterable<Candidate> candidates = ctrlCandidate.allCandidates();
        System.out.println("== CANDIDATES ==");
        if(candidates == null){
            System.out.println("No candidates present in the system!");
            return false;
        }
        for (Candidate candidate : candidates) {
            printCandidates(candidate.name(), candidate.email(), candidate.phoneNumber());
        }
        return true;
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
        int option = ConsoleUtils.readIntegerFromConsole("Select a Candidate: ");
        Iterator<Candidate> iterator = candidates.iterator();
        for (int j = 0; j < option - 1; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    @Override
    public String headline() {
        return "CANDIDATE LISTING";
    }

    private void printCandidates(String name, String email, String phone){
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println();
    }

}
