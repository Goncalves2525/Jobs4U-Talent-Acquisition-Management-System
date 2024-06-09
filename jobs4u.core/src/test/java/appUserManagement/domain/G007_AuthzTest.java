package appUserManagement.domain;

import jobOpeningManagement.application.GenerateCandidateFieldsFileController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class G007_AuthzTest {

    private Password pwd;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        pwd = new Password();
    }

    @Test
    public void isPasswordValidationGuaranteingRules(){
        boolean invalidBadLength = pwd.createPassword("123");
        boolean invalidMissingUpper = pwd.createPassword("abc123!!!");
        boolean invalidMissingLower = pwd.createPassword("ABC123!!!");
        boolean invalidMissingNumber = pwd.createPassword("ABCabc!!!");
        boolean invalidMissingSymbol = pwd.createPassword("ABCabc123");
        boolean valid = pwd.createPassword("ABCabc123!!!");

        assertFalse(invalidBadLength);
        assertFalse(invalidMissingUpper);
        assertFalse(invalidMissingLower);
        assertFalse(invalidMissingNumber);
        assertFalse(invalidMissingSymbol);
        assertTrue(valid);
    }

}