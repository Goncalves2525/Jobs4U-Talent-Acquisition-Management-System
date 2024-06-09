package presentation.CustomerManager;

import appUserManagement.domain.Role;
import console.ConsoleUtils;
import eapli.framework.presentation.console.AbstractUI;
import infrastructure.authz.AuthzUI;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.application.RegisterCustomerController;
import jobOpeningManagement.domain.Address;
import jobOpeningManagement.domain.CompanyCode;
import appUserManagement.application.SignUpController;
import appUserManagement.domain.Email;
import jobOpeningManagement.repositories.CustomerRepository;
import textformat.AnsiColor;

import java.util.Optional;

public class RegisterCustomerUI{
    private CustomerRepository customerRepository= PersistenceContext.repositories().customers();
    private RegisterCustomerController ctrl = new RegisterCustomerController(customerRepository);
    private SignUpController signUpController = new SignUpController();
    private final Role validRole = Role.CUSTOMER;
    static Role managerRole;


    public void doShow(AuthzUI authzUI) {
        ConsoleUtils.buildUiHeader("Register Customer");

        // get user role, to be used as parameter on restricted user actions
        managerRole = authzUI.getValidBackofficeRole();
        if (!managerRole.showBackofficeAppAccess()) {
            ConsoleUtils.showMessageColor("You don't have permissions for this action.", AnsiColor.RED);
        }

        CompanyCode code = null;
        String name = "";
        Email email = null;
        Address address = null;

        do {
            code = new CompanyCode(ConsoleUtils.readLineFromConsole("Company Code (10 caracteres max): "));
        } while (code == null);

        name = ConsoleUtils.readLineFromConsole("Name: ");
        do {
            String emailString = ConsoleUtils.readLineFromConsole("Email: ");
            email = Email.valueOf(emailString);
        } while (email == null);

        System.out.println("-ADDRESS-");
        String street, city, postalCode;
        street = ConsoleUtils.readLineFromConsole(" Street: ");
        city = ConsoleUtils.readLineFromConsole(" City: ");
        postalCode = ConsoleUtils.readLineFromConsole(" Postal Code: ");
        address = new Address(street, city, postalCode);

        boolean success = ctrl.registerCustomer(code, name, email, address);
        if (success) {
            System.out.println("Customer registered successfully!");
            Optional<String> userSignedUp = signUpController.signUp(email, validRole);
            if (userSignedUp.isPresent()) {
                System.out.println("User registered successfully with password: " + userSignedUp.get());
            } else {
                System.out.println("User registration failed!");
                ctrl.deleteCustomer(code);
                System.out.println("Customer deleted!");
            }
        }
    }


}
