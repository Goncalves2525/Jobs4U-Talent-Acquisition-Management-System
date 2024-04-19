package jobOpeningManagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements AggregateRoot<CompanyCode> {
    @Id
    private CompanyCode code;
    @Column
    private String name;
    @Column
    private String email;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobOpening> jobOpenings = new ArrayList<JobOpening>();

    protected Customer() {
        // for ORM
    }

    public Customer(CompanyCode code, String name, String email, Address address) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public CompanyCode getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "getCode=" + code +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
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
