package applicationManagement.application;

import appUserManagement.domain.Role;
import applicationManagement.repositories.CandidateRepository;
import infrastructure.persistance.PersistenceContext;

public class ManageCandidateController {
    private final CandidateRepository repo = PersistenceContext.repositories().candidates();
    public ManageCandidateController() {}

    public boolean swapAbility(String email, Role managerRole) {
        return repo.swapAbility(email, managerRole); }
}
