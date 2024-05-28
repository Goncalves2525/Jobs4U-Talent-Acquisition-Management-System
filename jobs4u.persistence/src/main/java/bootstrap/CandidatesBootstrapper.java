package bootstrap;

import applicationManagement.application.CandidateController;
import applicationManagement.domain.CandidateAbility;
import applicationManagement.domain.dto.CandidateDTO;
import console.ConsoleUtils;
import textformat.AnsiColor;

import java.util.Optional;

public class CandidatesBootstrapper {

    CandidateController candidateController = new CandidateController();

    public CandidatesBootstrapper() {
    }

    public void execute() {
        ConsoleUtils.showMessageColor("** Candidates created **", AnsiColor.CYAN);

        CandidateDTO cand1 = new CandidateDTO("Ana", "ana@mail.pt", "123", CandidateAbility.ENABLED);
        Optional<String> cand1pwd = candidateController.registerCandidate(cand1);
        System.out.println("Candidate : " + cand1.getEmail() + " | Password: " + cand1pwd.get());

        CandidateDTO cand2 = new CandidateDTO("Berta", "berta@mail.pt", "456",CandidateAbility.ENABLED);
        Optional<String> cand2pwd = candidateController.registerCandidate(cand2);
        System.out.println("Candidate : " + cand2.getEmail() + " | Password: " + cand2pwd.get());

        CandidateDTO cand3 = new CandidateDTO("Carlos", "carlos@mail.pt", "789",CandidateAbility.ENABLED);
        Optional<String> cand3pwd = candidateController.registerCandidate(cand3);
        System.out.println("Candidate : " + cand3.getEmail() + " | Password: " + cand3pwd.get());
    }
}
