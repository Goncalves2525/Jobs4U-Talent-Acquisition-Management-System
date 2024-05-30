# US 1021 - As a Customer Manager, I want to display all the data of an application

## 1. Context

* * The customer manager needs to have access to the imported files and generated data, such as interviews, from a job application of a candidate.

## 2. Requirements

** **

**Acceptance Criteria:**

- The client didn't give any acceptance criteria in the system specification pdf file. 

**Dependencies/References:**

*Regarding the requirements we understand that it relates to:

- 2002: As Operator, I want to register an application of a candidate for a job opening and import all files received.


## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](Partial_DM/DM_US1021.JPG)

### 3.2. Questions and Answers
> **Question 36: O que é "all data of an application? O que é uma job application?** 
> 
> **Answer 36: Uma job application é uma candidatura (de um candidato) a uma job opening. 
Relativamente ao “all data of an application” refere-se a todos os dados de uma candidatura, nomeadamente, 
os ficheiros submetidos pelos candidato assim como dados recolhidos ou gerados durante o processo 
(como as entrevistas e processamento de requisitos).** 
>



## 4. Design

### 4.1. Realization

| Interaction ID                               | Question: Which class is responsible for... | Answer                     | Justification (with patterns) |
|:---------------------------------------------|:--------------------------------------------|:---------------------------|:------------------------------|
| Step 1 : ask to show application information | ... interacting with the user?              | CheckApplicationDataUI     | No reason to delegate         |
|                                              | ... coordinating the us?                    | ListApplicationsController | Controller                    |
| Step 2 : request application id              | ... requesting data?                        | CheckApplicationDataUI     | Pure Fabrication              |                               |
| Step 3 : insert information                  | ... store information?                      | CheckApplicationDataUI     | Pure Fabrication              |                               |
|                                              | ... get list of applications?               | ListApplicationsController | Controller                    |
|                                              | ... having the list of applications?        | ApplicationRepository      | Information Expert            |
|                                              | ... knowing the data of the applications?   | Application                | Information Expert            |
| Step 4 : show the application data           | ... show the data of the application?       | CheckApplicationDataUI     | Pure Fabrication              |

According to the taken rationale, the conceptual classes promoted to software classes are:

* CheckApplicationDataUI

Other software classes (i.e. Pure Fabrication) identified:

* Application
* ApplicationRepository
* ListApplicationsController


### 4.2. Class Diagram

![a class diagram](CD/class-diagram.svg "US 1021 - Class Diagram")

### 4.3. Sequence Diagram

![a sequence diagram](SD/sequence-diagram.svg "A Sequence Diagram")

### 4.4. Tests

**Test 1:** *  *

**Refers to Acceptance Criteria:** 1021.1


```java


````



## 5. Implementation
**Customer**

```java


````


## 6. Integration/Demonstration

n/a

## 7. Observations

n/a