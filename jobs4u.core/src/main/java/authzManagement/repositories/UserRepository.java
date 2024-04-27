package authzManagement.repositories;

import authzManagement.domain.AppUser;
import authzManagement.domain.Email;
import authzManagement.domain.Password;
import authzManagement.domain.Role;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;

import java.util.Optional;

public interface UserRepository extends DomainRepository<EmailAddress, AppUser> {

    boolean exists(EmailAddress email);

    Optional<String> createAppUser(String email, Role role);

    Optional<String> createAppUser(String email, Role role, Role creatorRole);

    Optional<String> authenticate(String email, String password);

    boolean authorized(String sessionToken, Role roleRequired);

    Role getValidBackofficeRole(String sessionToken);

    boolean endSession(String sessionToken);
}
