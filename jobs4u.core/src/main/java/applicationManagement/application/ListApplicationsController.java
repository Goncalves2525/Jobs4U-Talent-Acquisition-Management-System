package applicationManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;

public class ListApplicationsController {
    private ApplicationRepository repo;

    public ListApplicationsController(ApplicationRepository applicationRepository) {
        this.repo = applicationRepository;
    }

    public Iterable<Application> listApplications() {
        return repo.findAll();
    }

    public Iterable<Application> listApplicationsOfJobRefence(String jobReference) {
        return repo.ofJobReference(jobReference);
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
