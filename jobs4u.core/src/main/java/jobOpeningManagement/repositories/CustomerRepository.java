package jobOpeningManagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;

import java.util.Optional;

public interface CustomerRepository extends DomainRepository<CompanyCode, Customer> {

    Optional<Customer> withEmail(String email);
}
