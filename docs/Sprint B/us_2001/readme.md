# US 2001 - System must, continuously, process the files produced by the Applications Email Bot, so that they can be imported into the system by initiative of the Operator.

## 1. Context

Candidates send Applications for Job Openings via email. 
These emails are processed by an Email Bot which is out of the scope of our system.
We are to create a File Bot that will monitor the email files and redirect them to a hierarchy of folders that are organized: job_references/candidate_id/[files].
These folder structure will be used by an operator to further validate candidates applications.

## 2. Requirements

**US 2001** As Product Owner, I want the system to, continuously, process the files
produced by the Applications Email Bot, so that they can be imported into the system
by initiative of the Operator.

**Acceptance Criteria:**

- 2001.1. If the folder with a new job_reference/candidate_id does not exist, it should be created.
- 2001.2. If the folder with a job_reference/candidate_id already exists, the file should be moved to it.
- 2001.3. If there is no job reference, the files of that application must not be imported.
- 2001.4. If there is no candidate email, the files of that application must not be imported.
- 2001.5. If there are missing files for a given application, a message shall be emitted.

**Dependencies/References:**

*No dependencies found.*

## 3. Analysis

> **Processes**
> 
> "**Processes** are the primitive units for allocation of system resources. Each process has its own address space and (usually) one thread of control.
  A process executes a program; you can have multiple processes executing the same program, but each process has its own copy of the program within its own address space and executes it independently of the other copies.
  Processes are organized hierarchically.
  Each process has a parent process which explicitly arranged to create it.
  The processes created by a given parent are called its child processes.
  A child inherits many of its attributes from the parent process." @[About processes](https://www.gnu.org/software/libc/manual/html_node/Processes.html)

> **Signals**
>
> "A **signal** is a software interrupt delivered to a process.
  The operating system uses signals to report exceptional situations to an executing program.
  Some signals report errors such as references to invalid memory addresses; others report asynchronous events, such as disconnection of a phone line.
  Some kinds of events make it inadvisable or impossible for the program to proceed as usual, and the corresponding signals normally abort the program.
  Other kinds of signals that report harmless events are ignored by default.
  If you anticipate an event that causes signals, you can define a handler function and tell the operating system to run it when that particular type of signal arrives.
  Finally, one process can send a signal to another process; this allows a parent process to abort a child, or two related processes to communicate and synchronize." @[About signals](https://www.gnu.org/software/libc/manual/html_node/Signal-Handling.html)

> **Pipes**
>
> "A **pipe** is a mechanism for interprocess communication; data written to the pipe by one process can be read by another process.
  The data is handled in a first-in, first-out (FIFO) order.
  The pipe has no name; it is created for one use and both ends must be inherited from the single process which created the pipe." @[About pipes](https://www.gnu.org/software/libc/manual/html_node/Pipes-and-FIFOs.html)

> **Exec Functions**
> 
> "These functions are used to execute a file, and they replace the current process image with a new process image once they are called. 
Even though they are very similar, there are differences between them, and each one of them receives different information as arguments." @[About exec functions](https://www.baeldung.com/linux/exec-functions)
>- execl();
>- execlp();
>- execle();
>- execv();
>- execvp();
>- execve().

> **Tasks**
> 
> 1. A child process will be responsible for identifying new incoming files and signal them.
> 
> 2. One child process will be responsible by each application files management. There will be a fixed number of worker child processes running at the same time. <br>
> *"(...) This shared folder should be organized by job reference (top folders) and then by application (sub folder inside the job reference folder). (...)"* <br>
> *"Each child process will be responsible for copying all files related to a specific candidate to its designated subdirectory in the output directory. (...)"* <br> 
> *"(...) distribute the new files among a fixed number of worker child processes. (...)"*
>  
> 3. A parent process is responsible for managing all the processes. <br>
> *"(...) Upon receiving a signal, the parent process should distribute the new files (...)"* <br>
> *"(...) Child workers do not terminate unless they are specifically terminated by the parent process. (...)"* <br>
>
> 4. The parent process is responsible for producing a text report of the work done. <br>
> *"(...) Once all files for all candidates have been copied, the parent process should generate a report file in the output directory. This report should list, for each candidate, the name of the output subdirectory and the names of all files that were copied. (...)"* <br>
> *"(...) The Bot should produce a text report of all the processed applications (including applications for job references and files available). (...)"*
>
> 5. Application termination managed by parent process, that will coordinate each child process work termination. <br>
> *"(...) To terminate the application, the parent process must handle the SIGINT signal. Upon reception, it should terminate all children and wait for their termination. (...)"*

> **Runtime Configurations**
> Configurations can be set either by parameter or a configuration file. The configurations are: <br>
> Names of the input and output directories; Number of worker children; Time interval for periodic checking of new files.

> **Application files**
> Each application shall hold 5 files, that will have a given prefix unique identifier, per application, a dash and its respective standard name (big-file1; candidate-data; cv; email; letter). <br>
> The "#-candidate-data" file will hold the main data for the present US development.

## 4. Design

### 4.1. Realization

The solution design is highly impacted by the sprint non-functional requirements set by SCOMP teachers.
The team interpretation is expressed through a flow diagram, on the item below.

### 4.2. Flow diagram

![Processes](processes.jpg "A Flow Diagram")

### 4.3. Tests

**Test 1:** *Verifies if a new folder is created only when it does not exist.*

**Refers to Acceptance Criteria:** 2001.1

```
@Test(expected = IllegalArgumentException.class)
public void ensureXxxxYyyy() {
	...
}
```

**Test 2:** *Verifies if a file is moved only to the correspondent job_reference/candidate_id folder.*

**Refers to Acceptance Criteria:** 2001.2

```
@TODO
```

**Test 3:** *Verifies if a set of files with no job reference are kept on the input folder.*

**Refers to Acceptance Criteria:** 2001.3

```
@TODO
```

**Test 4:** *Verifies if a set of files with no candidate e-mail are kept on the input folder.*

**Refers to Acceptance Criteria:** 2001.4

```
@TODO
```

**Test 5:** *Verifies if a special return (not success or fail) is sent by a child process, whenever a set of application files are incomplete.*

**Refers to Acceptance Criteria:** 2001.5

```
@TODO
```

## 5. Implementation

*In this section the team should present, if necessary, some evidencies that the implementation is according to the design. It should also describe and explain other important artifacts necessary to fully understand the implementation like, for instance, configuration files.*

*It is also a best practice to include a listing (with a brief summary) of the major commits regarding this requirement.*

```
@TODO
```

## 6. Integration/Demonstration

*In this section the team should describe the efforts realized in order to integrate this functionality with the other parts/components of the system*

*It is also important to explain any scripts or instructions required to execute an demonstrate this functionality*

```
@TODO
```

## 7. Observations

We note that the solution present won't handle any incomplete application (missing files). <br>
It will be highlighted on the report, but no actions will be taken towards them, within the scope of the present user story.