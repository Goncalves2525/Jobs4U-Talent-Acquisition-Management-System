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
    @Column(unique = true)
    private EmailAddress email;

    @Getter
    @Column
    private String password;

    protected User(){
        //for ORM
    }

    public User(EmailAddress email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean sameAs(Object other) {
        User user = (User) other;
        return this.email.equals(user.email);
    }

    @Override
    public EmailAddress identity() {
        return email;
    }
}
