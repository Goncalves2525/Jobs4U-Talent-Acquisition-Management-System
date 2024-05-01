# US 1006(b) - Display all the personal data of a candidate, including his/her applications

## 1. Context

The US will allow the Customer Manager to list a given candidate personal data, by searching with his e-mail. <br>
It will also allow him to check the applications the candidate is/was enrolled. <br>

## 2. Requirements

**US 1006** As Customer Manager, I want to display all the personal data of a candidate.
**US 1006b** As Customer Manager, I want to display all the personal data of a candidate, including his/her applications.

**Acceptance Criteria:**

- 1006.1. The customer manager can display the data of any candidate within the system.
- 1006.2. The listed data must include information of candidates application: candidate e-mail (/other id).
- 1006.3. The listed data must include information of candidates application: job opening (id/name).
- 1006.4. The listed data must include information of candidates application: application registry date.
- 1006.5. The listed data must include information of candidates application: application current status.

**Dependencies/References:**

The US has dependency on [2000a](../us_2000a/readme.md), since it is here that an operator registers a candidate on the system. <br>
The US 1006b was included, since it is an additional part of the US 1006. <br>
The US has dependency on the [1005](../us_1005/readme.md), since it needs all the listed applications to access a given candidate
and, through it, display a candidates data, without the need of typing his id/e-mail. <br>

**Question and Answers**

> *"A75. No contexto actual vamos assumir que o Customer Manager pode aceder (consultar) os dados pessoais de qualquer candidato."*

> *"A108. Espera-se que sejam listadas as candidaturas desse candidato indicando a que job opening, qual a data de registo da candidatura, o seu estado e qual o seu identificador."*

> *"A128. (...) Note-se que a US1005 permite obter as candidaturas para uma job opening. Esta US é para mostrar os dados de um candidato.
Portanto parece existir forma de aceder ao id do candidato, caso não se saiba qual é.
Mais uma vez, espero que apliquem boas práticas de UI/UX."*

## 3. Analysis

From the context and requirements, it was identified the following major features to take in consideration towards the design. <br>
- The customer manager can list the personal data and applications information for any candidate on the system.
- The functionality can be achieved through typing the candidate id/e-mail or by selecting a candidate from a list of candidates associated to a job opening.
- All candidates associated applications data must be presented as well.

## 4. Design

### 4.1. Realization

Customer Manager UI has an option to list the required data by typing the candidates id/e-mail. <br>
On the list of job opening applications obtained, the Customer Manager will be able to select one application's candidate and display his personal data. <br>
The list will be just a presentation of data, with no action associated, for now. <br>

#### List Candidate Personal Data by id/e-mail

| Interaction ID                                                                    | Question: Which class is responsible for...                | Answer                      | Justification (with patterns) |
|:----------------------------------------------------------------------------------|:-----------------------------------------------------------|:----------------------------|:------------------------------|
| Step 1 : System presents Customer Manager options                                 | ... presenting the Customer Manager options?               | CustomerManagerUI           | Pure Fabrication              |
| Step 2 : User selects an option                                                   | ... gathering option?                                      | CustomerManagerUI           | Pure Fabrication              |
|                                                                                   | ... calling specific option UI?                            | CustomerManagerUI           | Pure Fabrication              |
| Step 3 : System checks permission to list candidates personal data                | ... starting request of permission authorization?          | ListCandidatePersonalDataUI | Pure Fabrication              |
|                                                                                   | ... requesting current session user role?                  | CustomerManagerUI           | Pure Fabrication              |
|                                                                                   | ... coordinating authentication process?                   | AuthzController             | Controller                    |
|                                                                                   | ... getting the role of current session user?              | UserRepository              | Information Expert            |
| Step 4 : System request to type e-mail of the candidate to have data displayed    | ... requesting data?                                       | ListCandidatePersonalDataUI | Pure Fabrication              |
| Step 5 : User types the e-mail                                                    | ... gathering data?                                        | ListCandidatePersonalDataUI | Pure Fabrication              |
| Step 6 : System collect a copy of the candidate with the associated e-mail        | ... coordinating candidate acquisition process?            | CandidateController         | Controller                    |
|                                                                                   | ... candidate copy acquisition process?                    | CandidateRepository         | Information Expert            |
| Step 7 : System collects a list of the applications with the associated candidate | ... coordinating list of applications acquisition process? | CandidateController         | Controller                    |
|                                                                                   | ... list of applications acquisition process?              | ApplicationsRepository      | Information Expert            |
| Step 8 : System presents the data collected                                       | ... show result?                                           | ListCandidatePersonalDataUI | Pure Fabrication              |

