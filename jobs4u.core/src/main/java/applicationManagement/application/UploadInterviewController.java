package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import plugins.PluginLoader;

public class UploadInterviewController {

    private static ApplicationRepository appRepo = PersistenceContext.repositories().applications();
    private final JobOpeningRepository jobRepo = PersistenceContext.repositories().jobOpenings();

    public Application getApplicationById(int id) {
        return appRepo.ofIdentity(String.valueOf(id)).get();
    }

    public JobOpening getJobOpeningById(Application application) {
        String jobRefrenece = application.jobReference();
        return jobRepo.findByJobReference(jobRefrenece);
    }

    public static Iterable<Application> listApplications() {
        return appRepo.findAll();
    }

    public Iterable<Application> listApplicationsOfJobRefence(String jobReference) {
        return appRepo.ofJobReference(jobReference);
    }

    public void getApplication(Long applicationID) {
        Iterable<Application> applications = listApplications();
        boolean appNotFound = false;

        // Check if application is empty
        if (!applications.iterator().hasNext()) {
            System.out.println("No Applications found.");
        } else {
            // Iterate over applications if it's not empty
            for (Application application : applications) {
                if (applicationID == application.getId()) {
                    appNotFound = true;
                    System.out.println("Application ID: " + application.getId() +
                            "\nApplication Status: " + application.getStatus() +
                            "\n\n\u001B[4mCandidate Information\u001B[0m \n" + application.getCandidate() +
                            "\n\n\u001B[4mJob Opening Information\u001B[0m" +
                            "\nJob Reference: " + application.getJobOpening().getJobReference() +
                            "\nTitle: " + application.getJobOpening().getTitle() +
                            "\nContract Type: " + application.getJobOpening().getContractType() +
                            "\nMode: " + application.getJobOpening().getMode() +
                            "\n" + application.getJobOpening().getAddress() +
                            "\nCompany: " + application.getJobOpening().getCompany().getName() +
                            "\nNumber of Vacancies: " + application.getJobOpening().getNumberOfVacancies() +
                            "\nJob Specifications: " + application.getJobOpening().getJobSpecifications() +
                            "\nJob Description: " + application.getJobOpening().getDescription() +
                            "\nJob Requirements: " + application.getJobOpening().getRequirements() +
                            "\nJob Recruitment State: " + application.getJobOpening().getState() +
                            "\n\nApplication Date: " + application.getApplicationDate() +
                            "\nInterview Model: " + application.getInterviewModel() +
                            "\nInterview Model Path: " + application.filePath() +
                            "\nApplication Files Path: " + application.applicationFilesPath());
                }
            }
            if (!appNotFound) {
                System.out.println("Application with ID " + applicationID + " not found.");
            }
        }
    }
}
