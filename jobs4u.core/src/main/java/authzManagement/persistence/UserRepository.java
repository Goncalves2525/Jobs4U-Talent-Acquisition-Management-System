package authzManagement.persistence;

import authzManagement.domain.Role;

public interface UserRepository {

    /**
     * Creates user and returns a random password.
     * @param name
     * @param email
     * @return random password.
     */
    String createUser(String name, String email);

    boolean authenticateUser(String email, String password, Role validRole);
}
