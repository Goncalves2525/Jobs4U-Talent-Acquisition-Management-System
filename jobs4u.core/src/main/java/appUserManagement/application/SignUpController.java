package appUserManagement.application;

import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.Optional;

public class SignUpController {
    private UserRepository repo = PersistenceContext.repositories().users();

    public Optional<String> signUp(Email email, Role role){
        if (repo.exists(email)) {
            System.out.println("Username already exists");
            return Optional.empty();
        }
        Optional<String> password = repo.createAppUser(email.toString(), role);
        return password;
    }

    /**
     * Sign up version that uses creator role validation.
     * @param email
     * @param role
     * @param creatorRole
     * @return generated password for the user
     */
    public Optional<String> signUp(Email email, Role role, Role creatorRole){
        if (repo.exists(email)) {
            System.out.println("Username already exists");
            return Optional.empty();
        }
        Optional<String> password = repo.createAppUser(email.toString(), role, creatorRole);
        return password;
    }
}
