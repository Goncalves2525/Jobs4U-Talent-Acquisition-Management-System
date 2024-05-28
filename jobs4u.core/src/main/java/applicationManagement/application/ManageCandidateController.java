package applicationManagement.application;

import appUserManagement.domain.Role;
import applicationManagement.domain.Candidate;
import applicationManagement.domain.dto.CandidateDTO;
import applicationManagement.repositories.CandidateRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageCandidateController {
    private final CandidateRepository repo = PersistenceContext.repositories().candidates();
    private final ListCandidatesService svc = new ListCandidatesService();
    public ManageCandidateController() {}

    public boolean swapAbility(String email, Role managerRole) { return repo.swapAbility(email, managerRole); }
}
