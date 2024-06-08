package applicationManagement.repositories;

import applicationManagement.domain.Candidate;
import eapli.framework.domain.repositories.DomainRepository;
import applicationManagement.domain.Application;

import java.util.List;

public interface ApplicationRepository extends DomainRepository<String, Application>{

    List<Application> ofCandidate(Candidate candidate);

    void update(Application entity);

    String countApplicants(String jobReference);

    boolean defineRanking(Candidate candidate, String jobReference, int rank);

    List<Application> ofJobReference(String jobReference);

}
