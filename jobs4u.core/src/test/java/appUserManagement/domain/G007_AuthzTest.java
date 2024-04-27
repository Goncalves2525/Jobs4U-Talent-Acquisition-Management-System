package appUserManagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class G007_AuthzTest {

    // TODO: review it
//    @Test
//    void verifyThatOnlyAnEmailIsValidOnUserField() {
//        String invalidEmail1 = "username";
//        String invalidEmail2 = "username@domain";
//        String invalidEmail3 = "username@domain";
//        String validEmail1 = "username@domain.com";
//
//        Email email1 = (Email) Email.tryValueOf(invalidEmail1).right();
//        Email email2 = (Email) Email.tryValueOf(invalidEmail2).right();
//        Email email3 = (Email) Email.tryValueOf(invalidEmail3).right();
//        Email email4 = (Email) Email.tryValueOf(validEmail1).right();
//
//        System.out.println(email1);
//        System.out.println(email2);
//        System.out.println(email3);
//        System.out.println(email4);
//
//        assertTrue(email1.isEmpty());
//        assertTrue(email2.isEmpty());
//        assertTrue(email3.isEmpty());
//        assertFalse(email4.isEmpty());
//    }

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