package presentation.Operator;

import appUserManagement.domain.Role;
import applicationManagement.domain.Candidate;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.GenerateCandidateFieldsFileController;
import jobOpeningManagement.application.UploadCandidateRequirementsController;
import plugins.Plugin;
import plugins.PluginLoader;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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

        ConsoleUtils.readLineFromConsole("Path to the file: ");
        if(uploadCandidateRequirementsController.uploadCandidateRequirementsFile(candidate.get().email(), choice))
            ConsoleUtils.showMessageColor("File uploaded successfully", AnsiColor.GREEN);


        return true;
    }

}
