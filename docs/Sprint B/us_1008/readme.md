# US 1008

## 1. Context

*The Customer Manager will eventually generate text files for two occasions, Interviews and Job Requirement Specifications. The Language Engineer will deploy a plugin that facilitates this actions.

## 2. Requirements

**US 1008** As Language Engineer, I want to deploy and configure a plugin (i.e., Job Requirement Specification or Interview Model) to be used by the system.

## 3. Analysis

### 3.1. Questions and Answers
> **Question:** Como é que o Language Engineer faz o interview model e os job requirements? É texto? Ou ele seleciona perguntas para a interview e requirements para a job opening? E isso é quando se está a criar uma entrevista ou uma job opening ou para-se a meio para fazer isso e depois continua se?
>
> **Answer:** O language enginner com informação passada pelo customer manager (que obteve do customer) vai desenvolver em java um jar correspondente ao modulo/plugin. Para esse desenvolvimento terá de utilizar técnicas de desenvolvimento de gramáticas/linguagens como o antlr. Esse código ficará num jar que depois o language engineer “instala/regista” na aplicação (US1008, por exemplo, associando um nome ao jar num ficheiro de configuração – “5 anos experiencia java”, “req-model-5-years-java.jar”). A aplicação com essa informação carrega dinamicamente esse jar. Na gramátca usada no jar é que vão estar espelhadas a estrutura das perguntas a usar nesse modelo e sua avaliação. Estas atividades têm de ser feitas antes de se poder fazer a US1008. Esse trabalho é feito “fora” dos sistema, apenas se registando o modelo (quando está pronto) na US1008. A US 1009 e US1011 permitem selecionar modelos a usar (dos que foram devidamente registados no sistema).

> **Question:** US1008, relativamente aos módulos das entrevistas e dos requisitos, os seus identificadores podem ser automáticos ou específicos (i.e., manuais)?
> 
> **Answer:** A Q41 refere a mesma US. Lá refere-se que cada modulo será registado no sistema através de 2 dados, por exemplo, associando um nome ao jar num ficheiro de configuração – “5 anos experiencia java”, “req-model-5-years-java.jar”. Ou seja, assume-se que cada modulo terá um nome/designação (que suponho que deverá ser única) e a este nome ficará associado o nome do ficheiro jar (provavelmente um path completo) que implementa esse módulo. Ou seja, esse nome/designação pode ser considerado como um identificador especifico/manual.

> **Question:** Perguntas para os plugins.- É possível esclarecer-nos se as perguntas a serem feitas para os Interview Models e os Requirement Especifications são aquelas que estão no exemplo da documentação ou tem algum grupo de questões que gostaria que nós utiliza-se-mos?
>
> **Answer:** O documento de especificação apresenta, como disse, exemplos. São apenas exemplos indicativos. Podem usar esses exemplos como casos de teste e como ponto de partida para definirem outros. Mas é suposto a solução suportar mais do que apenas os exemplos que estão no documento. Em qualquer dos plugins, o tipo de perguntas que deve ser suportado é o que está apresentado na página 8 do documento. Como product onwer eu gostaria que uma demonstração funcional do sistema incluísse pelo menos 2 plugins de cada tipo, para se poder demonstrar, minimamente, o suporte para mais do que um plugin usado (de cada tipo) em simultâneo. Deve ainda demonstrar o uso de todos os tipos de perguntas apresentados na página 8 (atualizado em 2024-04-27)

> **Question:** O deployment do jar file (plugin) é suposto ser armazenado na base de dados? De que forma vamos manter o plugin após diferentes utilizações? Ou devemos armazenar uma string para o path do jar file?
> 
> **Answer:** Esta questão é um pouco técnica. Como cliente o que eu pretendo é que seja possível usar vários módulos (plugins). Não me parece que seja necessário armazenar os plugins na base de dados. Até poderia ser feito, mas não vamos por ai. Um plugin pode ser visto como parte do código da aplicação que é “feito” depois da aplicação estar em produção, podendo-se acrescentar “funcionalidade” à aplicação sem ter de gerar uma nova versão da aplicação, por intermédio da instalação de plugins. Sendo código. As suas versões são geridas como o restante código, num repositório de código fonte. Mas estou a entrar em considerações técnicas que queria evitar. Para questões técnicas existe outro fórum e existem os docentes das UC.


### 3.3. Other Remarks
The plugin (jar file) is in the directory /plugin.

**Grammar to be used in the next sprint when syntax checking and evaluation of interviews become relevant:**

[ANTLR Grammar](antlr/interviewInput.g4)

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

**Grammar**

```antlrv4
grammar interviewInput;

// Start rule
start: question1 question2 question3 question4 question5 question6 question7 question8 question9;

// Rule for the first question
question1: 'Is Java an object-oriented programming language? (True or False)' answer1=BOOLEAN;

// Rule for the second question
question2: 'How do you describe yourself in 5 words:' answer2=WORD WORD WORD WORD WORD;

// Rule for the third question
question3: 'Select one degree (None; Bachelor; Master; PHD)' answer3=DEGREE;

// Rule for the fourth question
question4: 'Select one or more programming languages you are proficient in (java; javascript; python; c)' answer4=language+;

// Rule for the fifth question
question5: 'Enter the number of years of experience (integer)' answer5=INTEGER;

// Rule for the sixth question
question6: 'Enter your salary expectations (decimal)' answer6=DECIMAL;

// Rule for the seventh question
question7: 'On what specific date can you start working? (dd/mm/yyyy)' answer7=DATE;

// Rule for the eighth question
question8: 'How many overtime hours are you available to work per week? (hh:mm)' answer8=TIME;

// Rule for the ninth question
question9: 'How capable do you feel to carry out the duties described in the job offer? [0-5]' answer9=CAPABILITY;

// Boolean rule for true or false answers
BOOLEAN: 'true' | 'false';

// Word rule for five words in the second question
WORD: [a-zA-Z]+;

// Rule for degree selection
DEGREE: 'None' | 'Bachelor' | 'Master' | 'PHD';

// Rule for programming language selection
language: 'java' | 'javascript' | 'python' | 'c';

// Rule for integer numbers
INTEGER: [0-9]+;

// Rule for decimal numbers
DECIMAL: [0-9]+ '.' [0-9]+;

// Rule for date format dd/mm/yyyy
DATE: [0-3]?[0-9] '/' [0-1]?[0-9] '/' [0-9]+;

// Rule for time format hh:mm
TIME: [0-2]?[0-9] ':' [0-5][0-9];

// Rule for capability rating between 0 and 5
CAPABILITY: [0-5];
```

## 6. Integration/Demonstration

In this sprint, we focused on implementing the plugin designed to streamline the process of generating text files containing interview models and job requirements specifications. Our primary goal was to ensure that the plugin seamlessly generates these text files, while future sprints will address the functionality for evaluating interviews.

Within the project, the plugin was developed as a standalone entity, accessible solely through its jar file. We  created jars  for each interview and job requirements specifications, as well as a jar encompassing both. This modular approach lays the groundwork for future optimization, allowing us to refine accessibility to specific methods as needed.

On this sprint centered on delivering a solution for text file generation, paving the way for further enhancements and optimizations in subsequent iterations.

## 7. Observations

n/a