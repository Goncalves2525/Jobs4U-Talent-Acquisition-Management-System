package jobOpeningManagement.repositories;

import applicationManagement.domain.Candidate;
import eapli.framework.domain.repositories.DomainRepository;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;

import java.util.List;

public interface JobOpeningRepository extends DomainRepository<String, JobOpening>{

    void update(JobOpening entity);

    JobOpening findByJobReference(String jobRefrenece);

    boolean update(JobOpening entity);

    JobOpening findByJobReference(String jobRefrence);

    List<JobOpening> findAllActiveJobOpenings(Customer code);

    Iterable<JobOpening> findAllActiveJobOpeningsResultPhase();

    List<JobOpening> findAllActiveJobOpenings();

    boolean addInterviewModelPlugin(String jobReference, String plugin);

    String findInterviewModelPluginByJobReference(String jobReference);

}
