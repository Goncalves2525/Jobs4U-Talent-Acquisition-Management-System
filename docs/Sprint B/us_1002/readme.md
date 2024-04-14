# US 1002

## 1. Context

*Customers from different companies will send Job offers to Customer Managers of Jobs4U.
The way these Job Offers are sent is out of the scope of the System.
Each Customer Manager should be able to register a Job Opening based on the Job Offer sent.*

## 2. Requirements

**US 1002** As Customer Manager, I want to register a job opening.

**Acceptance Criteria:**

- 1002.1. It shouldn't be possible to register that is already registered.
- 1002.2. The job opening should have: job reference, title, contract type, mode, address, company, number of vacancies, description, requirements and a state.
- 1002.3. The state of the job opening should be "Application" by default.
- 1002.4. The Job Opening must only be registered if there is a requirements specification available.
- 
**Dependencies/References:**

*Regarding this requirement we understand that it relates to US1008 because the Customer Manager must select one of the requirements specification that was previously loaded by the Language Engineer.*

## 3. Analysis

*In this section, the team should report the study/analysis/comparison that was done in order to take the best design decisions for the requirement. This section should also include supporting diagrams/artifacts (such as domain model; use case diagrams, etc.),*

## 4. Design

*In this sections, the team should present the solution design that was adopted to solve the requirement. This should include, at least, a diagram of the realization of the functionality (e.g., sequence diagram), a class diagram (presenting the classes that support the functionality), the identification and rational behind the applied design patterns and the specification of the main tests used to validade the functionality.*

### 4.1. Realization

### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### 4.3. Applied Patterns

### 4.4. Tests

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