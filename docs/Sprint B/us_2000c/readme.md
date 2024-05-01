# US 2000c - List all Candidates

## 1. Context

*Operators of Jobs4U will be able to list candidates.*

## 2. Requirements

**US 2002** As Operator, I want to list all Candidates.

**Acceptance Criteria:**

- 2000c.1. It should be possible to list all existing candidates.
- 2000c.2. The listing should contain the Name and Email of each candidate.

**Dependencies/References:**

**
**

## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](domain_model.png)

### 3.2. Questions and Answers

> Q58 - "US 2000c, quando estiver a listar os candidatos basta listar os emails?" <br/>
> A58 - "Eu diria que faz sentido apresentar para cada candidato o seu email e o seu nome."


### 3.3. Other Remarks

* N/A

## 4. Design

### 4.1. Realization
According to the taken rationale, the conceptual classes promoted to software classes are:

- **Candidate** - Represents a candidate.

Other software classes (i.e. Pure Fabrication) identified:

* CandidateController
* 

### 4.2. Sequence Diagram

![Sequence Diagram](sequence-diagram.svg)

### 4.3. Tests

**Test 1:** **

**Refers to Acceptance Criteria:**


```java
@Test
    void TODOtest(){
        
    
    }
````



## 5. Implementation
**Application**
    
```java
    

```

**RegisterApplicationUI**

```java

```
**RegisterApplicationController**

```java

```

**JpaApplicationRepository**

```java

```

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a