# US 1011

## 1. Context

*Along the recruitment process that is defined by the Customer Manager, there is an optional phase "Interview". During this phase, the Customer Manager may use a plugin that what configured by the Language Engineer that will help him create, validate and evaluate questions and answers for an Interview.*

## 2. Requirements

**US 1011** As Customer Manager, I want to select the interview model to use for the interviews of a job opening (for their evaluation/grading).

**Acceptance Criteria:**

- 1011.1. If a candidate was already interviewed, the Customer Manager can't change the interview model.


**Dependencies/References:**

*Regarding this requirement we understand that it relates to US1008 where the Language Engineer must first create and deploy the plugins that the Customer Manager will later on select.*


## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](domain_model.png)

### 3.2. Questions and Answers
> **Question:** Cada questão de um interview model aceita um x tipos de respostas(ex escolha múltipla) ou é a interview model que aceita um x tipos de respostas em todas as suas questões? Assumimos que uma job opening só segue um interview model?
> 
> **Answer:** Sim, cada pergunta/resposta aceita um tipo de pergunta/resposta (um dos tipos que aparece no inicio da página 8). Na US1011, o Customer manager seleciona o interview model a usar nas entrevistas para um job opening. Ou seja, existirá apenas um interview model a usar nas entrevistas desse job opening.


### 3.3. Other Remarks
The plugins (jar files) are in directory plugins/interview/jar.

## 4. Design

### 4.1. Realization

| Interaction ID                                                                            | Question: Which class is responsible for...                            | Answer                    | Justification (with patterns) |
|:------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------|:--------------------------|:------------------------------|
| Step 1 : Customer Manager requests to select an interview model for a certain application | 	... showing available interview models?                               | SelectInterviewModelUI    | Pure Fabrication              |
| 		                                                                                        | 	... showing available candidate applications?                         | SelectInterviewModelUI    | Pure Fabrication              |
| Step 2 : System assotiates interview model to application                                 | 	... coordination between users request and selecting interview model? | SelectInterviewController | Controller                    |
| 		                                                                                        | 	... accessing the plugin?                                             | PluginLoader              | Pure Fabrication              |
| 		                                                                                        | 	... saving the interview model?                                       | Application               | Information Expert            |
| Step 3 : System informs Customer Manager of success/failure of operation                  | 	... showing success/failure?                                          | SelectInterviewModelUI    | Pure Fabrication              |


According to the taken rationale, the conceptual classes promoted to software classes are:

* Application

Other software classes (i.e. Pure Fabrication) identified:

* SelectInterviewModelUI
* SelectInterviewController
* PluginLoader


### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### 4.3. Sequence Diagram

![a sequence diagram](sequence-diagram.svg "A Sequence Diagram")

### 4.4. Tests

**Test 1:** **

**Refers to Acceptance Criteria:** 1011.1


```java
@Test
void ensureApplicationDoesNotHaveInterviewModel() {

}

````




## 5. Implementation
****

**SelectInterviewModelUI**

```java

```
**SelectInterviewModelController**

```java

```

**PluginLoader**

```java

```

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a