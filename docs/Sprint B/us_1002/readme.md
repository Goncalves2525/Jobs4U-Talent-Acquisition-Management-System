# US 1002

## 1. Context

*Customers from different companies will send Job offers to Customer Managers of Jobs4U.
The way these Job Offers are sent is out of the scope of the System.
Each Customer Manager should be able to register a Job Opening based on the Job Offer sent.*

## 2. Requirements

**US 1002** As Customer Manager, I want to register a job opening.

**Acceptance Criteria:**

- 1002.1. It shouldn't be possible to register a job opening that is already registered.

- 1002.2. The first state of the job opening should be "APPLICATION".

- 1002.3. The job opening should have the following attributes:
  - Job Reference
  - Title
  - Contract Type
  - Mode
  - Address
  - Company
  - Number of vacancies
  - Description
  - Requirements
  - State

- 1002.4. Job Reference should be based on a Customer code followed by a sequential number (with 10 characters max).

- 1002.5. "Number of vacancies" should be a positive integer.

- 1002.6. "If there are no Customers registered, it shouldn't be possible to register a Job Opening."

**Dependencies/References:**

*Regarding this requirement we understand that it relates to US1008 because the Customer Manager must select one of the requirements specification that was previously loaded by the Language Engineer.*
*It is only possible to register a JobOpening of Customers have been registered (US1001)*

## 3. Analysis
### 3.1. Relevant Domain Model Excerpt
![Domain Model](domain_model.png)

### 3.2. Questions and Answers
> **Question:** No contexto em que o Customer Manager regista uma oferta de emprego, como são selecionados/definidos os requisitos para essa job offer?
> 
> **Answer:** O Customer manager regista a job opening (US 1002) e de seguida (normalmente) seleciona qual o requirements specification que é adequado a esse job opening. O requirements specification será um dos que foi “criado” pelo language engineer e registado no sistema.

> **Question:** No job opening é tudo de preenchimento obrigatório ou existem opcionais?
> 
> **Answer:** Os campos referidos na secção 2.2.2 são de preenchimento obrigatório. Os requirements vão ser dinâmicos uma vez que dependem do requirements specification selecionado para aquele job opening (que se baseia numa linguagem).

> **Question:** Sobre a job specification, deve ser o cliente a enviar os requisitos ou é a responsabilidade do customer manager? Qual o conceito de uma job specification?
>
> **Answer:** Tipicamente será o customer que informa o custerm manager dos requisitos mínimos para uma oferta de emprego. O Customer manager verifica se existe já um requirements specification adequado. Caso não existe, com a ajuda do Language Engineer é criado um novo.

> **Question:** Sobre a job specification, deve ser o cliente a enviar os requisitos ou é a responsabilidade do customer manager? Qual o conceito de uma job specification?
>
> **Answer:** Tipicamente será o customer que informa o custerm manager dos requisitos mínimos para uma oferta de emprego. O Customer manager verifica se existe já um requirements specification adequado. Caso não existe, com a ajuda do Language Engineer é criado um novo.


### 3.3. Other Remarks
After analysing more deeply the Specification Document and asking some questions to the client, we made the following adjustments to the domain model:
* Customer is a part of the same Aggregate as the Job Opening.
* Job Opening has a value object called state.
* Job Opening is connected to the Requirement Specification created by the Language Engineer.

## 4. Design

### 4.1. Realization

| Interaction ID                                                                            | Question: Which class is responsible for...                                         | Answer                       | Justification (with patterns)            |
|:------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------|:-----------------------------|:-----------------------------------------|
| Step 1 : Customer Manager requests to register a Job Opening                              | 	... requesting Job Opening Info?                                                   | RegisterJobOpeningUI         | Pure Fabrication                         |
| 		                                                                                        | 	... validating Customer Managers inputs?                                           | JobOpening                   | Information Expert                       |
| 		                                                                                        | 	... retrieving available companies?                                                | ListCustomersService         | Service                                  |
| Step 2 : System registers Job Opening                                                     | 	... coordination between users request and saving the Job Opening in the Database? | RegisterJobOpeningController | Controller                               |
|                                                                                           | 	... creating the Job Opening?                                                      | JobOpening                   | Creater                                  |
|                                                                                           | 	... persisting the Job Opening?                                                    | JobOpeningRepository         | Information Expert,<br/>Pure Fabrication |
| Step 3 : System Informs the Customer Manager of Success/insuccess of the operation			  		 | 	... Showing result?                                                                | RegisterJobOpeningUI         | Pure Fabrication                         |

