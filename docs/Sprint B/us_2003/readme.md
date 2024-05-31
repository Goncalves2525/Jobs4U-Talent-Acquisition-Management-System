# US 2003 - Generate and Export a Template .txt file to collect data

## 1. Context

*Operators of Jobs4U will be able to generate and export a template text file to help collect data fields for candidates of a job opening (so that the data is used to verify the requirements of the job opening).*

## 2. Requirements

**US 2003** As Operator, I want to generate and export a template text file to help collect data fields for candidates of a job opening (so the data is used to verify the requirements of the job opening).

**Acceptance Criteria:**

- 2003.1 The system shall display a list of available applications to the Operator.
- 2003.2 The system shall load the InterviewModel plugin using the retrieved path.
- 2003.3 The system shall generate the candidate fields template based on the loaded plugin.
- 2003.4 The system shall export the generated template to a .txt file.
- 2003.5 The system shall display a success message to the Operator after the template is exported.
- 2003.6 The system shall display an error message to the Operator if the template export fails, or the Application does not contain an InterviewModel plugin.

**Dependencies/References:**

**
**

## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](domain_model.png)

### 3.2. Questions and Answers

> **Question:** É o Operador que regista uma candidatura ou é o sistema que o faz automaticamente? E como integra o “plugin” de verificação da candidatura neste processo?
> 
> **Answer:** Na US 2002 o Operator regista a candidatura. Para isso, é o Operator que inicia o processo mas o sistema deve importar os dados resultantes do Application File Bot de forma “automática” (Ver References da US 2002). O plugin referido entra neste processo através da US 2003, em que o Operador gera um ficheiro template com os dados a introduzir para validar uma candidatura. Na US 2004, o Operador, após preencher os dados específicos da candidatura (com base no ficheiro template anterior) submete no sistema esse ficheiro que vai ser usado para o sistema avaliar/verificar a candidatura. Se os critérios não forem atingidos a candidatura é recusada.

### 3.3. Other Remarks

## 4. Design

### 4.1. Realization

| Interaction ID                                                                           | Question: Which class is responsible for...                           | Answer                    | Justification (with patterns) |
|-----------------------------------------------------------------------------------------|------------------------------------------------------------------------|---------------------------|--------------------------------|
| Step 1 : Operator selects an application                                               | ... displaying the list of available applications?                   | GenerateCandidateFieldsFileUI | Pure Fabrication              |
|                                                                                         | ... capturing user input for application selection?                  | GenerateCandidateFieldsFileUI | Controller                    |
| Step 2 : System retrieves the InterviewModel path from the selected application         | ... accessing the selected application to retrieve InterviewModel path? | GenerateCandidateFieldsFileController | Controller                    |
| Step 3 : System loads the InterviewModel plugin using the retrieved path                | ... accessing the InterviewModel plugin using the retrieved path?    | GenerateCandidateFieldsFileController | Controller                    |
| Step 4 : System generates the candidate fields template based on the loaded plugin      | ... invoking the export method of the loaded plugin?                  | GenerateCandidateFieldsFileController | Controller                    |


### 4.2. Class Diagram

![Class Diagram](class-diagram-01.svg)

### 4.3. Sequence Diagram

![Sequence Diagram](sequence-diagram.svg)

### 4.4. Tests

```java
@Test
    void testLoadPlugins() {
        GenerateCandidateFieldsFileController controller = new GenerateCandidateFieldsFileController();
        List<Plugin> plugins = controller.loadPlugins();

        assertNotNull(plugins);
    }
```

## 5. Implementation

**GenerateCandidateFieldsFileUI**

```java
package presentation.Operator;

import appUserManagement.domain.Role;
import jobOpeningManagement.application.GenerateCandidateFieldsFileController;
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
  GenerateCandidateFieldsFileController generateCandidateFieldsFileController = new GenerateCandidateFieldsFileController();

  protected boolean doShow(AuthzUI authzUI){
    ConsoleUtils.buildUiHeader("Generate a text file to collect candidate details");

    csutomerManagerRole = authzUI.getValidBackofficeRole();
    if (!csutomerManagerRole.showBackofficeAppAccess()) {
      ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
    }

    List<Plugin> plugins = generateCandidateFieldsFileController.loadPlugins();
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
      try {
        generateCandidateFieldsFileController.generateAnswerCollectionFile(choice);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Invalid choice. Please enter a number between 0 and " + (pluginInfo.size() - 1) + ".");
    }

    return true;
  }

}


```

**GenerateCandidateFieldsFileController**

```java

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

```

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a