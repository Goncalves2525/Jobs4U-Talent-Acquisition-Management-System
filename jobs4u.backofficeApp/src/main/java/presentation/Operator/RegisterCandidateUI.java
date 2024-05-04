package presentation.Operator;

import applicationManagement.application.CandidateController;
import applicationManagement.domain.dto.CandidateDTO;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;

import java.util.Optional;


public class RegisterCandidateUI extends AbstractUI {

    private CandidateController ctrlCandidate = new CandidateController();

    @Override
    protected boolean doShow() {

        System.out.println("== Registration of new Candidate ==");

        String name = ConsoleUtils.readLineFromConsole("Insert the Candidate name: ");
        String email = ConsoleUtils.readLineFromConsole("Insert the Candidate email: ");
        String phone = ConsoleUtils.readLineFromConsole("Insert the Candidate phone: ");

        CandidateDTO candidateDTO = new CandidateDTO(name, email, phone);
        Optional<String> pwd = ctrlCandidate.registerCandidate(candidateDTO);

        if (pwd.isEmpty()) {
            System.out.println("Error registering Candidate");
            return false;
        } else {
            System.out.println("Candidate registered successfully");
            System.out.println("Candidate name: " + candidateDTO.getName() + ", email: " + candidateDTO.getEmail() + ", phone: " + candidateDTO.getPhone() + "password: " + pwd.get());
            return true;
        }
    }

    @Override
    public String headline() {
        return "Candidate Registration";
    }
}