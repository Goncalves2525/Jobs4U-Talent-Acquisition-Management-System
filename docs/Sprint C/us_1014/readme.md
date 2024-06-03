# US 1014 - Record the date and time of an Interview with a candidate

## 1. Context

* Customer Managers are required to record the date and time of an interview with a candidate. This information is important for the Customer Manager to be able to schedule the interview with the candidate and to be able to follow up on the interview. 

## 2. Requirements

**US 1014** 

**Acceptance Criteria:**

- 1014.1. The system should allow the Customer Manager to record the date and time of an interview with a candidate.

**Dependencies/References:**


## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](domain_model.png)

### 3.2. Questions and Answers
> **Question: Uma entrevista pode ter apenas uma questão? US1014, time and date, quer dizer data de inicio e não data final? Podem haver entrevistas em paralelo?** 
> 
> **Answer: Quanto ao número de perguntas numa entrevista, não está definido nenhum limite inferior ou superior. Ou seja, pode haver uma entrevista com apenas 1 pergunta (não fará sentido não ter perguntas). A US1014 refere-se à marcação da data de uma entrevista com um candidato. Algo como indicar o dia e a hora (ex: 23 de abril pelas 14:00). Em relação à marcação de entrevistas “sobrepostas” (com a mesma data de inicio), neste momento, seria algo a permitir (pode, por exemplo, o customer manager delegar noutra pessoa a condução da entrevista). Isso não invalida que devam validar se as entrevistas ocorrem dentro da fase das entrevistas.** 

> **Question: Em relação à marcação da intervista, só deve ser possível marcar esta data quando? Só será possível marcar a entrevista quando a fase de recrutamento se encontrar na fase de intervista? Ou será possivel marcar noutras fases anteriores?**
> 
> **Answer: Por mim pode ser possível marcar as entrevistas antes mas deve-se ter em atenção se o candidato “passou” o screening. Não faz sentido marcar uma entrevista para um candidato que não foi aceite. Tenham em atenção este tipo de aspetos.**

## 4. Design

### 4.1. Realization

| Interaction ID | Question: Which class is responsible for...              | Answer                          | Justification (with patterns)                                     |
|:---------------|:---------------------------------------------------------|:--------------------------------|:------------------------------------------------------------------|
| Step 1         | Initiating the interview scheduling process              | CustomerManager                 | The CustomerManager starts the process by inputting candidate details. |
| Step 2         | Displaying input prompts for candidate, date, and time   | RegisterInterviewDateUI         | The UI class is responsible for gathering input from the user.       |
| Step 3         | Handling user input and forwarding to the controller     | RegisterInterviewDateController | The Controller processes the input and coordinates further actions.  |
| Step 5         | Retrieving the candidate's application                   | JpaApplicationRepository        | The Repository is responsible for accessing stored application data. |
| Step 6         | Setting the interview date in the application            | Application                     | The Application class holds the interview date once validated.       |
| Step 7         | Saving the updated application                           | ApplicationRepository           | The Repository saves the modified application data.                  |
| Step 8         | Informing the CustomerManager of the result              | RegisterInterviewDateUI         | The UI provides feedback to the user based on the outcome.           |


Other software classes (i.e. Pure Fabrication) identified:

* CustomerManagerUI
* RegisterInterviewDateUI
* RegisterInterviewDateController
* JpaApplicationRepository

### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### 4.3. Sequence Diagram

![a sequence diagram](sequence-diagram.svg "A Sequence Diagram")

### 4.4. Tests

**Test 1:** *  *

**Refers to Acceptance Criteria:** 1014.1


