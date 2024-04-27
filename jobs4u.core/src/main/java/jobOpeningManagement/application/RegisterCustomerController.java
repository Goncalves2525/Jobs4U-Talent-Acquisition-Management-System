package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.Address;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.repositories.CustomerRepository;
import appUserManagement.domain.Email;

public class RegisterCustomerController {
    private CustomerRepository repo = PersistenceContext.repositories().customers();

    public boolean registerCustomer(CompanyCode code, String name, Email email, Address address) {
        Customer customer = new Customer(code, name, email, address);
        customer = repo.save(customer);
        return customer != null;
    }

    public boolean deleteCustomer(CompanyCode code) {
        Customer customer = repo.ofIdentity(code).get();
        repo.delete(customer);
        return true;
    }
}
