# US G002 - Use the defined project repo, and set up a tool for project management

## 1. Context

Task assigned during sprint A.
This task is meant to set up the grounds for the team routines during each US, Sprint, and the Project as a whole.
The main goal is to have a tool, or several tools, that allow the team members to implement an agile framework, where task can be settled, configurated and followed along the Project development.

## 2. Requirements

**US G002** As Project Manager, I want the team to use the defined project repo, and set up a tool for project management.

**Acceptance Criteria:**

- G002.1. Use GitHub repository created by teachers.
- G002.2. Use any of the available templates in GitHub for project management.

**Dependencies/References:**

No dependencies found.

## 3. Analysis

The following analysis is divided by the two main goals for the US: **Setup Repository** and **Setup Tool for Project Management**.

> **Setup Repository**

Given the acceptance criteria G002.1. the team has to use the repository that was prepared by the teachers, within the **Organization** *Departamento-de-Engenharia-Informatica*.
No decisions are to be taken for this part.
The team has to familiarize with the tool and the features that allow communication with the project management tool, as well as with the IDE in use by each team member.

> **Setup Tool for Project Management**

Taking in consideration the acceptance criteria G002.2., all the GitHub Project Management templates were analysed, as well as each of the tabs available.
It is worth noting that all the templates allow to add new items/ new tabs.

* **Team Planning**: *Manage your team's work items, plan upcoming cycles, and understand team capacity.*
  * *Tabs*: Backlog; Team capacity; Current iteration; Roadmap; My items.
* **Feature Release**: *Manage your team's prioritized work items when planning for a feature release.* 🚀
  * *Tabs*: Prioritized backlog; Status board; Roadmap; Bugs; In review.
* **Kanban**: *Visualize the status of your project and limit work in progress.*
  * *Tabs*: Backlog; Priority board; Team items; Roadmap; In review.
* **Bug Tracker**: *Track and triage your bugs.* 🐛
  * *Tabs*: Prioritized bugs; Triage; In review; My items.
* **Iterative development**: *Plan your current and upcoming iterations as you work through your prioritized backlog of items.*
  * *Tabs*: Current iteration; Next iteration; Prioritized backlog; Roadmap; In review; My items.
* **Product launch**: *Manage work items across teams and functions when planning for a product launch.* 🚀
  * *Tabs*: Prioritized backlog; Status board; Current iteration; Roadmap; Bugs; My items.
* **Roadmap**: *Manage your team's long term plans as you plan out your roadmap.*
  * *Tabs*: Monthly roadmap; Quarterly roadmap; Backlog.
* **Team Retrospective**: *Reflect as a team what went well, what can be improved next time, and action items.*
  * *Tabs*: Retrospective; Categorized feedback; Action items.

## 4. Design

### 4.1. Realization

Regarding the Tool for Project Management, it is taken in consideration the Analysis and previous work/projects developed by the team.
Thus, it will be selected the template **Team Planning**.
This template encompasses all the features that the team was used to: Backlog; Team capacity; Current iteration; Roadmap; My items.
It won't be added any other *tab* to the tool.
Nevertheless, to make sure that all the team will be aware of the project development, regarding each US, a phase will be added to the Backlog tab.
So, instead of having only: Todo; In Progress; Done - it will be added the phase Team Review.

### 4.2. Class Diagram

n/a

### 4.3. Applied Patterns

n/a

### 4.4. Tests

All the acceptance criteria are met through observation of the Repository and Project Management tool location.
Both have to be within **Organization** *Departamento-de-Engenharia-Informatica*, as per requirements (G002.1).
By observation, we can compare the GitHub Project Templates features with the one in use (G002.2).
Given that the selected template was "Team Planning", it is expected that the following tabs are in use and updated:
* Backlog
* Team capacity
* Current iteration
* Roadmap
* My items

No other view shall be present, as it was not part of the team design for this US.

## 5. Implementation

The implementation of this US can be found through the weblinks below, given account access for each.

> Repository:
> https://github.com/Departamento-de-Engenharia-Informatica/sem4pi-23-24-2nb1

> Project Management:
> https://github.com/orgs/Departamento-de-Engenharia-Informatica/projects/168

## 6. Integration/Demonstration

All team members had to create a GitHub account with ISEP e-mail address, and access the team previously created by the teachers.

The team members added to the backlog all the US available
Assigned the tags for: Sprint iteration, Start and End date, Priority, and Size.
Also, the backlog entry was set as an issue, so it can be matched to future commits.

## 7. Observations

n/a