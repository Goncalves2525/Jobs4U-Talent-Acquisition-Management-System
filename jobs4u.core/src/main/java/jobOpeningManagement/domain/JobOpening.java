package jobOpeningManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;

@Entity
public class JobOpening implements AggregateRoot<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    protected JobOpening() {
        // for ORM
    }

    public JobOpening(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, Requirements requirements, RecruitmentState state) {
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
        this.state = state;

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
        jobReference = companyCode + "-" + id;
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