According to the taken rationale, the conceptual classes promoted to software classes are:

* Job Opening

Other software classes (i.e. Pure Fabrication) identified:

* RegisterJobOpeningUI
* RegisterJobOpeningController
* JobOpeningRepository
* ListCustomersService


### 4.2. Class Diagram

![a class diagram](class-diagram-01.svg "A Class Diagram")

### 4.3. Sequence Diagram

![a sequence diagram](sequence-diagram.svg "A Sequence Diagram")

### 4.4. Tests

**Test 1:** *Verifies that it is not possible to register a job opening that is already registered*

**Refers to Acceptance Criteria:** G002.1


```java
@Test
    void ensureJobOpeningIsUnique(){
        String title = "Software Engineer";
        ContractType contractType = ContractType.FULL_TIME;
        JobMode mode = JobMode.REMOTE;
        Address address = new Address("Rua do Ouro", "Porto", "4000-000");
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", "000@isep.ipp.pt", new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
        int numberOfVacancies = 5;
        String description = "Software Engineer to work on a new project";
        JobOpening jobOpening2 = new JobOpening(title, contractType, mode, address, company, numberOfVacancies, description, null);

        String id = jobOpening.identity();
        String id2 = jobOpening2.identity();

        assert(!id.equals(id2));
    }
````

**Test 2:** *Verifies that the first state of the job opening is "APPLICATION"*

**Refers to Acceptance Criteria:** G002.2

```java
    @Test
    void ensureJobOpeningHasCorrectState(){
        assert(jobOpening.state() == RecruitmentState.APPLICATION);
    }
````

**Test 3:** *Verifies that the Job Reference does not exceed maximum characters*

**Refers to Acceptance Criteria:** G002.4

```java
    @Test
    void ensureJobReferenceDoesNotExceedMaxChars(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CompanyCode("12345678900000"));
    }
````

**Test 4:** *Verifies that the Job Reference uses sequential numbers*

**Refers to Acceptance Criteria:** G002.4
```java
    @Test
    void ensureJobReferenceUsesSequentialNumbers(){
        JobOpening jobOpening2 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), jobOpening.company(), 1, "test", null);
        JobOpening jobOpening3 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), jobOpening.company(), 1, "test", null);
        String id = jobOpening.identity();
        String[] parts = id.split("-");
        int number = Integer.parseInt(parts[1]);
        String id2 = jobOpening2.identity();
        String[] parts2 = id2.split("-");
        int number2 = Integer.parseInt(parts2[1]);
        String id3 = jobOpening3.identity();
        String[] parts3 = id3.split("-");
        int number3 = Integer.parseInt(parts3[1]);

        assert(number2 == (number + 1));
        assert(number3 == (number2 + 1));
    }
```

**Test 5:** *Verifies that the Number of vacancies is a positive integer*

**Refers to Acceptance Criteria:** G002.5

```java
    @Test
    void ensureNumberOfVacanciesIsPositive(){
        //Success
        assert(jobOpening.numberOfVacancies() > 0);

        //Failure
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", "000@isep.ipp.pt", new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, -1, "test", null));
    }
````


## 5. Implementation
**JobOpening**
    
