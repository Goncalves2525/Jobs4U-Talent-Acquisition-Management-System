package applicationManagement.repositories;

import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.CandidateDTO;
import eapli.framework.domain.repositories.DomainRepository;

public interface CandidateRepository extends DomainRepository<String, Candidate>{

    boolean createCandidate(CandidateDTO dto);
}
