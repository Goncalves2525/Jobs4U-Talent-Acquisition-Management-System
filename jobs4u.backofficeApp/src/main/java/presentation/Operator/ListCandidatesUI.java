package presentation.Operator;

import applicationManagement.application.CandidateController;
import applicationManagement.domain.Candidate;
import eapli.framework.presentation.console.AbstractUI;


public class ListCandidatesUI extends AbstractUI{

    private CandidateController ctrlCandidate = new CandidateController();

    @Override
    protected boolean doShow() {
        Iterable<Candidate> candidates = ctrlCandidate.allCandidatesSortedByName();

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
