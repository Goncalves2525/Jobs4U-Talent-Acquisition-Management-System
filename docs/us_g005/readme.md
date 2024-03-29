# US G002

*Create scripts to make it easier to run the apps.*

## 1. Context

*This is a new Task. If a user wants to execute the apps, he must first create a jar file and the run the necessary command to run it. If we create scripts that do all of that work automatically, running the apps will be faster and easier.*

## 2. Requirements

**US G005** As Project Manager, I want the team to add to the project the necessary scripts,
so that build/executions/deployments/... can be executed e￿ortlessly.

**Acceptance Criteria:**

- G005.1. The scripts should be able to create the jar file.

- G005.2. The scripts should be able to run the jar file.

- G005.3. There should be a script for each app.

- G005.4. There should be scripts for linux/mac and windows.

**Dependencies/References:**

*No dependencies found.*

## 3. Analysis

In Sprint A, the team is focused on the structure of the project. We are using Maven to manage the project and the dependencies.
We decided that, at least, three apps should be created: one for the Customer, one for the Candidate and the Backoffice for the Customer Manager and the Operator.
To run the apps, the user must first create the jar file and then run the necessary command to run it. This process can be time-consuming and error-prone.
To make it easier for the user, we are going to create scripts that do all of that work automatically.
There will be a script for each app and for each operating system.
For Windows, we are going to use a batch files (.bat) and for Linux/Mac, we are going to use shell scripts (.sh).
## 4. Design

### 4.1. Realization
<br>

#### 4.1.1. Linux/Mac
To build the jar file, we are going to use the following command:
```
mvn clean package
```

To run the jar file, we are going to use the following command:
```
java -jar jobs4u.backofficeApp-0.1.0.jar
```
<br><br>

#### 4.1.2. Windows
To build the jar file, we are going to use the following command:
```
call mvn clean package
```

To run the jar file, we are going to use the following command:
```
java -jar jobs4u.backofficeApp-0.1.0.jar
```
<br>

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
### 4.2. Applied Patterns

### 4.3. Tests

Include here the main tests used to validate the functionality. Focus on how they relate to the acceptance criteria.

**Test 1:** *Verifies that it is not possible to ...*

**Refers to Acceptance Criteria:** G002.1


```
@Test(expected = IllegalArgumentException.class)
public void ensureXxxxYyyy() {
	...
}
````

## 5. Implementation

*In this section the team should present, if necessary, some evidencies that the implementation is according to the design. It should also describe and explain other important artifacts necessary to fully understand the implementation like, for instance, configuration files.*

*It is also a best practice to include a listing (with a brief summary) of the major commits regarding this requirement.*

## 6. Integration/Demonstration

*In this section the team should describe the efforts realized in order to integrate this functionality with the other parts/components of the system*

*It is also important to explain any scripts or instructions required to execute an demonstrate this functionality*

## 7. Observations

*This section should be used to include any content that does not fit any of the previous sections.*

*The team should present here, for instance, a critical prespective on the developed work including the analysis of alternative solutioons or related works*

*The team should include in this section statements/references regarding third party works that were used in the development this work.*