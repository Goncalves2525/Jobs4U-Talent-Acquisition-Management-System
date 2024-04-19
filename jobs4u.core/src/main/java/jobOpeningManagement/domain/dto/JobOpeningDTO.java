package jobOpeningManagement.domain.dto;

import jobOpeningManagement.domain.*;

public class JobOpeningDTO {
    private String jobReference;
    private String title;
    private ContractType contractType;
    private JobMode mode;
    private Address address;
    private Customer company;
    private int numberOfVacancies;
    private String description;
    private Requirements requirements;
    private RecruitmentState state;

    public JobOpeningDTO(String jobReference, String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, Requirements requirements, RecruitmentState state) {
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

    //Without jobReference
    public JobOpeningDTO(String title, ContractType contractType, JobMode mode, Address address, Customer company, int numberOfVacancies, String description, Requirements requirements, RecruitmentState state) {
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
}

