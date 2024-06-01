package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.application.RequirementsVerificationController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;


public class RequirementsVerificationUI {
    RequirementsVerificationController ctrl = new RequirementsVerificationController();
    ListApplicationsController listApplicationsController = new ListApplicationsController();
    static Role managerRole;

    public void doShow(AuthzUI authzUI) throws Exception {
        ConsoleUtils.buildUiHeader("Verify Job Requirement Specification");

        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        Iterable<Application> applications = listApplicationsController.listApplications();
        System.out.println("Applications:");
        for(Application app : applications){
            System.out.println(app.getId() + "- " + app);
        }
        int choice = ConsoleUtils.readIntegerFromConsole("Choose an application ID: ");
        Application application = ctrl.getApplicationById(choice);
        if(application == null){
            ConsoleUtils.showMessageColor("Application not found", AnsiColor.RED);
            return;
        }
        JobOpening jobOpening = ctrl.getJobOpeningById(application);
        String requirementsPath = ctrl.getRequirementsSpecification(jobOpening);
        if(requirementsPath == null){
            ConsoleUtils.showMessageColor("No requirements specification associated with this job opening", AnsiColor.RED);
            return;
        }
        int passed = ctrl.verifyRequirements(requirementsPath);
        if(passed == 1){
            ctrl.assotiateRequirementResultToApplication(application, passed);
            ConsoleUtils.showMessageColor("APPROVED", AnsiColor.GREEN);
            System.out.println("Requirement verification result associated with application");
        }else if(passed == 0){
            ctrl.assotiateRequirementResultToApplication(application, passed);
            ConsoleUtils.showMessageColor("REJECTED", AnsiColor.RED);
            System.out.println("Requirement verification result associated with application");
        }else{
            ConsoleUtils.showMessageColor("Error verifying requirements", AnsiColor.RED);
        }
    }
}
