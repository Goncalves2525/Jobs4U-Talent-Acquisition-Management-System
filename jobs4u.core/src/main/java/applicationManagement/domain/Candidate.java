package applicationManagement.domain;

import appUserManagement.domain.Ability;
import applicationManagement.domain.CandidateAbility;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Candidate implements AggregateRoot<String> {

    @Id
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    @Column
    private String name;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column
    private CandidateAbility ability;

    protected Candidate() {
        // for ORM
    }

    public Candidate(String email, String phoneNumber, String name) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.ability=CandidateAbility.ENABLED;
    }

    public String email() {
        return email;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "NAME: " + name +
                "\nEmail: " + email +
                "\nPhoneNumber: " + phoneNumber+
                "\nAbility: " + ability;
    }

    public void swapAbility() {
        if(this.ability.isAbilityValue()){
            this.ability = CandidateAbility.DISABLED;
        } else {
            this.ability = CandidateAbility.ENABLED;
        }
    }

    @Override
    public boolean sameAs(Object other) {
        Candidate candidate = (Candidate) other;
        return candidate.equals(candidate.email);
    }

    @Override
    public String identity() {
        return email;
    }
}
