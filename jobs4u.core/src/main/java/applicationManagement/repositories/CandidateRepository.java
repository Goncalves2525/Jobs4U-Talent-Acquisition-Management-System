package applicationManagement.repositories;

import applicationManagement.domain.dto.CandidateDTO;
import eapli.framework.domain.repositories.DomainRepository;
import applicationManagement.domain.Candidate;

public interface CandidateRepository extends DomainRepository<String, Candidate>{

    boolean createCandidate(CandidateDTO dto);


}
