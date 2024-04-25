package authzManagement.application;

import authzManagement.domain.Email;
import authzManagement.domain.AppUser;
import authzManagement.domain.Password;
import authzManagement.domain.Role;
import authzManagement.repositories.UserRepository;
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



}
