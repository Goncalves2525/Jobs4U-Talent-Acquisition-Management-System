package jobOpeningManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import plugins.Plugin;
import plugins.PluginLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class GenerateAnswerCollectionFileController {

    private final PluginLoader pluginLoader = new PluginLoader();
    private final String pluginsDirectory = "plugins/interviews/jar";
    private JobOpeningRepository jobOpeningRepository;

    public GenerateAnswerCollectionFileController(JobOpeningRepository repo) {
        this.jobOpeningRepository=repo;
    }

    public void generateAnswerCollectionFile(String pluginPath) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try {
//            List<Plugin> plugins = loadPlugins();
//            Object plugin = plugins.get(choice).getPluginInstance();

            Plugin plugin = pluginLoader.loadPlugin(pluginPath);
            Object pluginFinal = plugin.getPluginInstance();
            Method exportMethod = pluginFinal.getClass().getMethod("exportTemplateFile", String.class);
            exportMethod.invoke(pluginFinal, "plugins/interview/txt/answerSheet.txt");
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
