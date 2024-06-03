package applicationManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

import java.time.LocalDate;

@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"jobReference", "email"}))
public class Application implements AggregateRoot<String>, Serializable {
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
    private ApplicationStatus status;

    @Column
    private LocalDate date;

    @Column
    private String InterviewModel;

    @Column
    private String comment;

    @Column
    private Date applicationDate;

    @Column
    private String filePath;

    @Column
    private String applicationFilesPath;

    @Column
    private Date interviewDate;


    protected Application() {
        // for ORM
    }

    public Application(String jobReference, Candidate candidate, JobOpening jobOpening, ApplicationStatus status, Date applicationDate, String comment
    , String interviewModel, String filePath, String applicationFilesPath) {
        this.jobReference = jobReference;
        this.candidate = candidate;
        this.jobOpening = jobOpening;
        this.status = status;
        this.applicationDate = applicationDate;
        this.comment = comment;
        this.InterviewModel = interviewModel;
        this.date = LocalDate.now();
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

    public ApplicationStatus status() {
        return status;
    }

    public Date applicationDate() {
        return applicationDate;
    }

    public String comment() {
        return comment;
    }

    public String interviewModel() {
        return InterviewModel;
    }

    public String filePath() {
        return filePath;
    }

    public String applicationFilesPath() {
        return applicationFilesPath;
    }

    public Date interviewDate() {
        return interviewDate;
    }

    @Override
    public String toString() {
        return "Application{" +
                "Job Reference='" + jobReference + '\'' +
                ", Candidate =" + candidate +
                ", Job Opening=" + jobOpening +
                ", Status=" + status +
                //", InterviewModel=" + InterviewModel +
                ", Commend='" + comment + '\'' +
                ", Application Date=" + applicationDate +
                ", Interview Model Path='" + filePath + '\'' +
                ", Application Files Path='" + applicationFilesPath + '\'' +
                '}';
    }

    public boolean checkIfApplicationHasInterviewModel() {
        return InterviewModel != null;
    }

    public boolean checkIfApplicationHasInterviewDate() {
        return interviewDate != null;
    }


    public boolean associateInterviewModelToApplication(String interviewModel) {
        this.InterviewModel = interviewModel;
        return true;
    }

    public boolean registerInterviewDateToApplication(Date interviewDate) {
        this.interviewDate = interviewDate;
        return true;
    }


    @Override
    public boolean sameAs(Object other) {
        Application application = (Application) other;
        return jobReference.equals(application.identity());
    }

    @Override
    public String identity() {
        return id.toString();
    }

    public void changeStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void changeJobOpeningRecruitmentState(RecruitmentState newState) {
            jobOpening.setState(newState);
    }
}
