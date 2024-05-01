package presentation.Operator;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import plugins.Plugin;
import plugins.PluginLoader;
import textformat.AnsiColor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GenerateCandidateFieldsFileUI {

    private final PluginLoader pluginLoader = new PluginLoader();
    static Role csutomerManagerRole;

    protected boolean doShow(AuthzUI authzUI){
        ConsoleUtils.buildUiHeader("Generate a text file to collect candidate details");

        csutomerManagerRole = authzUI.getValidBackofficeRole();
        if (!csutomerManagerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
        }

        String pluginsDirectory = "plugins/answerCollection/jar";
        List<Plugin> plugins = pluginLoader.loadPlugins(pluginsDirectory);
        List<String> pluginInfo = new ArrayList<>();
        List<String> pluginNames = new ArrayList<>();

        for (Object plugin : plugins) {
            try {
                String jarFileName = plugin.toString();
                String jar = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
                //pluginInfo.add(jarFileName);
                pluginInfo.add(jar);
                pluginNames.add(jarFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (String info : pluginNames) {
            System.out.println(i + ". " + info);
            i++;
        }

        // Prompt the user to choose a plugin
        System.out.print("Choose a Template file (enter the number): ");
        int choice = scanner.nextInt();

        // Validate the user's choice
        if (choice >= 0 && choice < pluginInfo.size()) {
            String selectedPluginInfo = pluginInfo.get(choice);
            try {
                Object plugin = plugins.get(choice).getPluginInstance();
                Method exportMethod = plugin.getClass().getMethod("exportCandidateFile", String.class);
                exportMethod.invoke(plugin, "plugins/answerCollection/txt/candidateSheet.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Please enter a number between 0 and " + (pluginInfo.size() - 1) + ".");
        }

        return true;
    }

}
