package presentation.Operator;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import applicationManagement.application.CandidateController;
import applicationManagement.application.ListCandidatesService;
import applicationManagement.application.ManageCandidateController;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;
import eapli.framework.presentation.console.AbstractUI;


public class ListCandidatesUI extends AbstractUI{

    private ListCandidatesService listCandidatesService;

    public ListCandidatesUI(CandidateRepository candidateRepository) {
        this.listCandidatesService = new ListCandidatesService(candidateRepository);
    }

    @Override
    protected boolean doShow() {

        Iterable<Candidate> candidates = listCandidatesService.allCandidatesSortedByName();

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
