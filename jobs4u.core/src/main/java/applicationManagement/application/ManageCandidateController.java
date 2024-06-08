package applicationManagement.application;

import appUserManagement.domain.Ability;
import appUserManagement.domain.AppUser;
import appUserManagement.domain.Role;
import appUserManagement.domain.dto.AppUserDTO;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageCandidateController {
    private final UserRepository repo = PersistenceContext.repositories().users();
    public ManageCandidateController() {}

    public boolean swapCandidateAbility(String email, Role operatorRole) {
        return repo.swapCandidateAbility(email, operatorRole); }

    public Optional<AppUserDTO> getUserByEmail(String email) {
        Optional<AppUser> appUser = repo.findByEmail(email);
        return appUser.map(AppUserDTO::from);

    }
}
