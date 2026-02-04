import appUserManagement.application.AuthzController;
import appUserManagement.domain.Email;
import jpa.JpaUserRepository;
import appUserManagement.domain.AppUser;

import java.util.Optional;

public class TestAuth {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java TestAuth <email> <password>");
            return;
        }

        String email = args[0];
        String password = args[1];

        System.out.println("=== Testing Authentication ===");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println();

        JpaUserRepository repo = new JpaUserRepository();

        // First, check if user exists
        System.out.println("1. Checking if user exists...");
        Optional<AppUser> user = repo.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("   ✓ User found!");
            System.out.println("   Email: " + user.get().getEmail());
            System.out.println("   Role: " + user.get().getRole());
            System.out.println("   Ability: " + user.get().getAbility());
        } else {
            System.out.println("   ✗ User NOT found!");
            return;
        }

        System.out.println();
        System.out.println("2. Attempting authentication...");
        AuthzController controller = new AuthzController(repo);
        Optional<String> token = controller.doLogin(email, password);

        if (token.isPresent()) {
            System.out.println("   ✓ Authentication SUCCESS!");
            System.out.println("   Token: " + token.get());
        } else {
            System.out.println("   ✗ Authentication FAILED!");
        }
    }
}
