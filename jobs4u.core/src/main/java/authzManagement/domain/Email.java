package authzManagement.domain;
import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email extends EmailAddress implements Comparable<EmailAddress>{

    public Email() {
        // for ORM
    }

    public Email(String address){
        super(address);
    }


}
