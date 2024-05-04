package applicationManagement.application;

import plugins.Plugin;
import plugins.PluginLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class GenerateAnswerCollectionFileController {

    private final PluginLoader pluginLoader = new PluginLoader();
    private final String pluginsDirectory = "plugins/answerCollection/jar";

    public void generateAnswerCollectionFile(String pluginPath) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
//            List<Plugin> plugins = loadPlugins();
//            Object plugin = plugins.get(choice).getPluginInstance();

            Plugin plugin = pluginLoader.loadPlugin(pluginPath);
            Method exportMethod = plugin.getClass().getMethod("exportTemplateFile", String.class);
            exportMethod.invoke(plugin, "plugins/answerCollection/txt/answerSheet.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plugin> loadPlugins() {
        List<Plugin> plugins = pluginLoader.loadPlugins(pluginsDirectory);
        return plugins;
    }

}
