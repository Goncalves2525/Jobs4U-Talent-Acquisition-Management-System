package jobOpeningManagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;

import java.util.List;

public interface JobOpeningRepository extends DomainRepository<String, JobOpening>{

    boolean update(JobOpening entity);

    JobOpening findByJobReference(String jobRefrence);

    List<JobOpening> findAllActiveJobOpenings(Customer code);

    Iterable<JobOpening> findAllActiveJobOpeningsResultPhase();

}
