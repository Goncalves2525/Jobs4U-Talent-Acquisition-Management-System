package jobOpeningManagement.application;

import eapli.framework.application.ApplicationService;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.domain.Customer;

import java.util.Iterator;
import java.util.List;

@ApplicationService
public class ListCustomersService {
    private CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    public Iterable<Customer> allCustomers() {
        return customerRepository.findAll();
    }
}
