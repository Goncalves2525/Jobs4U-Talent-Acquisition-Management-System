package applicationManagement.application;

import appUserManagement.domain.Role;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

public class ManageCandidateController {
    private final UserRepository repo = PersistenceContext.repositories().users();
    public ManageCandidateController() {}

    public boolean swapCandidateAbility(String email, Role operatorRole) {
        return repo.swapCandidateAbility(email, operatorRole); }
}
