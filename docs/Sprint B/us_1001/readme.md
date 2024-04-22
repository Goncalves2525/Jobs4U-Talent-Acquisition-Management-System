# US 1001

## 1. Context

*Customers from different companies will send Job offers to Customer Managers of Jobs4U.
The way these Job Offers are sent is out of the scope of the System.
Each Customer Manager should be able to register a Customer in the system that automatically creates a user for that customer.*

## 2. Requirements

**US 1001** As Customer Manager I want to register a customer and that the system automatically creates a user for that customer.

**Acceptance Criteria:**

- 1001.1. The Customer Manager must provide the following information: Company Code, Company Name, Company Email, Company Address.

- 1001.2. The Company Code must be 10 characters long at most.

- 1001.3. The Company Code must be unique.


**Dependencies/References:**

*Regarding this requirement we understand that it relates to US1002. JobOpening must be associated to a Customer.*


## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](domain_model.png)

### 3.2. Questions and Answers
> **Question:** No enunciado não está explicita a informação a recolher para os Customers? Qual a informação necessária? E quando aos funcionários da empresa?
> 
> **Answer:** De facto isso não está explicito. No entanto, são referidos no nome da empresa e o seu endereço no âmbito de um job opening. Quanto aos utilizadores (representante da empresa que acede à Customer App) eu diria que serão dados similares ao do Candidate. Quando aos funcionários da empresa, eu diria que é importante garantir que é usado o email para identificar qualquer utilizador do sistema. Penso que será importante para cada utilizador termos o nome completo assim como um short user name (que deverá ser único). Actualização em 2024-03-21: O Product Owner reconsiderou e decidiu que o short user name é dispensável uma vez que para autenticação dos utilizadores se deve usar apenas o email e a password.

> **Question:** Customer tem que ter morada e nome da empresa ou se basta essa informação estar no job opening?
>
> **Answer:** Devemos registar nome e morada do customer. Para cada job opening a morada pode ser especifica (e diferente da morada do customer).



### 3.3. Other Remarks
After analysing more deeply the Specification Document and asking some questions to the client, we made the following adjustments to the domain model:
* Customer is a part of the same Aggregate as the Job Opening.
* Customer has a Company Code and an Email(for the user).

## 4. Design

### 4.1. Realization

| Interaction ID                                                                            | Question: Which class is responsible for...                                         | Answer                     | Justification (with patterns) |
|:------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------|:---------------------------|:------------------------------|
| Step 1 : Customer Manager requests to register a Customer                                 | 	... requesting Customer Info?                                                      | RegisterCustomerUI         | Pure Fabrication              |
| 		                                                                                        | 	... validating Customer Managers inputs?                                           | Customer                   | Information Expert            |
| Step 2 : System registers Customer                                                        | 	... coordination between users request and saving the Job Opening in the Database? | RegisterCustomerController | Controller                    |
|                                                                                           | 	... creating the Customer?                                                         | Customer                   | Creater                       |
|                                                                                           | 	... persisting the Job Opening?                                                    | CustomerRepository         | Information Expert            |
| Step 3 : System automatically creates a user                                              | 	... creating a user?                                                               | SignUpController           | Controller                    |
|                                                                                           | 	... persisting ther user?                                                          | UserRepository             | Information Expert            |
| Step 4 : System informs the Customer Manager of Success/insuccess of the operation			  		 | 	... Showing result?                                                                | RegisterCustomerUI         | Pure Fabrication              |

According to the taken rationale, the conceptual classes promoted to software classes are:

* Customer

Other software classes (i.e. Pure Fabrication) identified:

* RegisterCustomerUI
* RegisterCustomerController
* CustomerRepository
* SignUpController
* UserRepository


### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### 4.3. Sequence Diagram

![a sequence diagram](sequence-diagram.svg "A Sequence Diagram")

### 4.4. Tests

**Test 1:** *Verifies that the Customer has the folling information: Company Code, Company Name, Company Email, Company Address.*

**Refers to Acceptance Criteria:** G001.1


```java
    @Test
    void ensureCustomerHasFullInformation() {

    }

````

**Test 2:** *Verifies that the Company Code has a maximum of 10 caracteres*

**Refers to Acceptance Criteria:** G001.2

```java
    @Test
    void ensureCompanyCodeHasMaxLength() {

    }

````

**Test 3:** *Verifies that the Customer is unique*

**Refers to Acceptance Criteria:** G001.3

```java
    @Test
    void ensureCustomerIsUnique() {

    }

````



## 5. Implementation
**Customer**
    
```java


```

**RegisterCustomerUI**

```java

```
**RegisterCustomerController**

```java

```

**JpaCustomerRepository**

```java

```

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a