```java
    package jobOpeningManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;

@Entity
public class JobOpening implements AggregateRoot<String> {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column
  private String jobReference;

  @Column
  private String title;

  @Enumerated(EnumType.STRING)
  @Column
  private ContractType contractType;

  @Enumerated(EnumType.STRING)
  @Column
  private JobMode mode;

  @Embedded
  @Column
  private Address address;

  @ManyToOne
  @JoinColumn(name = "code")
  private Customer company;

  @Column
  private int numberOfVacancies;

  @Column
  private String description;

  @Embedded
  @Column
  private Requirements requirements;

  @Enumerated(EnumType.STRING)
  @Column
  private RecruitmentState state;

  @Transient
  private static int counter = 0;



  protected JobOpening() {
    // for ORM
  }

  public JobOpening(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, Requirements requirements) {
    this.title = title;
    this.contractType = contractType;
    this.mode = mode;
    this.address = address;
    this.company = company;
    //ensure that the number of vacancies is a positive number
    if (numberOfVacancies <= 0) {
      throw new IllegalArgumentException("Number of vacancies must be a positive number");
    }
    this.numberOfVacancies = numberOfVacancies;
    this.description = description;
    this.requirements = requirements;
    state = RecruitmentState.APPLICATION;
    counter++;
    //Company code + sequential number
    generateJobReference();
  }

  public String jobReference() {
    return jobReference;
  }

  public String title() {
    return title;
  }

  public ContractType contractType() {
    return contractType;
  }

  public JobMode mode() {
    return mode;
  }

  public Address address() {
    return address;
  }

  public Customer company() {
    return company;
  }

  public int numberOfVacancies() {
    return numberOfVacancies;
  }

  public String description() {
    return description;
  }

  public Requirements requirements() {
    return requirements;
  }

  public RecruitmentState state() {
    return state;
  }

  @Override
  public String toString() {
    return "JobOpening{" +
            "jobReference='" + jobReference + '\'' +
            ", title='" + title + '\'' +
            ", contractType='" + contractType + '\'' +
            ", mode='" + mode + '\'' +
            ", address='" + address + '\'' +
            ", company='" + company + '\'' +
            ", numberOfVacancies='" + numberOfVacancies + '\'' +
            ", description='" + description + '\'' +
            ", requirements='" + requirements + '\'' +
            ", state='" + state + '\'' +
            '}';
  }

  @PrePersist
  public void generateJobReference() {
    String companyCode = company.getCode().getCode();
    jobReference = companyCode + "-" + counter;
  }

  @Override
  public boolean sameAs(Object other) {
    JobOpening jobOpening = (JobOpening) other;
    return jobReference.equals(jobOpening.jobReference);
  }

  @Override
  public String identity() {
    return jobReference;
  }
}

```

**RegisterJobOpeningUI**

```java
package presentation.CustomerManager;

import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.RegisterJobOpeningController;
import jobOpeningManagement.domain.*;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import utils.Utils;
import java.util.Iterator;


public class RegisterJobOpeningUI extends AbstractUI{

    RegisterJobOpeningController ctrl = new RegisterJobOpeningController();

    @Override
    protected boolean doShow() {
        int option = 0;
        String title;
        do{
            title = Utils.readLineFromConsole("Title: ");
        }while(title.equals(""));
        ContractType contractType = selectContractType();
        JobMode mode = selectJobMode();
        System.out.println("-ADDRESS-");
        String street, city, postalCode;
        do{
            street = Utils.readLineFromConsole(" Street: ");
            city = Utils.readLineFromConsole(" City: ");
            postalCode = Utils.readLineFromConsole(" Postal Code: ");
        }while(street.equals("") || city.equals("") || postalCode.equals(""));
        Address address = new Address(street, city, postalCode);
        Customer company = selectCompany();
        if(company == null){
            return false;
        }
        int numberOfVacancies = Utils.readIntegerFromConsole("Number of Vacancies: ");
        String description = Utils.readLineFromConsole("Description: ");
        Requirements requirements = null;
        RecruitmentState state = RecruitmentState.APPLICATION;



        printJobOpenings(title, contractType, mode, address, company, numberOfVacancies, description, state);
        System.out.println("1 - Confirm");
        System.out.println("0 - Exit");
        option = Utils.readIntegerFromConsole("Option: ");
        if(option == 0){
            return false;
        }



        JobOpeningDTO jobOpeningDTO = new JobOpeningDTO(title, contractType, mode, address, company, numberOfVacancies, description, requirements, state);
        boolean success = ctrl.registerJobOpening(jobOpeningDTO);
        if (success){
            System.out.println("Job Opening registered successfully");
            return true;
        }else{
            System.out.println("Error registering Job Opening");
            return false;
        }
    }

    @Override
    public String headline() {
        return "JOB OPENING REGISTRATION";
    }

    private ContractType selectContractType(){
        int i = 1;
        System.out.println("CONTRACT TYPES");
        for (ContractType contractType : ContractType.values()) {
            System.out.println(i + " - " + contractType);
            i++;
        }
        int option = Utils.readIntegerFromConsole("Select Contract Type: ");
        return ContractType.values()[option - 1];
    }

    private JobMode selectJobMode(){
        int i = 1;
        System.out.println("JOB MODES");
        for (JobMode jobMode : JobMode.values()) {
            System.out.println(i + " - " + jobMode);
            i++;
        }
        int option = Utils.readIntegerFromConsole("Select Job Mode: ");
        return JobMode.values()[option - 1];
    }

    private Customer selectCompany(){
        Iterable<Customer> customers = ctrl.allCustomers();
        if(customers == null){
            System.out.println("No companies registered");
            return null;
        }
        int i = 1;
        System.out.println("COMPANIES");
        for (Customer customer : customers) {
            System.out.println(i + " - " + customer.getCode());
        }
        int option = Utils.readIntegerFromConsole("Select Company: ");
        Iterator<Customer> iterator = customers.iterator();
        for (int j = 0; j < option - 1; j++) {
            iterator.next();
        }
        return iterator.next();
    }

    private RecruitmentState selectRecruitmentState(){
        int i = 1;
        System.out.println("RECRUITMENT STATES");
        for (RecruitmentState recruitmentState : RecruitmentState.values()) {
            System.out.println(i + " - " + recruitmentState);
            i++;
        }
        int option = Utils.readIntegerFromConsole("Select Recruitment State: ");
        return RecruitmentState.values()[option - 1];
    }

    private void printJobOpenings(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, RecruitmentState state){
        System.out.println("Title: " + title);
        System.out.println("Contract Type: " + contractType);
        System.out.println("Mode: " + mode);
        System.out.println(address);
        System.out.println("Company: " + company.getCode());
        System.out.println("Number of Vacancies: " + numberOfVacancies);
        System.out.println("Description: " + description);
        System.out.println("State: " + state);
        System.out.println();
    }
}
    
```
**RegisterJobOpeningController**

