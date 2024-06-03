package presentation.Operator;

import applicationManagement.application.CandidateController;
import applicationManagement.domain.dto.CandidateDTO;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;
import java.util.regex.*;
import java.util.Optional;


public class RegisterCandidateUI extends AbstractUI {

    private static final String PHONE_VALID = "^\\d{9}$"; // Assuming a 9-digit phone number
    private static final String EMAIL_VALID = "^.+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    private CandidateController ctrlCandidate = new CandidateController();

    @Override
    protected boolean doShow() {
        String name, email, phone;

        // Insert a valid name
        do {
            name = ConsoleUtils.readLineFromConsole("Insert the Candidate name: ");
        } while (!isValidInput(name));

        // Validate email
        do {
            email = ConsoleUtils.readLineFromConsole("Insert the Candidate email: ");
        } while (!isValidInput(email) || !isValidEmail(email));

        // Insert a valid phone
        do {
            phone = ConsoleUtils.readLineFromConsole("Insert the Candidate phone: ");
        } while (!isValidInput(phone) || !isValidPhone(phone));

        CandidateDTO candidateDTO = new CandidateDTO(name, email, phone);
        Optional<String> pwd = ctrlCandidate.registerCandidate(candidateDTO);

        if (pwd.isEmpty()) {
            System.out.println("Error registering Candidate");
            return false;
        } else {
            System.out.println("Candidate registered successfully");
            System.out.println("Candidate name: " + candidateDTO.getName() + " | email: " + candidateDTO.getEmail() + " | phone: " + candidateDTO.getPhone() + " | password: " + pwd.get());
            return true;
        }
    }

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_VALID);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public static boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_VALID);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public String headline() {
        return "== Registration of new Candidate ==";
    }
}