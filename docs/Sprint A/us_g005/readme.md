# US G005 - Add to the project the necessary scripts

## 1. Context

Task assigned during sprint A.
When a user needs to execute the applications, a jar file must be created and then run the necessary command-line commands to execute it. Another example comes when running tests, that must be performed individually, on a restricted group.
It is intended for the project to offer a set of scripts that facilitate the team project development, as well as guaranteeing the consistency of the work developed.
This task can have improvements throughout the project development, as more scripts might be needed or refinements required.

## 2. Requirements

**US G005** As Project Manager, I want the team to add to the project the necessary scripts,
so that build/executions/deployments/... can be executed e￿ffortlessly.

**Acceptance Criteria:**

- G005.1 The scripts should be able to create .jar files.
- G005.2 The scripts should be able to run .jar files.
- G005.3 The scripts should be able to run Maven commands sequentially.
- G005.4. Scripts to be implemented for Mac/Linux (.sh) and Windows (.bat) systems.
- G005.5. Scripts execution must not exceed 2 minutes.

**Dependencies/References:**

*No dependencies found.*

## 3. Analysis

Previous knowledge has been developed, by the team members, on scripting with command-line, and it is going to be used accordingly.
For sprint A, the needed scripts are intended to build and run each application separately, displaying simple messages contained within each application.
Once the project develops itself, more scripts will be necessary.

Since the project is built on a Maven framework, using Maven commands is essencial.
The main **Maven commands** to use are: validate; compile; test; package; integration-test; verify; install; deploy; clean; site.
More can be found through: [Maven Tools](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html#running-maven-tools)

Within the team it is available both Mac and Windows systems, which will facilitate the testing of scripts.

Script time consumption will be closely related with the commands executed, as well as the complexity of the script itself.
Those two main concepts shall be taken in consideration during script development.
Some other techniques can be used, such as the one's in the article: [Unlocking Maximum Efficiency: How to Speed Up PowerShell Performance for Streamlined Command-Line Operations](https://locall.host/how-to-speed-up-powershell-performance/)

## 4. Design

### 4.1. Realization
In Sprint A, the team is focused on the structure of the project.
We are using Maven to manage the project and the dependencies.
We decided that, at least, three apps should be created: one for the Customer, one for the Candidate and the Backoffice for the Customer Manager and the Operator.
To run the apps, the user must first create the jar file and then run the necessary command to run it.
This process can be time-consuming and error-prone.
To make it easier for the user, we are going to create scripts that do all of that work automatically.
There will be a script for each app and for each operating system.
For Windows, we are going to use batch files (.bat) and for Linux/Mac, we are going to use shell scripts (.sh).
## 4. Design
For Windows, we are going to use a batch files (.bat) and for Linux/Mac, we are going to use shell scripts (.sh).

> **Build and run Backoffice Application**
>
> A script that intends to remove any previous build of the backofficeApp, (re-)build it and run it afterward.
> Due to dependencies, the whole project is built, but only the backofficeApp runs.

> **Build and run Candidate Application**
>
> A script that intends to remove any previous build of the candidateApp, (re-)build it and run it afterward.
> Due to dependencies, the whole project is built, but only the candidateApp runs.

> **Build and run Customer Application**
>
> A script that intends to remove any previous build of the customerApp, (re-)build it and run it afterward.
> Due to dependencies, the whole project is built, but only the customerApp runs.

#### 4.1.1. Linux/Mac
To build the jar file, we are going to use the following command:
```
mvn clean package
```

To run the jar file, we are going to use the following command:
```
java -jar jobs4u.backofficeApp-0.1.0.jar
```

#### 4.1.2. Windows
To build the jar file, we are going to use the following command:
```
call mvn clean package
```

To run the jar file, we are going to use the following command:
```
java -jar jobs4u.backofficeApp-0.1.0.jar
```

#### 4.1.3. Others
Since the scripts are going to be in the "scripts" folder, we are going to use some commands to navigate to the root folder of the project.
Examples:
```
cd ..
```
and
```
cd jobs4u.backofficeApp/target
```

### 4.2. Class Diagram

n/a

### 4.3. Applied Patterns

n/a

### 4.4. Tests

Manual tests are to be performed by both Mac and Windows users within the team.
Operational scripts will be added to the [Implementation](#5-implementation) table.

## 5. Implementation

| Script Description                     | Mac                                                                  | Windows                                                               |
|----------------------------------------|----------------------------------------------------------------------|-----------------------------------------------------------------------|
| Builds and runs Backoffice Application | [build_backofficeApp](../../../scripts/backofficeApp_build.sh)       | [build_backofficeApp](../../../scripts/backofficeApp_build.bat)       |
|                                        | [run_backofficeApp](../../../scripts/backofficeApp_run.sh)           | [run_backofficeApp](../../../scripts/backofficeApp_run.bat)           |
| Builds and runs Candidate Application  | [build_run_candidateApp](../../../scripts/build_run_candidateApp.sh) | [build_run_candidateApp](../../../scripts/build_run_candidateApp.bat) |
| Builds and runs Customer Application   | [build_run_customerApp](../../../scripts/build_run_customerApp.sh)   | [build_run_customerApp](../../../scripts/build_run_customerApp.bat)   |

## 6. Integration/Demonstration

To execute all scripts, go to the [scripts](../../../scripts) folder, within project, through the command line.
After that just type the "[script name]".
The script will run accordingly.

## 7. Observations

n/a