package presentation.CustomerManager;

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

public class TestPluginUI{

    private final PluginLoader pluginLoader = new PluginLoader();
    static Role managerRole;

    protected boolean doShow(AuthzUI authzUI){
        ConsoleUtils.buildUiHeader("Test Plugin");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
        }

        String pluginsDirectory = "plugins/interview/jar";
        List<Plugin> plugins = pluginLoader.loadPlugins(pluginsDirectory);
        List<String> pluginInfo = new ArrayList<>();

        for (Object plugin : plugins) {
            try {
                String jarFileName = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
                pluginInfo.add(jarFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (String info : pluginInfo) {
            System.out.println(i + ". " + info);
            i++;
        }

        // Prompt the user to choose a plugin
        System.out.print("Choose a plugin (enter the number): ");
        int choice = scanner.nextInt();

        // Validate the user's choice
        if (choice >= 0 && choice < pluginInfo.size()) {
            String selectedPluginInfo = pluginInfo.get(choice);
            try {
                Object plugin = plugins.get(choice);
                Method exportMethod = plugin.getClass().getMethod("exportFile", String.class);
                exportMethod.invoke(plugin, "plugins/interview/txt/testInterview.txt");

                Method readMethod = plugin.getClass().getMethod("readFile", String.class);
                String content = (String) readMethod.invoke(plugin, "plugins/interview/txt/testInterview.txt");
                System.out.println(content);

                //Method mainMethod = plugin.getClass().getMethod("main", String[].class);
                //mainMethod.invoke(null, (Object) new String[]{}); // Pass any command-line arguments if needed
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Please enter a number between 0 and " + (pluginInfo.size() - 1) + ".");
        }

        return true;
    }

}
