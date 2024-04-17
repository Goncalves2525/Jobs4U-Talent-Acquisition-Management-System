package jobOpeningManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JobOpening")
public class JobOpening implements AggregateRoot<String> {
    @Id
    private String jobReference;

    @Column
    private String title;

    @Column
    private String contractType;

    @Column
    private String mode;

    @Column
    private String address;

    @Column
    private String company;

    @Column
    private String numberOfVacancies;

    @Column
    private String description;

    @Column
    private String requirements;

    @Column
    private String state;

    protected JobOpening() {
        // for ORM
    }

    public JobOpening(String jobReference){
        this.jobReference = jobReference;
    }

    public JobOpening(String jobReference, String title, String contractType, String mode, String address, String company, String numberOfVacancies, String description, String requirements, String state) {
        this.jobReference = jobReference;
        this.title = title;
        this.contractType = contractType;
        this.mode = mode;
        this.address = address;
        this.company = company;
        this.numberOfVacancies = numberOfVacancies;
        this.description = description;
        this.requirements = requirements;
        this.state = state;
    }

    public String getJobReference() {
        return jobReference;
    }

    public String getTitle() {
        return title;
    }

    public String getContractType() {
        return contractType;
    }

    public String getMode() {
        return mode;
    }

    public String getAddress() {
        return address;
    }

    public String getCompany() {
        return company;
    }

    public String getNumberOfVacancies() {
        return numberOfVacancies;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public String getState() {
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
