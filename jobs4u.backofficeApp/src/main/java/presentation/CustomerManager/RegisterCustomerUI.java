package presentation.CustomerManager;

import authzManagement.domain.User;
import authzManagement.repositories.UserRepository;
import eapli.framework.presentation.console.AbstractUI;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.application.RegisterCustomerController;
import jobOpeningManagement.domain.Address;
import jobOpeningManagement.domain.CompanyCode;
import authzManagement.application.SignUpController;
import authzManagement.domain.Email;
import utils.Utils;

public class RegisterCustomerUI extends AbstractUI {
    private RegisterCustomerController ctrl = new RegisterCustomerController();
    private SignUpController signUpController = new SignUpController();

    @Override
    protected boolean doShow() {
        UserRepository repo = PersistenceContext.repositories().users();
        User user = new User(new Email("user@isep.ipp.pt"), "password");
        repo.save(user);

        CompanyCode code = null;
        String name = "";
        Email email = null;
        Address address = null;

        do {
            code = new CompanyCode(Utils.readLineFromConsole("Company Code (10 caracteres max): "));
        }while(code == null);

        name = Utils.readLineFromConsole("Name: ");

        do{
            email = new Email(Utils.readLineFromConsole("Email: "));
        }while(email == null);

        System.out.println("-ADDRESS-");
        String street, city, postalCode;
        street = Utils.readLineFromConsole(" Street: ");
        city = Utils.readLineFromConsole(" City: ");
        postalCode = Utils.readLineFromConsole(" Postal Code: ");
        address = new Address(street, city, postalCode);

        boolean success = ctrl.registerCustomer(code, name, email, address);
        if(success) {
            System.out.println("Customer registered successfully!");
            success = signUpController.signUp(email);
            if(success) {
                System.out.println("User registered successfully!");
                return true;
            }
            else{
                System.out.println("User registration failed!");
                ctrl.deleteCustomer(code);
                System.out.println("Customer deleted!");
            }
        }
        return false;
    }

    @Override
    public String headline() {
        return "CUSTOMER REGISTRATION";
    }
}
