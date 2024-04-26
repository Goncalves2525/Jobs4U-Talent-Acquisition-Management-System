package authzManagement.domain;

import authzManagement.application.PasswordGeneratorService;
import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

@Embeddable
public class Password implements ValueObject {

    private String value;
    private static final int PASSWORD_LENGTH = 8;
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_=+.";

    public Password() {
    }

    public String generatePassword(){
         String generatedPassword = PasswordGeneratorService.generatePassword(PASSWORD_LENGTH);
         this.value = generatedPassword;
         return generatedPassword;
    }

    public boolean createPassword(String pwd){
        if(pwd.length() >= PASSWORD_LENGTH && containsUpper(pwd) && containsLower(pwd)
                && containsNumber(pwd) && containsSymbol(pwd)){
            this.value = pwd;
            return true;
        }
        return false;
    }

    private boolean containsSymbol(String pwd) {
        for (int i = 0; i < SPECIAL.length(); i++) {
            char c = SPECIAL.charAt(i);
            if (pwd.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsNumber(String pwd) {
        for (int i = 0; i < DIGITS.length(); i++) {
            char c = DIGITS.charAt(i);
            if (pwd.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLower(String pwd) {
        for (int i = 0; i < LOWER.length(); i++) {
            char c = LOWER.charAt(i);
            if (pwd.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsUpper(String pwd) {
        for (int i = 0; i < UPPER.length(); i++) {
            char c = UPPER.charAt(i);
            if (pwd.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String dots = "";
        for (int i = 0; i < this.value.length(); i++) {
            dots = dots.concat("*");
        }
        return "dots";
    }
}
