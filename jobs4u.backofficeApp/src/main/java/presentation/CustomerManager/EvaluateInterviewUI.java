package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.EvaluateInterviewController;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

import java.util.List;

public class EvaluateInterviewUI {

    static Role managerRole;
    ListJobOpeningsController listJobOpeningsCtrl = new ListJobOpeningsController();
    EvaluateInterviewController evaluateInterviewCtrl = new EvaluateInterviewController();

    public EvaluateInterviewUI() {
    }

    public void doShow(AuthzUI authzUI) {

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        do {
            // print UI title
            ConsoleUtils.buildUiTitle("Evaluate Interview UI");

            // list active job openings and let user select one
            List<JobOpening> listJobOpenings = listJobOpeningsCtrl.listActiveJobOpenings();
            String header = "Select one job opening:";
            JobOpening selectedJobOpening = null;

            // select job opening, if list is not null
            if(!listJobOpenings.isEmpty()){
                selectedJobOpening = (JobOpening) ConsoleUtils.showAndSelectOne(listJobOpenings, header, "Exit");
            }

            // execute functionality if object is not null
            if(selectedJobOpening != null) {
                int successfullyGradedApplications = evaluateInterviewCtrl.gradeJobOpeningInterviews(selectedJobOpening.getJobReference());
                if (successfullyGradedApplications == -2) {
                    ConsoleUtils.showMessageColor("There is no plugin associated with Job Opening", AnsiColor.RED);
                } else if (successfullyGradedApplications == -1) {
                    ConsoleUtils.showMessageColor("No applications to be graded.", AnsiColor.CYAN);
                } else {
                    ConsoleUtils.showMessageColor("Successfully graded application interviews: " + successfullyGradedApplications, AnsiColor.CYAN);
                }
            }

        } while (ConsoleUtils.confirm("Do you want to evaluate the interviews of another Job Opening? (y/n)"));
    }
}
