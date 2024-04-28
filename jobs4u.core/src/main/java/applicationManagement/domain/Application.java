package applicationManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import jobOpeningManagement.domain.JobOpening;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"jobReference", "email"}))
public class Application implements AggregateRoot<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String jobReference;

    @ManyToOne
    @JoinColumn(name = "email")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "jobOpening")
    private JobOpening jobOpening;

    @Column
    private String status;

    @Column
    private LocalDate date;

    @Column(columnDefinition = "VARBINARY")
    private Object InterviewModel = null; //"" by omission


    protected Application() {
        // for ORM
    }

    public Application(String jobReference, Candidate candidate, JobOpening jobOpening) {
        this.jobReference = jobReference;
        this.candidate = candidate;
        this.jobOpening = jobOpening;
        this.date = LocalDate.now();
    }

//    public Application(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, Requirements requirements) {
//        this.title = title;
//        this.contractType = contractType;
//        this.mode = mode;
//        this.address = address;
//        this.company = company;
//        //ensure that the number of vacancies is a positive number
//        if (numberOfVacancies <= 0) {
//            throw new IllegalArgumentException("Number of vacancies must be a positive number");
//        }
//        this.numberOfVacancies = numberOfVacancies;
//        this.description = description;
//        this.requirements = requirements;
//        state = RecruitmentState.APPLICATION;
//        counter++;
//        //Company code + sequential number
//        generateJobReference();
//    }
//
//    public String jobReference() {
//        return jobReference;
//    }
//
//    public String title() {
//        return title;
//    }
//
//    public ContractType contractType() {
//        return contractType;
//    }
//
//    public JobMode mode() {
//        return mode;
//    }
//
//    public Address address() {
//        return address;
//    }
//
//    public Customer company() {
//        return company;
//    }
//
//    public int numberOfVacancies() {
//        return numberOfVacancies;
//    }
//
//    public String description() {
//        return description;
//    }
//
//    public Requirements requirements() {
//        return requirements;
//    }
//
//    public RecruitmentState state() {
//        return state;
//    }
//
//    @Override
//    public String toString() {
//        return "JobOpening{" +
//                "jobReference='" + jobReference + '\'' +
//                ", title='" + title + '\'' +
//                ", contractType='" + contractType + '\'' +
//                ", mode='" + mode + '\'' +
//                ", address='" + address + '\'' +
//                ", company='" + company + '\'' +
//                ", numberOfVacancies='" + numberOfVacancies + '\'' +
//                ", description='" + description + '\'' +
//                ", requirements='" + requirements + '\'' +
//                ", state='" + state + '\'' +
//                '}';
//    }
//
//    @PrePersist
//    public void generateJobReference() {
//        String companyCode = company.getCode().getCode();
//        jobReference = companyCode + "-" + counter;
//    }

    public boolean checkIfApplicationHasInterviewModel() {
        return InterviewModel != null;
    }

    public boolean associateInterviewModelToApplication(Object interviewModel) {
        if (!checkIfApplicationHasInterviewModel()) {
            this.InterviewModel = interviewModel;
            return true;
        }
        return false;
    }

    @Override
    public boolean sameAs(Object other) {
        Application jobOpening = (Application) other;
        return jobReference.equals(jobOpening.jobReference);
    }

    @Override
    public String identity() {
        return jobReference;
    }
}
