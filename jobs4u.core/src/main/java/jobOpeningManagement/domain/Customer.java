package jobOpeningManagement.domain;

import appUserManagement.domain.Email;
import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements AggregateRoot<CompanyCode> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Embedded
    @Column(unique = true)
    private CompanyCode code;

    @Getter
    @Column
    private String name;

    @Getter
    @Column
    private Email email;

    @Getter
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobOpening> jobOpenings = new ArrayList<JobOpening>();

    protected Customer() {
        // for ORM
    }

    public Customer(CompanyCode code, String name, Email email, Address address) {
        if (code == null || name == null || email == null || address == null) {
            throw new IllegalArgumentException();
        }
        this.code = code;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return name + "(" + code + ")";
    }

    @Override
    public boolean sameAs(Object other) {
        Customer customer = (Customer) other;
        return this.code.equals(customer.code);
    }

    @Override
    public CompanyCode identity() {
        return code;
    }

}
