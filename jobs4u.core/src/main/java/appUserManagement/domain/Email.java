package appUserManagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.functional.Either;
import eapli.framework.strings.StringMixin;
import eapli.framework.strings.util.StringPredicates;
import eapli.framework.validations.Check;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Email implements Comparable<Email>, ValueObject, Serializable, StringMixin {

    private static final long serialVersionUID = 1L;

    private final String email;

    protected Email(final String address) {
        this.email = address;
    }

    protected Email() {
        this.email = "";
    }

    public static Email valueOf(final String address) {
        Preconditions.nonEmpty(address, "email address should neither be null nor empty");
        Preconditions.ensure(StringPredicates.isEmail(address), "Invalid E-mail format");
        return new Email(address);
    }

    public static Either<String, Email> tryValueOf(final String address) {
        return (new Check()).failIf().isEmpty(address, () -> {
            return "email address should neither be null nor empty";
        }).not(StringPredicates.isEmail(address), () -> {
            return "Invalid E-mail format";
        }).elseSucceed(() -> {
            return new Email(address);
        });
    }

    @Override
    public String toString() {
        return this.email;
    }

    public int compareTo(final Email o) {
        return this.email.compareTo(o.email);
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Email)) {
            return false;
        } else {
            Email other = (Email) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$email = this.email;
                Object other$email = other.email;
                if (this$email == null) {
                    if (other$email != null) {
                        return false;
                    }
                } else if (!this$email.equals(other$email)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Email;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $email = this.email;
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        return result;
    }

}