```java
package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.Iterator;


public class RegisterJobOpeningController {

    private JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();
    private ListCustomersService svc = new ListCustomersService();

    public boolean registerJobOpening(JobOpeningDTO dto) {
        JobOpening jobOpening = new JobOpening(dto.title(), dto.contractType(), dto.mode(), dto.address(), dto.company(), dto.numberOfVacancies(), dto.description(), dto.requirements());
        jobOpening = repo.save(jobOpening);
        return jobOpening != null;
    }

    public Iterable<Customer> allCustomers() {
        return svc.allCustomers();
    }
}
```

**JpaJobOpeningRepository**

```java
package jpa;

import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalRepository;
import jakarta.persistence.*;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.List;
import java.util.Optional;

import static org.eclipse.persistence.jpa.JpaHelper.getEntityManager;

public class JpaJobOpeningRepository implements JobOpeningRepository {
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.
                createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        return manager;
    }


    public JobOpening add(JobOpening jobOpening) {
        if (jobOpening == null) {
            throw new IllegalArgumentException();
        }
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(jobOpening);
        tx.commit();
        em.close();

        return jobOpening;
    }

    @Override
    public <S extends JobOpening> S save(S entity) {
        if(correctJobOpening(entity)){
            EntityManager em = getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(entity);
            tx.commit();
            em.close();

            return entity;
        }
        return null;
    }

    private boolean correctJobOpening(JobOpening jobOpening) {
        boolean correct = true;
        if (jobOpening.title() == null || jobOpening.title().isEmpty()) {
            correct = false;
        }
        if (jobOpening.contractType() == null) {
            correct = false;
        }
        if (jobOpening.mode() == null) {
            correct = false;
        }
        if (jobOpening.address() == null) {
            correct = false;
        }
        if (jobOpening.company() == null) {
            correct = false;
        }
        if (jobOpening.numberOfVacancies() <= 0) {
            correct = false;
        }
        if (jobOpening.description() == null || jobOpening.description().isEmpty()) {
            correct = false;
        }
        //We are letting "requirements" be null because Customer Manager can choose later
        if (jobOpening.state() == null || jobOpening.state() != RecruitmentState.APPLICATION) {
            correct = false;
        }
        return correct;
    }

    public List<JobOpening> findAll() {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e");
        List<JobOpening> list = query.getResultList();
        return list;
    }

    @Override
    public Optional<JobOpening> ofIdentity(String id) {
        Query query = getEntityManager().createQuery(
                "SELECT e FROM JobOpening e WHERE e.id = :id");
        query.setParameter("id", id);
        JobOpening jobOpening = (JobOpening) query.getSingleResult();
        return Optional.of(jobOpening);
    }

    @Override
    public void delete(JobOpening entity) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(entity);
        tx.commit();
        em.close();
    }

    @Override
    public void deleteOfIdentity(String jobReference) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createQuery("DELETE FROM JobOpening e WHERE e.jobReference = :jobReference");
        query.setParameter("jobReference", jobReference);
        query.executeUpdate();
        tx.commit();
        em.close();

    }

    @Override
    public long count() {
        Query query = getEntityManager().createQuery(
                "SELECT COUNT(e) FROM JobOpening e");
        return (long) query.getSingleResult();
    }


}
```

## 6. Integration/Demonstration

n/a

## 7. Observations

n/a