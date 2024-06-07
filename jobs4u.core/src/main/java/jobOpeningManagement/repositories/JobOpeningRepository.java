package jobOpeningManagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobOpeningManagement.domain.JobOpening;

public interface JobOpeningRepository extends DomainRepository<String, JobOpening>{

    void update(JobOpening entity);

    JobOpening findByJobReference(String jobRefrenece);
}
