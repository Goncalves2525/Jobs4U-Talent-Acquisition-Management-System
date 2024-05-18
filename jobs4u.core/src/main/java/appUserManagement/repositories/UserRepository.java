package appUserManagement.repositories;

import appUserManagement.domain.AppUser;
import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import appUserManagement.domain.dto.AppUserDTO;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends DomainRepository<Email, AppUser> {

    boolean exists(Email email);

    Optional<String> createAppUser(String email, Role role);

    Optional<String> createAppUser(String email, Role role, Role creatorRole);

    boolean swapAbility(String email, Role managerRole);

    Optional<String> authenticate(String email, String password);

    boolean authorized(String sessionToken, Role roleRequired);

    Role getValidBackofficeRole(String sessionToken);

    Optional<AppUser> ofIdentity(Email id);

    public Optional<List<AppUserDTO>> buildListByRole(List<Role> requiredRoles, Role managerRole);

    boolean endSession(String sessionToken);
}
