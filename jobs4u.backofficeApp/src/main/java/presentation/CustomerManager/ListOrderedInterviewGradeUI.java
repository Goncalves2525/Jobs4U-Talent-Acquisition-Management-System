package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListOrderedInterviewGradeController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class ListOrderedInterviewGradeUI {

    static Role managerRole;
    ListJobOpeningsController listJobOpeningsCtrl = new ListJobOpeningsController();
    ListOrderedInterviewGradeController listOrderedInterviewGradeCtrl = new ListOrderedInterviewGradeController();

    public ListOrderedInterviewGradeUI() {
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
            ConsoleUtils.buildUiTitle("List Applications by Interview Grade UI");

            // list active job openings and let user select one
            List<JobOpening> listJobOpenings = listJobOpeningsCtrl.listActiveJobOpenings();
            String header = "Select one job opening:";
            JobOpening selectedJobOpening = null;

            // if list is not null, get only the valid job openings and select one
            List<JobOpening> validListJobOpenings = new ArrayList<>();
            if(!listJobOpenings.isEmpty()){
                for (JobOpening jo : listJobOpenings) {
                    if(jo.getState().ordinal() > 1) {
                        validListJobOpenings.add(jo);
                    }
                }
                selectedJobOpening = (JobOpening) ConsoleUtils.showAndSelectOne(validListJobOpenings, header, "Exit");
            }

            // execute functionality if object is not null
            if(selectedJobOpening != null) {
                List<Application> orderedList = listOrderedInterviewGradeCtrl.listInterviewGradedOrdered(selectedJobOpening.getJobReference());
                if(orderedList == null) {
                    ConsoleUtils.showMessageColor("There are no applications on the Job Opening selected.", AnsiColor.RED);
                } else if (orderedList.isEmpty()) {
                    ConsoleUtils.showMessageColor("There are Applications on the Job Opening selected that are pending to be evaluated.", AnsiColor.RED);
                } else {
                    ConsoleUtils.showMessageColor("\nJob Opening: "
                            + selectedJobOpening.getJobReference() + " | " + selectedJobOpening.getTitle(), AnsiColor.CYAN);
                    String[] listHeader = {"Name", "E-mail", "Grade"};
                    System.out.printf("%20s | %20s | %5s\n", "Name", "E-mail", "Grade");
                    for (Application app : orderedList) {
                        String finalGrade = "-";
                        if(app.getInterviewGrade() > -101) {
                            finalGrade = String.valueOf(app.getInterviewGrade());
                        }
                        System.out.printf("%20s | %20s | %5s\n", app.getCandidate().name(), app.getCandidate().email(), finalGrade);
                    }
                }
            }

        } while (ConsoleUtils.confirm("Do you want to get the list of Applications, ordered by interview grade, for another Job Opening? (y/n)"));
    }
}
