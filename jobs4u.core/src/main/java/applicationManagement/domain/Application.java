package applicationManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Date;

import java.time.LocalDate;
import java.util.Optional;

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
    private String interviewReplyPath;

    @Column
    private int interviewGrade;

    @Column
    private String comment;

    @Column
    private Date applicationDate;

    @Column
    private String filePath;

    @Column
    private String applicationFilesPath;

    @Column
    @Enumerated(EnumType.STRING)
    private RequirementsResult requirementsResult;

    private Ranking rankNumber;

    @Column
    private Date interviewDate;


    protected Application() {
        // for ORM
    }

    public Application(String jobReference, Candidate candidate, JobOpening jobOpening, ApplicationStatus status, Date applicationDate, String comment
    , String filePath, String applicationFilesPath, RequirementsResult requirementsResult) {
        this.jobReference = jobReference;
        this.candidate = candidate;
        this.jobOpening = jobOpening;
        this.status = status;
        this.applicationDate = applicationDate;
        this.comment = comment;
        this.interviewGrade = -101; // impossible result
        this.date = LocalDate.now();
        this.filePath = filePath;
        this.applicationFilesPath = applicationFilesPath;
        this.requirementsResult = requirementsResult;
        this.rankNumber = new Ranking();
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

    public String filePath() {
        return filePath;
    }

    public String applicationFilesPath() {
        return applicationFilesPath;
    }

    public RequirementsResult requirementsResult() {
        return requirementsResult;
    }

    public Ranking rankNumber() {return rankNumber; }

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
                ", Commend='" + comment + '\'' +
                ", Application Date=" + applicationDate +
                ", Interview Model Path='" + filePath + '\'' +
                ", Application Files Path='" + applicationFilesPath + '\'' +
                ", Requirements Result='" + requirementsResult + '\'' +
                '}';
    }

    // TODO: rever uso deste bloco e criar novo método
//    @Override
//    public String toString() {
//        return "Application ID: " + this.getId()
//                + " | Candidate Name: " + this.getCandidate()
//                + " | Application Status: " + this.getStatus()
//                + " | Application Rank: " + this.getRankNumber().getOrdinal();
//
//    }

    public boolean checkIfApplicationHasInterviewDate() {
        return interviewDate != null;
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

    public void assotiateInterviewModelToApplication(int passed, String interviewModelPath) {
        if(passed == 1){
            this.InterviewModel = interviewModelPath;
        }else{
            this.InterviewModel = this.InterviewModel;
        }
    }
    public void assotiateRequirementResultToApplication(int passed){
        if(passed == 1){
            this.requirementsResult = RequirementsResult.APPROVED;
        }else{
            this.requirementsResult = RequirementsResult.REJECTED;
        }
    }

    public void changeRankingNumber(String rank) { rankNumber.setRank(rank); }

    public boolean addInterviewFilePath(String path) {
        if (this.interviewReplyPath == null){
            this.interviewReplyPath = path;
            return true;
        }
        return false;
    }

    public boolean addInterviewGrade() {
        int grade = readGradeFromInterviewFile();
        if(this.interviewGrade == -101 && grade > -101 && grade <= 100){
            this.interviewGrade = grade;
            return true;
        }
        return false;
    }

    private int readGradeFromInterviewFile() {
        int result = -101;
        if(this.interviewReplyPath.isEmpty()) {
            return result;
        }

        try {
            StringBuilder transitContent = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new FileReader(this.interviewReplyPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    transitContent.append(line).append(System.lineSeparator());
                }
            }

            String content = transitContent.toString();
            String[] contentSplit1 = content.split("#RESULT");
            String[] contentSplit2 = contentSplit1[1].split(" with ");
            String[] contentSplit3 = contentSplit2[1].split("/");
            result = Integer.parseInt(contentSplit3[0]);

        } catch (FileNotFoundException e) {
            return result;
        } catch (Exception other) {
            other.printStackTrace(); // TESTING
            return result;
        }

        return result;
    }

}
