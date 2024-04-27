package appUserManagement.domain;

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

    @Getter
    @Enumerated(EnumType.STRING)
    @Column
    private Ability ability;

    protected AppUser() {
        //for ORM
    }

    public AppUser(EmailAddress email, Password password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.token = new Token();
        this.ability = Ability.ENABLED;
    }

    public void addToken(Token token) {
        this.token = token;
    }

    public void swapAbility() {
        if(this.ability.isAbilityValue()){
            this.ability = Ability.DISABLED;
        } else {
            this.ability = Ability.ENABLED;
        }
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
