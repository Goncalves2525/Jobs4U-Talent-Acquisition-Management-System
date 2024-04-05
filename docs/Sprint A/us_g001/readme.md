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
  This is as opposed to software being developed first and test cases created later." @[TDD](https://en.wikipedia.org/wiki/Test-driven_development)
- **G001.NFR04. Source Control**
  - "The complete developer platform to build, scale, and deliver secure software."
  - GitHub allows a set of features for collaborative coding and project/team management ([GitHub Features](https://github.com/features)).
  - It was provided a project access and a specific repository for the team to work on.
  This will allow the team to have a source code and documentation control.
  - "In software engineering, version control (also known as revision control, source control, or source code management) is a class of systems 
  responsible for managing changes to computer programs, documents, large web sites, or other collections of information. 
  Version control is a component of software configuration management." @[Source Control](https://en.wikipedia.org/wiki/Version_control)
- **G001.NFR05. Continuous Integration**
  - Given the requirement of using GitHub as the repository, it was analysed the usage of GitHub actions as a mean to continuously integrate the work developed.
  - "GitHub Actions makes it easy to automate all your software workflows, now with world-class CI/CD. Build, test, and deploy your code right from GitHub. 
  Make code reviews, branch management, and issue triaging work the way you want." @[GitHub Actions](https://github.com/features/actions)
- **G001.NFR06. Deployment and Scripts**
  - Shell and Batch scripting files are tools used that allow to quickly run a set of repetitive tasks.
  It can be used to locally build and deploy a runnable executable file.
  - "Shell scripting is a powerful tool commonly used across industries to automate tasks, test solutions, and increase efficiency." @[Shell Scripting](https://www.coursera.org/articles/what-is-shell-scripting)
- **G001.NFR07. Database**
  - Relational databases allow to consistently maintain the data across a database, by relating tables that have related content.
  It is also consistent with data normalization theories.
  - "H2 is a relational database management system written in Java. It can be embedded in Java applications or run in client-server mode." @[About H2](https://en.wikipedia.org/wiki/H2_(database))
  - "The main features of H2 are: Very fast, open source, JDBC API; Embedded and server modes; in-memory databases; 
  Browser based Console application; Small footprint: around 2.5 MB jar file size" @[H2 Features](https://www.h2database.com/html/main.html)
- **G001.NFR08. Authentication and Authorization**
  - To be updated on the next sprints, along with main menu planning.
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - To be updated on the next sprints, once provided specific requirements by the LPROG teachers.
- **G001.NFR10. (RCOMP)**
  - To be updated on the next sprints, once specific protocol are provided: Application Protocol.
- **G001.NFR11. (RCOMP)**
  - To be updated on the next sprints, when it is lectured on how to deploy servers in the cloud and how to implement running applications in them.
- **G001.NFR12. (SCOMP)**
  - "**Processes** are the primitive units for allocation of system resources. Each process has its own address space and (usually) one thread of control. 
  A process executes a program; you can have multiple processes executing the same program, but each process has its own copy of the program within its own address space and executes it independently of the other copies.
  Processes are organized hierarchically. 
  Each process has a parent process which explicitly arranged to create it. 
  The processes created by a given parent are called its child processes. 
  A child inherits many of its attributes from the parent process." @[About processes](https://www.gnu.org/software/libc/manual/html_node/Processes.html)
  - "A **signal** is a software interrupt delivered to a process.
  The operating system uses signals to report exceptional situations to an executing program. 
  Some signals report errors such as references to invalid memory addresses; others report asynchronous events, such as disconnection of a phone line. 
  Some kinds of events make it inadvisable or impossible for the program to proceed as usual, and the corresponding signals normally abort the program.
  Other kinds of signals that report harmless events are ignored by default.
  If you anticipate an event that causes signals, you can define a handler function and tell the operating system to run it when that particular type of signal arrives.
  Finally, one process can send a signal to another process; this allows a parent process to abort a child, or two related processes to communicate and synchronize." @[About signals](https://www.gnu.org/software/libc/manual/html_node/Signal-Handling.html)
  - "A **pipe** is a mechanism for interprocess communication; data written to the pipe by one process can be read by another process. 
  The data is handled in a first-in, first-out (FIFO) order. 
  The pipe has no name; it is created for one use and both ends must be inherited from the single process which created the pipe." @[About pipes](https://www.gnu.org/software/libc/manual/html_node/Pipes-and-FIFOs.html)
- **G001.NFR13. (SCOMP)**
  - To be updated on the next sprints, when it is lectured on how to implement shared memory and semaphores using Java and threads.
- **G001.NFR14. (SCOMP)**
  - To be updated on the next sprints, when it is lectured on how to implement parallelism and concurrency using Java and threads.
- **G001.NFR15. (LAPR4)**
  - To be updated on the next sprints, once provided specific requirements by the LAPR4 TP teacher.

## 4. Design

### 4.1. Realization

- **G001.NFR01. Programming language**
  - A list with the programming languages used will be kept updated within this document.
- **G001.NFR02. Technical Documentation**
  - Markdown is to be used for formal documentation, as well as code snippets (eg: tests).
  - PlantUML and Visual Paradigm are to be used in building diagrams, flow-charts, or conceptual models.
- **G001.NFR03. Test-driven development**
  - All User Stories, and essentially all methods intended to be developed, shall have tests ideated and implemented, before it is developed.
  Predicted entries and outcomes must be defined by the team member responsible by the User Story and/or method.
  Further tests might be implemented during development, based on remaining team inputs. 
- **G001.NFR04. Source Control**
  - Further details on [US G002](../us_g002/readme.md).
- **G001.NFR05. Continuous Integration**
  - Further details on [US G004](../us_g004/readme.md).
- **G001.NFR06. Deployment and Scripts**
  - Further details on [US G005](../us_g005/readme.md).
- **G001.NFR07. Database**
  - By using JDBC and associated libraries, the team will use annotations throughout the java code to implement the database model to H2.
  - Additionally, and to guarantee a baseline of dummy data to be used in the academic setting, a bootstrap will be created, making it possible for applications initial use.
- **G001.NFR08. Authentication and Authorization**
  - To be updated on the next sprints, along with main menu planning.
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - To be updated on the next sprints, once provided specific requirements by the LPROG teachers.
- **G001.NFR10. (RCOMP)**
  - To be updated on the next sprints, once specific protocol are provided: Application Protocol.
- **G001.NFR11. (RCOMP)**
  - To be updated on the next sprints, when it is lectured on how to deploy servers in the cloud and how to implement running applications in them.
- **G001.NFR12. (SCOMP)**
  - Using processes and signals, a service will be continuously looking for new files to be processed.
  Per each new file, the service will be using pipes to gather the new data for the system.
- **G001.NFR13. (SCOMP)**
  - To be updated on the next sprints, when it is lectured on how to implement shared memory and semaphores using Java and threads.
- **G001.NFR14. (SCOMP)**
  - To be updated on the next sprints, when it is lectured on how to implement parallelism and concurrency using Java and threads.
- **G001.NFR15. (LAPR4)**
  - To be updated on the next sprints, once provided specific requirements by the LAPR4 TP teacher.

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
- **G001.NFR07. Database**

Automated tests:
- **G001.NFR12. (SCOMP)** - by creating a new file, and track its lifecycle through the process of being identified and data transferred.

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
  - Command language (shell\bash; batch)
- **G001.NFR02. Technical Documentation**
  - [docs readme](../../readme.md)
- **G001.NFR03. Test-driven development**
  - Read readme for each US, to find the test implemented.
  Follow on this document for weekly validation.
- **G001.NFR04. Source Control**
  - Further details on [US G002](../us_g002/readme.md).
- **G001.NFR05. Continuous Integration**
  - Further details on [US G004](../us_g004/readme.md).
- **G001.NFR06. Deployment and Scripts**
  - Further details on [US G005](../us_g005/readme.md).
- **G001.NFR07. Database**
  - A live connection is established with H2, guaranteeing the most updated model, whenever a new deployment is made.
- **G001.NFR08. Authentication and Authorization**
  - To be updated on the next sprints, along with main menu planning.
- **G001.NFR09. (LPROG) Requirement Specifications and Interview Models**
  - To be updated on the next sprints, once provided specific requirements by the LPROG teachers.
- **G001.NFR10. (RCOMP)**
  - To be updated on the next sprints, once specific protocol are provided: Application Protocol.
- **G001.NFR11. (RCOMP)**
  - To be updated on the next sprints, when it is lectured on how to deploy servers in the cloud and how to implement running applications in them.
- **G001.NFR12. (SCOMP)**
  - To be implemented during sprint B.
- **G001.NFR13. (SCOMP)**
  - To be updated on the next sprints, when it is lectured on how to implement shared memory and semaphores using Java and threads.
- **G001.NFR14. (SCOMP)**
  - To be updated on the next sprints, when it is lectured on how to implement parallelism and concurrency using Java and threads.
- **G001.NFR15. (LAPR4)**
  - To be updated on the next sprints, once provided specific requirements by the LAPR4 TP teacher.

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a