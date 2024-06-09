package jobOpeningManagement.domain;

import jobOpeningManagement.application.RegisterCustomerController;
import jobOpeningManagement.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appUserManagement.domain.Email;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class registerCustomerControllerTest {

    @Mock
    private CustomerRepository repo;

    private RegisterCustomerController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RegisterCustomerController(repo);
    }

    @Test
    void testRegisterCustomer() {
        CompanyCode code = new CompanyCode("123");
        String name = "Test Company";
        Email email = Email.valueOf("test@example.com");
        Address address = new Address("123 Test St", "Test City", "12345");

        when(repo.save(any(Customer.class))).thenReturn(new Customer(code, name, email, address));

        boolean customerRegistered = controller.registerCustomer(code, name, email, address);

        verify(repo, times(1)).save(any(Customer.class));

        // Assert that the customer was registered successfully
        assertTrue(customerRegistered);
    }

    @Test
    void testDeleteCustomer() {
        CompanyCode code = new CompanyCode("123");

        when(repo.ofIdentity(code)).thenReturn(Optional.of(new Customer(code, "Test Company", Email.valueOf("test@example.com"), new Address())));

        boolean customerDeleted = controller.deleteCustomer(code);

        verify(repo, times(1)).ofIdentity(code);
        verify(repo, times(1)).delete(any(Customer.class));

        // Assert that the customer was deleted successfully
        assertTrue(customerDeleted);
    }
}
