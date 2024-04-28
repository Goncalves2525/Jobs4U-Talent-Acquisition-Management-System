//package applicationManagement.domain;
//
//import eapli.framework.domain.model.AggregateRoot;
//import jakarta.persistence.*;
//import jobOpeningManagement.domain.JobOpening;
//import lombok.Getter;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.util.Date;
//
//@Getter
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"jobReference", "email"}))
//public class ApplicationFiles implements AggregateRoot<String> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Application applicationID;
//
//    @Column
//    private String fileName;
//
//    @Column
//    private String filePath;
//
//    protected ApplicationFiles() {
//        // for ORM
//    }
//
////    public ApplicationFiles(String jobReference, Candidate candidate, JobOpening jobOpening, ApplicationStatus status, Date applicationDate, String comment
////    , Serializable interviewModel) {
////        this.jobReference = jobReference;
////        this.candidate = candidate;
////        this.jobOpening = jobOpening;
////        this.status = status;
////        this.applicationDate = applicationDate;
////        this.comment = comment;
////        this.InterviewModel = interviewModel;
////        this.date = LocalDate.now();
////    }
////
////    public String jobReference() {
////        return jobReference;
////    }
////
////    public Candidate candidate() {
////        return candidate;
////    }
////
////    public JobOpening jobOpening() {
////        return jobOpening;
////    }
////
////    public ApplicationStatus status() {
////        return status;
////    }
////
////    public Date applicationDate() {
////        return applicationDate;
////    }
////
////    public String comment() {
////        return comment;
////    }
////
////    public Serializable interviewModel() {
////        return InterviewModel;
////    }
////
////    @Override
////    public String toString() {
////        return "Application{" +
////                "jobReference='" + jobReference + '\'' +
////                ", candidate=" + candidate +
////                ", jobOpening=" + jobOpening +
////                ", status=" + status +
////                ", InterviewModel=" + InterviewModel +
////                ", comment='" + comment + '\'' +
////                ", applicationDate=" + applicationDate +
////                '}';
////    }
////
////    public boolean checkIfApplicationHasInterviewModel() {
////        return InterviewModel != null;
////    }
////
////    public boolean associateInterviewModelToApplication(Object interviewModel){
////        if(!checkIfApplicationHasInterviewModel()){
////            this.InterviewModel = (Serializable) interviewModel;
////            return true;
////        }
////        return false;
////    }
////
////    @Override
////    public boolean sameAs(Object other) {
////        ApplicationFiles application = (ApplicationFiles) other;
////        return jobReference.equals(application.identity());
////    }
////
////    @Override
////    public String identity() {
////        return id.toString();
////    }
//}
