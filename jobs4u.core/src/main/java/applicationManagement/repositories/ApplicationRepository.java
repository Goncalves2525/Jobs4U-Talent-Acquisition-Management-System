package applicationManagement.repositories;

import applicationManagement.domain.Candidate;
import eapli.framework.domain.repositories.DomainRepository;
import applicationManagement.domain.Application;

import java.util.List;

public interface ApplicationRepository extends DomainRepository<String, Application>{

    List<Application> ofCandidate(Candidate candidate);

    void update(Application entity);

    List<Application> ofJobReference(String jobReference);
  
    String countApplicants(String jobReference);

    boolean defineRanking(Candidate candidate, String jobReference, String rank);

    List<Application> findGradableApplications(String jobReference);

    int saveGrades(List<Application> listOfGradableApplications);

    boolean addInterviewReplyPath(Candidate candidate, String jobReference, String interviewReplyPath);
  
}
