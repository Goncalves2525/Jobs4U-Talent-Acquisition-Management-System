package presentation.Operator;

import appUserManagement.domain.Role;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.UploadCandidateRequirementsController;
import textformat.AnsiColor;

import java.util.Optional;

public class UploadCandidateRequirementsFileUI {

    static Role operatorRole;
    UploadCandidateRequirementsController uploadCandidateRequirementsController = new UploadCandidateRequirementsController();

    protected boolean doShow(AuthzUI authzUI){
        ConsoleUtils.buildUiHeader("Upload Candidate Requirements File");

        operatorRole = authzUI.getValidBackofficeRole();
        if (!operatorRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
        }

        Iterable<Candidate> candidates = uploadCandidateRequirementsController.getAllCandidates();
        System.out.println("Candidates:");
        for(Candidate candidate : candidates){
            System.out.println(candidate.name() + " - " + candidate.email());
        }
        String choice = ConsoleUtils.readLineFromConsole("Choose a Candidate by its email: ");
        Optional<Candidate> candidate = uploadCandidateRequirementsController.getCandidateByEmail(choice);
        if(candidate.isEmpty()){
            ConsoleUtils.showMessageColor("Candidate not found", AnsiColor.RED);
            return false;
        }

        String filePath = ConsoleUtils.readLineFromConsole("Path to the file: ");
        if(uploadCandidateRequirementsController.uploadCandidateRequirementsFile(candidate.get().email(), filePath))
            ConsoleUtils.showMessageColor("File uploaded successfully", AnsiColor.GREEN);


        return true;
    }

}
