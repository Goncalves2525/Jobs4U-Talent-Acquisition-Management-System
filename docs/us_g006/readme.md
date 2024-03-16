# US G006

*Elaborate the Domain Model using DDD.*

## 1. Context

*This is a new task. DDD stands for "Domain-Driven Design" which is a software develompent appproach that focuses on the processes and rules of the projects domain.*

## 2. Requirements

**US G006** s Project Manager, I want the team to elaborate a Domain Model using DDD.

**Acceptance Criteria:**

- G006.1. The entities should be identified.
- G006.2. The value objects should be identified.
- G006.3. The aggregates should be identified.
- G006.4. 

**Dependencies/References:**

*Regarding this requirement we understand that it relates to...*

## 3. Analysis

*In this section, the team should report the study/analysis/comparison that was done in order to take the best design decisions for the requirement. This section should also include supporting diagrams/artifacts (such as domain model; use case diagrams, etc.),*

 


## 4. Design

*In this section, the team should present the solution design that was adopted to solve the requirement. This should include, at least, a diagram of the realization of the functionality (e.g., sequence diagram), a class diagram (presenting the classes that support the functionality), the identification and rational behind the applied design patterns and the specification of the main tests used to validade the functionality.*

### 4.1. Realization

### Domain Model 1
![domain model](domain_model_attemps/domain_model1.jpeg)

*This was the first domain model aproach.*

*Problems:*
* Usage of system users which are irrelevant for the domain model.
* Not kwoing the difference between entities and value objects yet.
* Not knowing how "Interview" should be represented.
* Too simple.

*At this point we started asking the client questions and improved our aproach.*

**Question:** "As entrevistas são feitas presencialmente? Se sim, quem é responsável por registar essas respostas no sistema?"

**Answer:** "O meio usado para as entrevistas está fora do âmbito do sistema. Podem ser presenciais, remotas (ex: telefone ou outro meio). Independentemente do meio, o Customer Manager é o responsável por registar as respostas no sistema, através da submissão (upload) do ficheiro de texto com as respostas do candidato."

**Question:** "Quem é responsável por analisar as candidaturas (applications)?"

**Answer:** "Será o Customer Manager. Este analisa as candidaturas e decide o ranking dos candidatos."


### Domain Model 2
![domain model](domain_model_attemps/domain_model2.png)

*In our second aproach, we started to distinguish entities from value objects and added services.*

*Problems:*
*


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