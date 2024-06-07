package applicationManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.domain.Role;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

public class ManageCandidateController {
    private final UserRepository repo;
    private final AuthzController authzController;

    public ManageCandidateController(UserRepository repo, AuthzController authzController) {
        this.repo = repo;
        this.authzController = authzController;
    }

    public boolean swapCandidateAbility(String email, Role operatorRole, String sessionToken) {
        if (authzController.validateAccess(sessionToken, operatorRole)) {
            return repo.swapCandidateAbility(email, operatorRole);
        }
        return false;
    }
}
