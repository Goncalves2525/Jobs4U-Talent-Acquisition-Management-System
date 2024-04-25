package presentation.CustomerManager;

import eapli.framework.presentation.console.AbstractUI;
import plugins.PluginLoader;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestPluginUI extends AbstractUI {

    private final PluginLoader pluginLoader = new PluginLoader();

    @Override
    protected boolean doShow() {
        String pluginsDirectory = "plugins";
        List<Object> plugins = pluginLoader.loadPlugins(pluginsDirectory);
        List<String> pluginInfo = new ArrayList<>();

        for (Object plugin : plugins) {
            try {
                // Assuming the main class name is known or specified in each plugin
                String jarFileName = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
                pluginInfo.add(jarFileName);
                //Method mainMethod = plugin.getClass().getMethod("main", String[].class);
                //mainMethod.invoke(null, (Object) new String[]{});
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
            // Extract the selected plugin and run it
            try {
                Object plugin = plugins.get(choice);
                // Assuming the main class name is known or specified in each plugin
                Method mainMethod = plugin.getClass().getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) new String[]{}); // Pass any command-line arguments if needed
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid choice. Please enter a number between 0 and " + (pluginInfo.size() - 1) + ".");
        }


        return true;
    }

    @Override
    public String headline() {
        return "TEST PLUGIN";
    }
}
