package applicationManagement.domain.dto;

import applicationManagement.domain.Candidate;
import jobOpeningManagement.domain.*;

public class ApplicationDTO {
    private Long id;
    private String jobReference;
    private Candidate candidate;
    private JobOpening jobOpening;


    public ApplicationDTO(String jobReference , Candidate candidate, JobOpening jobOpening) {
        this.jobReference = jobReference;
        this.candidate = candidate;
        this.jobOpening = jobOpening;
    }




    public String jobReference() {
        return jobReference;
    }

    public Candidate candidate() {
        return candidate;
    }

    public JobOpening jobOpening() {
        return jobOpening;
    }


}

