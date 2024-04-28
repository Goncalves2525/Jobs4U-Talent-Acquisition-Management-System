package jobOpeningManagement.application;

import eapli.framework.application.ApplicationService;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.domain.Customer;

import java.util.Optional;

@ApplicationService
public class ListCustomersService {
    private CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    public Iterable<Customer> allCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> customerByCode(CompanyCode code) { return customerRepository.ofIdentity(code); }
}
