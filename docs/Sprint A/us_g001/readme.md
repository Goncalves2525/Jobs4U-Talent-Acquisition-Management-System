# US G001 - Follow the technical constraints and concerns of the project

## 1. Context

Task assigned during sprint A.
This task is intended to set a way to guarantee the teams is complying with the Non-Functional Requirements.

## 2. Requirements

**US G001:** As Project Manager, I want the team to follow the technical constraints and concerns of the project.

**Acceptance Criteria:**

- **G001.NFR01. Programming language**
  - The solution should be implemented using Java as the main language. Other languages can be used in accordance with more specific requirements.
- **G001.NFR02. Technical Documentation**
  - Project documentation should be always available on the project repository ("docs" folder, Markdown format) and, when applicable, in accordance to the UML notation.
  The development process of every US (e.g.: analysis, design, testing, etc.) must be reported (as part of the documentation).
- **G001.NFR03. Test-driven development**
  - The team should develop a relevant set of automated tests for every US / Class / Method.
  The team should aim to adopt a test-driven development approach.
- **G001.NFR04. Source Control**
  - The source code of the solution as well as all the documentation and related artifacts should be versioned in a GitHub repository to be provided to the students.
  Only the main (master/main) branch will be used (e.g., as a source for releases).
- **G001.NFR05. Continuous Integration**
  - The GitHub repository will provide night builds with publishing of results and metrics.
- **G001.NFR06. Deployment and Scripts**
  - The repository should include the necessary scripts to build and deploy the solution in a variety of systems (at least Linux and Windows).
  It should also include a readme.md file in the root folder explaining how to build, deploy and execute the solution.
- **G001.NFR07. Database**
  - By configuration, the system must support that data persistence is done either "in memory" or in a relational database (RDB).
  Although in-memory database solutions can be used during development and testing, the solution must include a final deployment where a persistent relational database is used.
  The system should have the ability to initialize some default data.
