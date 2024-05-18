package jobOpeningManagement.domain;

import appUserManagement.domain.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(new CompanyCode("TEST"), "UNIT TEST", Email.valueOf("user@isep.ipp.pt"), new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
    }

    @Test
    void ensureCustomerHasFullInformation() {
        //Success
        assert (customer.getCode() != null);
        assert (customer.getName() != null);
        assert (customer.getEmail() != null);
        assert (customer.getAddress() != null);

        //Failure
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Customer(null, null, null, null));
    }

    @Test
    void ensureCompanyCodeHasMaxLength() {
        //Success
        CompanyCode companyCode = customer.getCode();
        assert (companyCode.getCode().length() <= 10);

        //Failure
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CompanyCode("12345678901"));
    }

}
