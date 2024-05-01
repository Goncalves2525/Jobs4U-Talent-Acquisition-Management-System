package plugins;

import java.io.Serializable;

public class Plugin implements Serializable {
    private Object pluginInstance;
    private String jarName;
    private String path;

    public Plugin(Object pluginInstance, String jarName, String path) {
        this.pluginInstance = pluginInstance;
        this.jarName = jarName;
        this.path = path;
    }

    public Object getPluginInstance() {
        return pluginInstance;
    }

    public String getJarName() {
        return jarName;
    }

    public String getPath() { return path; }
    @Override
    public String toString() {
        return jarName;
    }
}