```java
// Test for Acceptance Criteria 1014.1: Validating the date and time of the interview
    @Test
    public void testValidateInterviewDateAndTime() {
        Date invalidInterviewDate = new Date(System.currentTimeMillis() - 100000); // A past date
        boolean success = controller.registerInterviewDateToApplication(application, invalidInterviewDate);
        assertFalse("The system should not allow recording an invalid interview date and time.", success);

        Date validInterviewDate = new Date(System.currentTimeMillis() + 100000); // A future date
        success = controller.registerInterviewDateToApplication(application, validInterviewDate);
        assertTrue("The system should allow recording a valid interview date and time.", success);
    }

````



## 5. Implementation
**Application**

```java
package applicationManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

import java.time.LocalDate;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"jobReference", "email"}))
public class Application implements AggregateRoot<String>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String jobReference;

    @ManyToOne
    @JoinColumn(name = "email")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "jobOpening")
    private JobOpening jobOpening;

    @Column
    private ApplicationStatus status;

    @Column
    private LocalDate date;

    @Column
    private String InterviewModel;

    @Column
    private String comment;

    @Column
    private Date applicationDate;

    @Column
    private String filePath;

    @Column
    private String applicationFilesPath;

    @Column
    private Date interviewDate;


    protected Application() {
        // for ORM
    }

    public Application(String jobReference, Candidate candidate, JobOpening jobOpening, ApplicationStatus status, Date applicationDate, String comment
    , String interviewModel, String filePath, String applicationFilesPath) {
        this.jobReference = jobReference;
        this.candidate = candidate;
        this.jobOpening = jobOpening;
        this.status = status;
        this.applicationDate = applicationDate;
        this.comment = comment;
        this.InterviewModel = interviewModel;
        this.date = LocalDate.now();
        this.filePath = filePath;
        this.applicationFilesPath = applicationFilesPath;
    }

    public String jobReference() {
        return jobReference;
    }

    public Candidate candidate() {
        return candidate;
    }

    public JobOpening jobOpening() {
        return jobOpening;
    }

    public ApplicationStatus status() {
        return status;
    }

    public Date applicationDate() {
        return applicationDate;
    }

    public String comment() {
        return comment;
    }

    public String interviewModel() {
        return InterviewModel;
    }

    public String filePath() {
        return filePath;
    }

    public String applicationFilesPath() {
        return applicationFilesPath;
    }

    public Date interviewDate() {
        return interviewDate;
    }

    @Override
    public String toString() {
        return "Application{" +
                "Job Reference='" + jobReference + '\'' +
                ", Candidate =" + candidate +
                ", Job Opening=" + jobOpening +
                ", Status=" + status +
                //", InterviewModel=" + InterviewModel +
                ", Commend='" + comment + '\'' +
                ", Application Date=" + applicationDate +
                ", Interview Model Path='" + filePath + '\'' +
                ", Application Files Path='" + applicationFilesPath + '\'' +
                '}';
    }

    public boolean checkIfApplicationHasInterviewModel() {
        return InterviewModel != null;
    }

    public boolean checkIfApplicationHasInterviewDate() {
        return interviewDate != null;
    }


    public boolean associateInterviewModelToApplication(String interviewModel) {
        this.InterviewModel = interviewModel;
        return true;
    }

    public boolean registerInterviewDateToApplication(Date interviewDate) {
        this.interviewDate = interviewDate;
        return true;
    }


    @Override
    public boolean sameAs(Object other) {
        Application application = (Application) other;
        return jobReference.equals(application.identity());
    }

    @Override
    public String identity() {
        return id.toString();
    }

    public void changeStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void changeJobOpeningRecruitmentState(RecruitmentState newState) {
            jobOpening.setState(newState);
    }
}


````




## 6. Integration/Demonstration

For integration and demonstration, consider the following steps:

Setup Database/Repository:
Ensure the ApplicationRepository is correctly initialized and populated with candidate applications.

User Interface:
Create a simple user interface (e.g., a web form or a console application) to simulate the Customer Manager entering candidate interview details.

Demo Script:

Show the UI prompting for candidate selection, date, and time.
Demonstrate the system handling valid and invalid inputs.
Highlight the interaction between UI, Controller, and Service classes.

## 7. Observations

n/a