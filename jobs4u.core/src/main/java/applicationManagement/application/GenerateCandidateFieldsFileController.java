package applicationManagement.application;

import plugins.Plugin;
import plugins.PluginLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class GenerateCandidateFieldsFileController {

    private final PluginLoader pluginLoader = new PluginLoader();
    private final String pluginsDirectory = "plugins/interview/jar";

    public void generateAnswerCollectionFile(int choice) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            List<Plugin> plugins = loadPlugins();
            Object plugin = plugins.get(choice).getPluginInstance();
            Method exportMethod = plugin.getClass().getMethod("exportCandidateFile", String.class);
            exportMethod.invoke(plugin, "plugins/interview/txt/candidateSheet.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plugin> loadPlugins() {
        List<Plugin> plugins = pluginLoader.loadPlugins(pluginsDirectory);
        return plugins;
    }

}
