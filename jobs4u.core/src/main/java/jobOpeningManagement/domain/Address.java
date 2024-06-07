package jobOpeningManagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Value;

@Embeddable
public class Address implements ValueObject {
    private String street;
    private String city;
    private String postalCode;

    protected Address() {
        // for ORM
    }

    public Address(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + postalCode;
    }




}
