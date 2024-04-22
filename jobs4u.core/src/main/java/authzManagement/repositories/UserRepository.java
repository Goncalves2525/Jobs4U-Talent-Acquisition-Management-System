package authzManagement.repositories;

import authzManagement.domain.Email;
import authzManagement.domain.Role;
import authzManagement.domain.User;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;

public interface UserRepository extends DomainRepository<EmailAddress, User> {

    boolean exists(EmailAddress email);

    /**
     * Creates user and returns a random password.
     * @param name
     * @param email
     * @return random password.
     */
    String createUser(String name, String email);

    boolean authenticateUser(String email, String password, Role validRole);
}
