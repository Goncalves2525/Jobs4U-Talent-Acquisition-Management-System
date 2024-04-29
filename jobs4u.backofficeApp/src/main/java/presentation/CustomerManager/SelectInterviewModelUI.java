package presentation.CustomerManager;

import applicationManagement.application.SelectInterviewModelController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import plugins.Plugin;

import java.util.List;

public class SelectInterviewModelUI {
    private SelectInterviewModelController ctrl = new SelectInterviewModelController();

    protected boolean show() {
        boolean success = false;
        String appID = "";
        System.out.println("Insert Application ID: ");
        appID = ConsoleUtils.readLineFromConsole("Application ID: ");
        Application application = ctrl.findApplicationById(appID);
        if (application == null) {
            System.out.println("Application not found");
            return false;
        }
        success = ctrl.checkIfApplicationHasInterviewModel(application);
        if (success) {
            System.out.println("Application already has Interview Model");
            return false;
        }
        List<Plugin> interviewModels = ctrl.getAllInterviewModels();
        int choice = selectInterviewModel(interviewModels);
        success = ctrl.associateInterviewModelToApplication(application, interviewModels.get(choice));
        if (success) {
            System.out.println("Interview Model associated to Application");
            return true;
        } else {
            System.out.println("Error associating Interview Model to Application");
            return false;
        }
    }


    private int selectInterviewModel(List<Plugin> interviewModels) {
        int i = 0;
        System.out.println("== INTERVIEW MODELS ==");
        for (Object interviewModel : interviewModels) {
            System.out.println(i + ". " + interviewModel.toString());
            i++;
        }
        int choice;
        do {
            choice = ConsoleUtils.readIntegerFromConsole("Choose a model (enter the number): ");
        } while (choice < 0 || choice >= interviewModels.size());
        return choice;
    }
}