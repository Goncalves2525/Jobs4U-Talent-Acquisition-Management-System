package authzManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.general.domain.model.EmailAddress;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class AppUser implements AggregateRoot<EmailAddress> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Embedded
    @Column(unique = true)
    @AttributeOverride(name = "email", column = @Column(name = "email"))
    private EmailAddress email;

    @Getter
    @Embedded
    @Column
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private Password password;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Getter
    @Embedded
    @Column
    @AttributeOverride(name = "value", column = @Column(name = "token"))
    private Token token;

    protected AppUser() {
        //for ORM
    }

    public AppUser(EmailAddress email, Password password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.token = new Token();
    }

    public void addToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean sameAs(Object other) {
        AppUser appUser = (AppUser) other;
        return this.email.equals(appUser.email);
    }

    @Override
    public EmailAddress identity() {
        return email;
    }
}
