package authzManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class User implements AggregateRoot<EmailAddress> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Embedded
    @Column
    private Email email;

    @Getter
    @Column
    private String password;

    protected User(){
        //for ORM
    }

    public User(Email email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean sameAs(Object other) {
        return email.equals(((User) other).email);
    }

    @Override
    public Email identity() {
        return email;
    }
}