- **G001.NFR08. Authentication and Authorization**
  - The system must support and apply authentication and authorization for all its users and functionalities.
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - The support for this functionality must follow specific technical requirements, specified in LPROG. 
  The ANTLR tool should be used (https://www.antlr.org/).
- **G001.NFR10. (RCOMP)**
  - Functionalities related to the Candidate and Customer Apps and to the Follow-Up Server part of the system have very specific technical requirements.
  It must follow a client-server architecture, where a client application is used to access a server.
  Communications between these two components must follow specific protocol described in a document from RCOMP ("Application Protocol").
  Also, the client applications can not access the relational database, they can only access the server application. 
- **G001.NFR11. (RCOMP)**
  - The solution should be deployed using several network nodes.
  It is expected that, at least, the relational database server and the Follow-Up Server be deployed in nodes different from localhost, preferably in the cloud.
  The e-mail notification tasks must be executed in background by the Follow-Up Server.
- **G001.NFR12. (SCOMP)**
  - The base solution for the upload of files must be implemented following specific technical requirements such as the use of the C programming language with processes, signals and pipes.
  Specific requirements will be provided in SCOMP.
- **G001.NFR13. (SCOMP)**
  - An alternative solution for the upload of files must be implemented following specific technical requirements such as the use of the C programming language with shared memory and semaphores.
  Specific requirements will be provided in SCOMP.
- **G001.NFR14. (SCOMP)**
  - The process to count words of very large files should follow specific technical requirements such as implementing parallelism and concurrency using Java and threads.
  Specific requirements will be provided in SCOMP.
- **G001.NFR15. (LAPR4)** 
  - This project has some specific requirements regarding communication and presentation of the project and its results.
  This is a concern of the project, and it's related to the presentations for the sprint reviews in the context of the LAPR4 TP classes (i.e., skills module).
  LAPR4 will provide further specification for this requirement.

**Dependencies/References:**

Regarding this requirement we understand that it relates to all the user stories for the present sprint, as well as all the sprints to come.

## 3. Analysis

*In this section, the team should report the study/analysis/comparison that was done in order to take the best 
design decisions for the requirement. This section should also include supporting diagrams/artifacts 
(such as domain model; use case diagrams, etc.),*

- **G001.NFR01. Programming language**
  - From the document specifications, Java, C, and command-line scripting languages will be required. Other languages might be necessary, or not, during next sprints.
- **G001.NFR02. Technical Documentation**
  - The team as previously used Markdown format documentation, and no further study is needed. It is also consensus that it is the ideal format.
  - Regarding UML documentation (diagrams and others), the team has previous knowledge in using PlantUML and Visual Paradigm.
- **G001.NFR03. Test-driven development**
  - "Test-driven development (TDD) is a software development process relying on software requirements being converted to test cases before 
  software is fully developed, and tracking all software development by repeatedly testing the software against all test cases. 
  This is as opposed to software being developed first and test cases created later."
- **G001.NFR04. Source Control**
  - ...
- **G001.NFR05. Continuous Integration**
  - ...
- **G001.NFR06. Deployment and Scripts**
  - ...
- **G001.NFR07. Database**
  - ...
- **G001.NFR08. Authentication and Authorization**
  - ...
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - ...
- **G001.NFR10. (RCOMP)**
  - ...
- **G001.NFR11. (RCOMP)**
  - ...
- **G001.NFR12. (SCOMP)**
  - ...
- **G001.NFR13. (SCOMP)**
  - ...
- **G001.NFR14. (SCOMP)**
  - ...
- **G001.NFR15. (LAPR4)**
  - ...

## 4. Design

*In these sections, the team should present the solution design that was adopted to solve the requirement.
This should include, at least, a diagram of the realization of the functionality (e.g., sequence diagram),
a class diagram (presenting the classes that support the functionality),
the identification and rationale behind the applied design patterns and the specification of the main tests used
to validate the functionality.*

### 4.1. Realization

- **G001.NFR01. Programming language**
  - A list with the programming languages used will be kept updated within this document.
- **G001.NFR02. Technical Documentation**
  - Markdown is to be used for formal documentation, as well as code snippets (eg: tests).
  - PlantUML and Visual Paradigm are to be used in building diagrams, flow-charts, or conceptual models.
- **G001.NFR03. Test-driven development**
  - All User Stories, and essentially all methods intended to be developed, shall have tests ideated and implemented, before it's developed.
  Predicted entries and outcomes must be defined by the team member responsible by the User Story and/or method.
  Further tests might be implemented during development, based on remaining team inputs. 
- **G001.NFR04. Source Control**
  - ...
- **G001.NFR05. Continuous Integration**
  - ...
- **G001.NFR06. Deployment and Scripts**
  - ...
- **G001.NFR07. Database**
  - ...
- **G001.NFR08. Authentication and Authorization**
  - ...
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - ...
- **G001.NFR10. (RCOMP)**
  - ...
- **G001.NFR11. (RCOMP)**
  - ...
- **G001.NFR12. (SCOMP)**
  - ...
- **G001.NFR13. (SCOMP)**
  - ...
- **G001.NFR14. (SCOMP)**
  - ...
- **G001.NFR15. (LAPR4)**
  - ...

### 4.2. Class Diagram

n/a

### 4.3. Applied Patterns

n/a

### 4.4. Tests

Manual testing/reviews to be performed every friday, from April 8th onwards, through commit inspection and thorough 
documentation presence, content review and team members feedback, for NFRs:
- **G001.NFR01. Programming language**
- **G001.NFR02. Technical Documentation**
- **G001.NFR03. Test-driven development**

Automated tests:
- (...)

Reports of the manual testing/review:
- April 12th
  - (...)
- April 19th
  - (...)
- April 26th
  - (...)
- May 3rd
  - (...)
- May 10th
  - (...)
- May 17th
  - (...)
- May 24th
  - (...)
- May 31st
  - (...)
- June 7th
  - (...)

## 5. Implementation

- **G001.NFR01. Programming language**
  - Java language
  - C language
  - Command language (shell; batch)
- **G001.NFR02. Technical Documentation**
  - [docs readme](../../readme.md)
- **G001.NFR03. Test-driven development**
  - ...
- **G001.NFR04. Source Control**
  - ...
- **G001.NFR05. Continuous Integration**
  - ...
- **G001.NFR06. Deployment and Scripts**
  - ...
- **G001.NFR07. Database**
  - ...
- **G001.NFR08. Authentication and Authorization**
  - ...
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - ...
- **G001.NFR10. (RCOMP)**
  - ...
- **G001.NFR11. (RCOMP)**
  - ...
- **G001.NFR12. (SCOMP)**
  - ...
- **G001.NFR13. (SCOMP)**
  - ...
- **G001.NFR14. (SCOMP)**
  - ...
- **G001.NFR15. (LAPR4)**
  - ...

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a