package authzManagement.application;

import authzManagement.domain.Email;
import authzManagement.domain.User;
import authzManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

public class SignUpController {
    private UserRepository repo = PersistenceContext.repositories().users();
    private PasswordGeneratorService svc = new PasswordGeneratorService();

    public boolean signUp(Email email){
        if (repo.exists(email)) {
            System.out.println("Username already exists");
            return false;
        }
        User user = new User(email, svc.generatePassword(9));
        repo.save(user);
        return true;
    }



}
