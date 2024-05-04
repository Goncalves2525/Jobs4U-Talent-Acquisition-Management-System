package applicationManagement.domain.dto;

import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.Candidate;
import jobOpeningManagement.domain.*;

import java.io.Serializable;
import java.util.Date;

public class ApplicationDTO {
    private Long id;
    private String jobReference;
    private Candidate candidate;
    private JobOpening jobOpening;
    private String comment;
    private Date applicationDate;
    private String InterviewModel;
    private ApplicationStatus status;
    private String filePath;
    private String applicationFilesPath;


    public ApplicationDTO(String jobReference , Candidate candidate, JobOpening jobOpening, String comment, Date applicationDate, String interviewModel, ApplicationStatus status
    , String filePath, String applicationFilesPath) {
        this.jobReference = jobReference;
        this.candidate = candidate;
        this.jobOpening = jobOpening;
        this.comment = comment;
        this.applicationDate = applicationDate;
        this.InterviewModel = interviewModel;
        this.status = status;
        this.filePath = filePath;
        this.applicationFilesPath = applicationFilesPath;
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

    public String comment() {
        return comment;
    }

    public Date applicationDate() {
        return applicationDate;
    }

    public String interviewModel() {
        return InterviewModel;
    }

    public ApplicationStatus status() {
        return status;
    }

    public String filePath() {
        return filePath;
    }

    public String applicationFilesPath() {
        return applicationFilesPath;
    }

    public Long id() {
        return id;
    }
}

