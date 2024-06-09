package jobOpeningManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Entity
public class JobOpening implements AggregateRoot<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String jobReference;

    @Column
    @Setter
    private String title;

    @Enumerated(EnumType.STRING)
    @Column
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    @Column
    private JobMode mode;

    @Embedded
    @Column
    @Setter
    private Address address;

    @ManyToOne
    @JoinColumn(name = "code")
    private Customer company;

    @Column
    @Setter
    private int numberOfVacancies;

    @Column
    private String jobSpecifications;

    @Column
    private String interviewModel;

    @Column
    private String description;

    @Embedded
    @Column
    private Requirements requirements;

    @Enumerated(EnumType.STRING)
    @Column
    @Getter
    @Setter
    private RecruitmentState state;

    @Column
    @Getter
    private Date startDate;

    @Column
    @Getter
    @Setter
    private Date endDate;

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
        this.state = RecruitmentState.APPLICATION;
        this.startDate = Date.from(Instant.now());
        this.endDate = null;
        counter++;
        //Company code + sequential number
        generateJobReference();
    }

    public JobOpening(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String jobSpecifications, String interviewModel, String description, Requirements requirements) {
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
        this.jobSpecifications = jobSpecifications;
        this.interviewModel = interviewModel;
        this.description = description;
        this.requirements = requirements;
        this.state = RecruitmentState.APPLICATION;
        this.startDate = Date.from(Instant.now());
        this.endDate = null;
        counter++;
        //Company code + sequential number
        generateJobReference();
    }

    public JobOpening(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String jobSpecifications, String description, Requirements requirements, RecruitmentState recruitmentState, String jobReference) {
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
        this.jobSpecifications = jobSpecifications;
        this.description = description;
        this.requirements = requirements;
        this.state = recruitmentState;
        this.jobReference = jobReference;
        this.startDate = Date.from(Instant.now());
        this.endDate = null;
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

    public String jobSpecifications() {
        return jobSpecifications;
    }

    public String interviewModel() {
        return interviewModel;
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
        return jobReference + " : " + title + ", by " + company + " | STATE: " + state;
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

    public boolean checkIfJobOpeningHasJobRequirementSpecification() {
        return jobSpecifications != null;
    }

    public boolean checkIfJobOpeningHasInterviewModel() {
        return interviewModel != null;
    }

    public boolean associateJobRequirementSpecificationToJobOpening(String allJobRequirementSpecification) {
        if(!checkIfJobOpeningHasJobRequirementSpecification()){
            this.jobSpecifications = allJobRequirementSpecification;
            return true;
        }
        return false;
    }

    public boolean associateInterviewModelToJobOpening(String interviewModel) {
        if(!checkIfJobOpeningHasInterviewModel()){
            this.interviewModel = interviewModel;
            return true;
        }
        return false;
    }
}
