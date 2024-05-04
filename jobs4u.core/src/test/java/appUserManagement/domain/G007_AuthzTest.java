package appUserManagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class G007_AuthzTest {

    @Test
    public void isPasswordValidationGuaranteingRules(){
        Password pwd = new Password();

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