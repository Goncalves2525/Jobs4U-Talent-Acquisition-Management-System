# Project Jobs4U

## 1. Description of the Project

Jobs4U is an application whose purpose is to develop a solution to help talent acquisition companies, to simplify and optimize processes, related to the selection and recruitment of possible candidates.
These automated recruitment processes provide an interface for all the users involved in Jobs4U (system administrators, customer managers, operators, and candidates).

## 2. Planning and Technical Documentation

All the user stories procedure, as well as the team members who did it, can be found in:
[Planning and Technical Documentation](docs/readme.md)

2.1. **Planning**

For the project's development it was required that teams should use GitHub for code development. Our repository can be found here:
[jobs4U repo](https://github.com/Departamento-de-Engenharia-Informatica/sem4pi-23-24-2nb1)

For that reason, the team decided to use, as its planning and track tool, the option "Project" available by GitHub and created a project linked to the team's repository.
[Creating the project](https://docs.github.com/en/issues/planning-and-tracking-with-projects/creating-projects/creating-a-project)

This project contains templates (some use the kanban style), that are useful for accompanying the user story's status, who is doing it,the sprints due time, each member's capacity, among others.
[Our tracking tool](https://github.com/orgs/Departamento-de-Engenharia-Informatica/projects/168/views/2)

Every user story had a priority (defined by the client) and we classified each one with labels S,M,L and XL according to the quantity of work it was required, being S a small quantity of work required and XL a very large of work required.
In all user stories it was implemented a little bit of pair programming. A team member develops the story and other reviews it and closes or not the US.
[What is pair programming?](https://www.codecademy.com/resources/blog/what-is-pair-programming/)

Imitating a little bit of the good practices of Scrum, we had team reunions during the week, including the ones with LAPR4 PL's teacher at mondays, in which one of the members would "play" scrum master and ask all team members what was done, what needed to improve and what would be done next.
[What is scrum?](https://www.scrum.org/)

In this collaborative way, we make sure the sprint requirements' deadline are met on time.

2.2 **System design**
It were used both Plant UML and Visual Paradigm to generate sequence diagrams for the US and the domain model.

2.3 **System database**
It was used a relational database management system (H2 engine) written in java, for persisting the system data.

Advantages:
- Simple, fast and portable database solution
- Lightweight, embeddable database that supports SQL and JDBC
- Ability to run in-memory or persist data to disk

This is the URL to access the database data:
- URL: jdbc:h2:./db/jobs4u

[H2 database engine](https://www.h2database.com/html/main.html)

## 3. How to Build

To build the project, we can follow these steps:

- First we should clone the repository to our local machine:
  git clone <repository-url>

- Second, make sure JAVA_HOME is set to JDK folder
- Make sure maven is installed and on the system PATH

- Finally, to build the project we must run:
  ./build-all.sh (linux)
  ./build-all.bat (windows)

## 4. How to Execute Tests

For executing tests run:
- mvn test

## 5. How to Run

- Make sure JRE is installed and on the system PATH

- Run ./build_run_backofficeApp.sh

- Run ./build_run_customerApp.sh

- Run ./build_run_candidateApp.sh

## 6. How to Install/Deploy into Another Machine (or Virtual Machine)

To install or deploy the application into another machine or virtual machine, we follow these steps:

- The JRE version should be compatible with the Java version used to compile the .jar file.

- Clone the repository url to your local machine
  [jobs4U repo](https://github.com/Departamento-de-Engenharia-Informatica/sem4pi-23-24-2nb1)

- Make sure Maven, JDK and JRE are installed and on the PATH

## 7. How to Generate PlantUML Diagrams

- To generate plantuml diagrams for documentation execute the script:
./generate-plantuml-diagrams.sh
