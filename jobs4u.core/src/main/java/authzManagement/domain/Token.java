package authzManagement.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Embeddable;

@Embeddable
public class Token {

    private String value;

    public Token() {
    }

    public String generateToken(String email) {
        String generatedToken = getUserFromEmail(email) + generateTimestamp();
        this.value = generatedToken;
        return generatedToken;
    }

    private String getUserFromEmail(String email) {
        String[] split = email.split("@");
        return split[0];
    }

    private String generateTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }

    public String showToken() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
