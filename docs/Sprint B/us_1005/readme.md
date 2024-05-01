# US 1005

*List all applications for a Job Opening*

## 1. Context

*Whenever a Customer Manager intends to, he should be able to list all the applications that refer to a specific job opening*

## 2. Requirements

**US 1005** As Customer Manager, I want to list all applications for a job opening.

**Acceptance Criteria:**

- 1005.1. Each application must have the candidate identification and the state of his application.

**Dependencies/References:**

*Regarding this requirement we understand that it relates to:*

> 1002-  As Customer Manager, I want to register a job opening
> 
> 2002 - As Operator, I want to register an application of a candidate for a job opening and import all files received.

## 3. Analysis

**Questions/Answers:**

>"Q63:  Relativamente aos critérios para a listagem das candidaturas: Devem aparecer candidaturas que estão a decorrer ou podem aparecer candidaturas feitas no passado? 
Podem aparecer quaisquer candidaturas ou apenas as que tenham sido aceites? Que informação deverá ser mostrada em cada candidatura?"
> 
>"A63. Tal como refere a descrição da US, devem ser listadas todas as candidaturas para um job opening. Faz sentido mostrar todas as candidaturas,
independentemente do seu estado. Assim, para cada cada candidatura deve ser identificado o candidato e o estado da sua candidatura."
> 
> "Q129 Pinto – US1005- O customer manager vai receber a lista de todas as job Openings e selecionará uma, feito isto deve aparecer 
as job applications correspondentes. Que informações das job applications tem que ser mostradas ao listar?"
> 
> "A129. As candidaturas são de um candidato (pessoa), pelo acho que deve aparecer a identificação da candidatura (application), assim como 
a identificação do candidato e o seu nome."

### 3.1. System Sequence Diagram

![System Sequence Diagram - US1005](SSD/system_sequence_diagram_1005.svg)

## 4. Design

### 4.1. Realization

| Interaction ID                                                            | Question: Which class is responsible for... | Answer                                     | Justification (with patterns) |
|:--------------------------------------------------------------------------|:--------------------------------------------|:-------------------------------------------|:------------------------------|
| Step 1 : Customer Manager requests to list applications for a job opening |                                             |                                            |                               |
| Step 2 : System builds list of Job Openings                               | ... requesting list to be built?            | ListApplicationsUI                         | Pure Fabrication              |
|                                                                           | ... coordinating list capture?              | ListJobOpeningController                   | Controller                    |
|                                                                           | ... building the applications list?         | JobOpeningRepository                       | Information Expert            |
| Step 3 : System presents the list of Job Openings                         | ... show result?                            | ListApplicationUI                          | Pure Fabrication              |
| Step 4 : Customer manager chooses Job Opening from list                   | ... requesting info?                        | ListApplicationUI                          | Pure Fabrication              |
| Step 4 : System builds list of applications                               | ... requesting list to be built?            | ListApplicationsUI                         | Pure Fabrication              |
|                                                                           | ... coordinating list capture?              | ListApplicationController                  | Controller                    |
|                                                                           | ... building the applications list?         | ApplicationRepository                      | Information Expert            |                                          |                      |                               |
|                                                                           | ... show list?                              | ListApplicationsUI                         | Pure Fabrication              |


### 4.2. Class Diagram

![Class diagram](CD\class-diagram-01.svg "Class Diagram")

### 4.3. Sequence Diagram

![Sequence Diagram](SD\sequence-diagram.svg "Sequence Diagram")

### 4.4. Tests

**Test 1:** *Verifies that the job opening ID exists*

```java
@Test
void ensureJobReferenceNotNull() {
    assert (application.getJobReference() != null);
}
````

## 5. Implementation

**ListApplicationsUI**

```java
package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.ListApplicationsController;
import applicationManagement.domain.Application;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

public class ListApplicationsUI {

    ListApplicationsController ctrl = new ListApplicationsController();
    ListJobOpeningsController ctrlJobOpening = new ListJobOpeningsController();

    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("List Applications");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
            return;
        }

        System.out.println("Job Openings:");
        Iterable<JobOpening> jobOpenings = ctrlJobOpening.listJobOpenings();

        // Check if jobOpenings is empty
        if (!jobOpenings.iterator().hasNext()) {
            System.out.println("No job openings found.");
        } else {
            // Iterate over jobOpenings if it's not empty
            for (JobOpening jobOpening : jobOpenings) {
                System.out.println(jobOpening.toString());
            }
        }

        String jobReference = ConsoleUtils.readLineFromConsole("Insert the Job Reference:");

        System.out.println("Applications for the Job Reference inserted:");
        Iterable<Application> applications = ctrl.listApplications();

        // Check if application is empty
        if (!applications.iterator().hasNext()) {
            System.out.println("No applications found.");
        } else {
            // Iterate over applications if it's not empty
            for (Application application : applications) {
                if (application.getJobReference().equals(jobReference))
                    System.out.println(application.toString());
            }
        }
    }
}
```

**ListApplicationsController**

```java
package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;

public class ListApplicationsController {
    private ApplicationRepository repo = PersistenceContext.repositories().applications();

    public Iterable<Application> listApplications() {
        return repo.findAll();
    }
}
```

**CustomerManagerUI**

```java
package presentation.CustomerManager;


import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import presentation.Operator.RegisterApplicationUI;
import textformat.AnsiColor;

import java.util.ArrayList;
import java.util.List;

public class CustomerManagerUI {


    public void doShow(AuthzUI authzUI) {

        // build UI header:
        ConsoleUtils.buildUiHeader("Jobs4U Backoffice for Customer Manager");

        // set option variable, list of options, selection message, and exit name (eg.: exit / cancel / etc.)
        int option;
        List<String> options = new ArrayList<>();
        options.add("Register Customer");               // 1
        options.add("Register Job Opening");            // 2
        options.add("List Job Openings");               // 3
        options.add("Select Interview Model");          // 4
        options.add("List Candidate Personal Data");    // 5
        options.add("Test Plugin");                     // 6
        options.add("List Applications For Job Opening");// 7
        String message = "What do you want to do?";
        String exit = "Exit";

        // run options menu
        do {
            option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            switch (option) {
                case 0:
                    break;
                case 1:
                    RegisterCustomerUI registerCustomerUI = new RegisterCustomerUI();
                    registerCustomerUI.doShow(authzUI);
                    break;
                case 2:
                    RegisterJobOpeningUI registerJobOpeningUI = new RegisterJobOpeningUI();
                    registerJobOpeningUI.doShow(authzUI);
                    break;
                case 3:
                    ListJobOpeningsUI listJobOpeningsUI = new ListJobOpeningsUI();
                    listJobOpeningsUI.doShow(authzUI);
                    break;
                case 4:
                    SelectInterviewModelUI selectInterviewModelUI = new SelectInterviewModelUI();
                    selectInterviewModelUI.doShow(authzUI);
                    break;
                case 5:
                    ListCandidatePersonalDataUI listCandidatePersonalDataUI = new ListCandidatePersonalDataUI();
                    listCandidatePersonalDataUI.doShow(authzUI);
                    break;
                case 6:
                    TestPluginUI testPluginUI = new TestPluginUI();
                    testPluginUI.doShow(authzUI);
                    break;
                case 7:
                    ListApplicationsUI listApplicationsUI = new ListApplicationsUI();
                    listApplicationsUI.doShow(authzUI);
                    break;
                default:
                    ConsoleUtils.showMessageColor("Invalid option! Try again.", AnsiColor.RED);
            }
        } while (option != 0);
    }
}
```

## 6. Integration/Demonstration

>

## 7. Observations

>