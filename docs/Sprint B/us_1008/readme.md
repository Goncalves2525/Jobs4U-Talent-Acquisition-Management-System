# US 1008

## 1. Context

*The Customer Manager will eventually generate text files for two occasions, Interviews and Job Requirement Specifications. The Language Engineer will deploy a plugin that facilitates this actions.

## 2. Requirements

**US 1008** As Language Engineer, I want to deploy and configure a plugin (i.e., Job Requirement Specification or Interview Model) to be used by the system.

## 3. Analysis

### 3.1. Questions and Answers
> **Question:** TO DO
>
> **Answer:** 


### 3.3. Other Remarks
The plugin (jar file) is in the directory /plugin.

## 4. Design

n/a

## 5. Implementation
****

**PluginLoader**

```java
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
                        Plugin plugin = new Plugin(pluginInstance, jarName);
                        plugins.add(plugin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return plugins;
    }
}

```

**Plugin**

```java
package plugins;

import java.io.Serializable;

public class Plugin implements Serializable {
    private Object pluginInstance;
    private String jarName;

    public Plugin(Object pluginInstance, String jarName) {
        this.pluginInstance = pluginInstance;
        this.jarName = jarName;
    }

    public Object getPluginInstance() {
        return pluginInstance;
    }

    public String getJarName() {
        return jarName;
    }

    @Override
    public String toString() {
        return jarName;
    }
}

```

## 6. Integration/Demonstration
TO DO

## 7. Observations

n/a