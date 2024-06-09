package presentation.CustomerManager;

import appUserManagement.domain.Role;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.application.GenerateAnswerCollectionFileController;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.application.RegisterApplicationController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import plugins.PluginLoader;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenerateInterviewQuestionsFileUI {

    private final PluginLoader pluginLoader = new PluginLoader();
    static Role csutomerManagerRole;
    JobOpeningRepository jobOpeningRepository= PersistenceContext.repositories().jobOpenings();
    GenerateAnswerCollectionFileController generateAnswerCollectionFileController = new GenerateAnswerCollectionFileController(jobOpeningRepository);
    ListJobOpeningsController listJobOpeningsController = new ListJobOpeningsController();

    protected boolean doShow(AuthzUI authzUI){
        ConsoleUtils.buildUiHeader("Generate a text file to collect the answers of an interview");

        // get user role, to be used as parameter on restricted user actions
        csutomerManagerRole = authzUI.getValidBackofficeRole();
        if (!csutomerManagerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
        }

        //show all applications
        Iterable<JobOpening> jobOpenings = listJobOpeningsController.listJobOpenings();
        List<JobOpening> jobOpeningList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (JobOpening job : jobOpenings) {
            System.out.println(i + ". ID:" + job.getId() + " - " + job.jobReference());
            jobOpeningList.add(job);
            i++;
        }

        // Prompt the user to choose a plugin
        System.out.print("Choose a Job Opening (enter the number): ");
        int choice = scanner.nextInt();

        // Validate the user's choice
        if (choice >= 0 && choice < i) {
            try {
                JobOpening jobOpening = generateAnswerCollectionFileController.findJobOpeningById(String.valueOf(jobOpeningList.get(choice).getId()));
                String pluginPath = jobOpening.getInterviewModel();
                if(pluginPath == null){
                    System.out.println("No Interview Model associated with this Job Opening");
                    return false;
                }
                else{
                    generateAnswerCollectionFileController.generateAnswerCollectionFile(pluginPath);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Please enter a number between 0 and " + (i - 1) + ".");
        }

        return true;
    }

}
