package applicationManagement.repositories;

import appUserManagement.domain.Role;
import appUserManagement.domain.dto.AppUserDTO;
import applicationManagement.domain.dto.CandidateDTO;
import eapli.framework.domain.repositories.DomainRepository;
import applicationManagement.domain.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends DomainRepository<String, Candidate>{

    boolean createCandidate(CandidateDTO dto);

    boolean swapAbility(String email, Role managerRole);
}
