package plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader {
    public List<Plugin> loadPlugins(String pluginsDirectory) {
        File pluginsDir = new File(pluginsDirectory);
        List<Plugin> plugins = new ArrayList<>();
        if (pluginsDir.isDirectory()) {
            File[] files = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));
            if (files != null) {
                for (File file : files) {
                    try {
                        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
                        Class<?> pluginClass = classLoader.loadClass("lapr4.Main");
                        Object pluginInstance = pluginClass.newInstance();
                        String jarName = file.getName();
                        String jarPath = file.getPath();
                        Plugin plugin = new Plugin(pluginInstance, jarName, jarPath);
                        plugins.add(plugin);
                        classLoader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return plugins;
    }

    public Plugin loadPlugin(String pluginPath){
        Plugin plugin = null;
        try {
            File file = new File(pluginPath);
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
//            Class<?> pluginClass = classLoader.loadClass("lapr4.Main");
            Class<?> pluginClass = classLoader.loadClass("Main");
            Object pluginInstance = pluginClass.newInstance();
            String jarName = file.getName();
            String jarPath = file.getPath();
            plugin = new Plugin(pluginInstance, jarName, jarPath);
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plugin;
    }
}
