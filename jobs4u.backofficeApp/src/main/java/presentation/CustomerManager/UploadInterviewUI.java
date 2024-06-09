//package presentation.CustomerManager;
//
//import appUserManagement.domain.Role;
//import applicationManagement.application.UploadInterviewController;
//import applicationManagement.domain.Application;
//import console.ConsoleUtils;
//import infrastructure.authz.AuthzUI;
//import jobOpeningManagement.domain.JobOpening;
//import textformat.AnsiColor;
//
//import javax.swing.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class UploadInterviewUI {
//
//    private UploadInterviewController ctrl = new UploadInterviewController();
//    static Role managerRole;
//
//    public void doShow(AuthzUI authzUI) throws Exception {
//        ConsoleUtils.buildUiHeader("Upload Interview Model");
//
//        managerRole = authzUI.getValidBackofficeRole();
//        if (!managerRole.showBackofficeAppAccess()) {
//            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
//            return;
//        }
//
//        Iterable<Application> applications = UploadInterviewController.listApplications();
//        System.out.println("Applications:");
//        for(Application app : applications){
//            System.out.println(app.getId() + "- " + app);
//        }
//        int choice = ConsoleUtils.readIntegerFromConsole("Choose an application ID: ");
//        Application application = ctrl.getApplicationById(choice);
//        if(application == null){
//            ConsoleUtils.showMessageColor("Application not found", AnsiColor.RED);
//            return;
//        }
//        String interviewModelPath = ctrl.getInterviewModelPath(application);
//        if(interviewModelPath == null){
//            ConsoleUtils.showMessageColor("No interview model associated with this job opening", AnsiColor.RED);
//            return;
//        }
//        int passed = ctrl.verifyInterviewModel(interviewModelPath);
//        if(passed == 1){
//            ctrl.assotiateInterviewModelToApplication(application, interviewModelPath, passed);
//            ConsoleUtils.showMessageColor("APPROVED", AnsiColor.GREEN);
//            System.out.println("The interview model is syntactically correct, and so, uploaded.");
//        }else if(passed == 0){
//            ConsoleUtils.showMessageColor("REJECTED", AnsiColor.RED);
//            System.out.println("The interview model is not syntactically correct, and so, not uploaded.");
//        }else{
//            ConsoleUtils.showMessageColor("Error verifying interview model", AnsiColor.RED);
//        }
//    }
//}