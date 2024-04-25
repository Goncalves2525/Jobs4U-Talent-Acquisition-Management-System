package authzManagement.domain;
import eapli.framework.general.domain.model.EmailAddress;

public class Email extends EmailAddress implements Comparable<EmailAddress>{

    public Email(String address){
        super(address);
    }

}
