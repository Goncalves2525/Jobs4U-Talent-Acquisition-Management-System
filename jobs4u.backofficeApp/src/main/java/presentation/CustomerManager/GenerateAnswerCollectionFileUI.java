package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.GenerateAnswerCollectionFileController;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.application.RegisterApplicationController;
import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import console.ConsoleUtils;
import eapli.framework.application.ApplicationService;
import infrastructure.authz.AuthzUI;
import plugins.Plugin;
import plugins.PluginLoader;
import textformat.AnsiColor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GenerateAnswerCollectionFileUI{

    private final PluginLoader pluginLoader = new PluginLoader();
    static Role csutomerManagerRole;
    GenerateAnswerCollectionFileController generateAnswerCollectionFileController = new GenerateAnswerCollectionFileController();
    ListApplicationsController listApplicationsController = new ListApplicationsController();
    RegisterApplicationController registerApplicationController = new RegisterApplicationController();;

    protected boolean doShow(AuthzUI authzUI){
        ConsoleUtils.buildUiHeader("Generate a text file to collect the answers of an interview");

        // get user role, to be used as parameter on restricted user actions
        csutomerManagerRole = authzUI.getValidBackofficeRole();
        if (!csutomerManagerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
        }

        //show all applications
        Iterable<Application> applications = listApplicationsController.listApplications();
        List<Application> applicationList = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (Application app : applications) {
            System.out.println(i + ". " + app.getId() + " - " + app.jobReference());
            applicationList.add(app);
            i++;
        }

        // Prompt the user to choose a plugin
        System.out.print("Choose an Application (enter the number): ");
        int choice = scanner.nextInt();

        // Validate the user's choice
        if (choice >= 0 && choice < i) {
            try {
                Application app = registerApplicationController.findApplicationById(String.valueOf(applicationList.get(choice).getId()));
                String pluginPath = app.getInterviewModel();
                generateAnswerCollectionFileController.generateAnswerCollectionFile(pluginPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Please enter a number between 0 and " + (i - 1) + ".");
        }


//        List<Plugin> plugins = generateAnswerCollectionFileController.loadPlugins();
//        List<String> pluginInfo = new ArrayList<>();
//        List<String> pluginNames = new ArrayList<>();
//
//        for (Object plugin : plugins) {
//            try {
//                String jarFileName = plugin.toString();
//                String jar = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
//                //pluginInfo.add(jarFileName);
//                pluginInfo.add(jar);
//                pluginNames.add(jarFileName);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        int i = 0;
//        for (String info : pluginNames) {
//            System.out.println(i + ". " + info);
//            i++;
//        }
//
//        // Prompt the user to choose a plugin
//        System.out.print("Choose a Template file (enter the number): ");
//        int choice = scanner.nextInt();
//
//        // Validate the user's choice
//        if (choice >= 0 && choice < pluginInfo.size()) {
//            try {
//                generateAnswerCollectionFileController.generateAnswerCollectionFile(choice);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Invalid choice. Please enter a number between 0 and " + (pluginInfo.size() - 1) + ".");
//        }

        return true;
    }

}
