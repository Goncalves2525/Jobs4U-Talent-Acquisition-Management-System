package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import plugins.Plugin;
import plugins.PluginLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class GenerateCandidateFieldsFileController {

    private final PluginLoader pluginLoader = new PluginLoader();
    private final String pluginsDirectory = "plugins/jobRequirements/jar";
    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    public void generateAnswerCollectionFile(int choice) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
            List<Plugin> plugins = loadPlugins();
            Object plugin = plugins.get(choice).getPluginInstance();
            String pluginName = "plugins/jobRequirements/txt/";
            String jarName = plugins.get(choice).getJarName();
            pluginName += jarName;
            pluginName = pluginName.substring(0, pluginName.length() - 4);
            pluginName += ".txt";
            Method exportMethod = plugin.getClass().getMethod("generateJobRequirementsFile", String.class);
            exportMethod.invoke(plugin, pluginName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Plugin> loadPlugins() {
        List<Plugin> plugins = pluginLoader.loadPlugins(pluginsDirectory);
        return plugins;
    }

    public JobOpening findJobOpeningById(String id){
        return jobOpeningRepository.ofIdentity(id).get();
    }

}
