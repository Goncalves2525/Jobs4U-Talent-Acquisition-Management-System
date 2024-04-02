# US G004 - Setup a Continuous Integration Server

## 1. Context

Task assigned during sprint A.


## 2. Requirements

**US G004** As PM, I want the team to setup a continuous integration server.

**Acceptance Criteria:**

- G004.1 The scripts should be able to create Jobs on GitHub actions.
- G004.2 The scripts should be able made in the .yml (YAML) format.
- G004.3 The scripts should be able to run Maven commands (build).
- G004.4 The scripts should provide nightly builds.
- G004.5 The scripts should provide results and metrics.

**Dependencies/References:**

*No dependencies found.*

## 3. Analysis

Previous knowledge has been developed, by the team members, on scripting with command-line, and it is going to be used accordingly.
For sprint A, the needed scripts are intended to build and run each application separately, displaying simple messages contained within each application.
Once the project develops itself, more scripts will be necessary.

Since the project is built on a Maven framework, using Maven commands is essencial.
The main **Maven commands** to use are: validate; compile; test; package; integration-test; verify; install; deploy; clean; site.
More can be found through: [Maven Tools](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html#running-maven-tools)

Within the team it is available both Mac and Windows systems, which will facilitate the testing of scripts.

Script time consumption will be closely related with the commands executed, as well as the complexity of the script itself.
Those two main concepts shall be taken in consideration during script development.
Some other techniques can be used, such as the one's in the article: [Unlocking Maximum Efficiency: How to Speed Up PowerShell Performance for Streamlined Command-Line Operations](https://locall.host/how-to-speed-up-powershell-performance/)

## 4. Design

### 4.1. Realization
In Sprint A, the team is focused on the structure of the project.
We are using GitHub Actions to automate nightly builds.
So that GitHub Actions can run the scripts, we are going to create them in the .yml format.
The scripts are going to be created in the .github folder, inside a "workflows" subfolder.
The scripts are going to be named "cron.yml" and "maven.yml".
The scripts are platform-agnostic.


## 4. Design


> **Night build**
>
> A script that runs every night, building the project and running the tests, if changes have been made.


### 4.1. Class Diagram

n/a

### 4.2. Applied Patterns

n/a

### 4.3. Tests

Tests were made inside the GitHub Actions, and the results were displayed in the Actions tab.

## 5. Implementation

n/a

## 6. Integration/Demonstration

Scripts will be run automatically by GitHub Actions.

## 7. Observations

n/a