#### List Candidate Personal Data by application selection

| Interaction ID                                                                                | Question: Which class is responsible for...                | Answer                      | Justification (with patterns) |
|:----------------------------------------------------------------------------------------------|:-----------------------------------------------------------|:----------------------------|:------------------------------|
| Step 1 : (uses step 1, 2 and 3 of previous table)                                             | ...                                                        | ...                         | ...                           |
| Step 2 : User selects a Application from a JobOpening                                         | ... gathering selected Application?                        | ListJobOpeningsUI           | Pure Fabrication              |
|                                                                                               | ... requesting to show candidate personal data?            | ListJobOpeningsUI           | Pure Fabrication              |
| Step 3 : System collect a copy of the candidate with the e-mail associated to the application | ... coordinating candidate acquisition process?            | CandidateController         | Controller                    |
|                                                                                               | ... candidate copy acquisition process?                    | CandidateRepository         | Information Expert            |
| Step 4 : System collects a list of the applications with the associated candidate             | ... coordinating list of applications acquisition process? | CandidateController         | Controller                    |
|                                                                                               | ... list of applications acquisition process?              | ApplicationsRepository      | Information Expert            |
| Step 5 : System presents the data collected                                                   | ... show result?                                           | ListCandidatePersonalDataUI | Pure Fabrication              |

According to the taken rationale, the conceptual classes promoted to software classes are:

* (all conceptual classes have been promoted to software classes on previous developed US's)

Other software classes (i.e. Pure Fabrication) identified:

* AuthzController
* UserRepository
* CustomerManagerUI
* ListCandidatePersonalDataUI
* ListJobOpeningsUI
* CandidateController
* CandidateRepository
* ApplicationsRepository

### 4.2. Sequence Diagram

![1006(b) SD-01 List Candidate Personal Data by id/e-mail](1006(b)_SD_01.svg)

![1006(b) SD-02 List Candidate Personal Data by application selection](1006(b)_SD_02.svg)

### 4.3. Tests

**Test 1:** *Tests if all of a set of candidates registered within the system are accessed for data display.*
<br> **Refers to Acceptance Criteria:** 1006.1.

```java
@Test
public void canDisplayDataOfAnyCandidateWithinSystem() {  }
````

**Test 2:** *Validates that the candidate e-mail (/other id) is displayed.*
<br> **Refers to Acceptance Criteria:** 1006.2.

```java
@Test
public void candidateEmailIsDisplayed() {  }
````

**Test 3:** *Validates that the job opening (id/name) is displayed.*
<br> **Refers to Acceptance Criteria:** 1006.3.

```java
@Test
public void candidateEmailIsDisplayed() {  }
````

**Test 4:** *Validates that the application registry date is displayed.*
<br> **Refers to Acceptance Criteria:** 1006.4.

```java
@Test
public void candidateEmailIsDisplayed() {  }
````

**Test 5:** *Validates that the application current status is displayed.*
<br> **Refers to Acceptance Criteria:** 1006.5.

```java
@Test
public void candidateEmailIsDisplayed() {  }
````

## 5. Implementation

Implementation was done as an option to list candidate details by providing the e-mail. <br>
Still pending the integration of listing details after selecting one application on a job opening search. <br>
The functionality is implemented though, and available for integration.

> Commit list (descending)
>
> c2e078e4277fb64f7340efe4ce0b82c6dd41a4ef

## 6. Integration/Demonstration

No major integration was needed for the US. <br>
It was only added a new menu option on the Customer Manager UI. <br>
The access to the functionality follows the same pattern as previous Customer Manager functionalities. <br>

## 7. Observations

N/A