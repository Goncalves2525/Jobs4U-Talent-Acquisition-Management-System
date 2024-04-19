package jobOpeningManagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

@Embeddable
public class CompanyCode implements ValueObject, Comparable<CompanyCode> {
    private String code;

    protected CompanyCode() {
        // for ORM
    }

    public CompanyCode(String code) {
        //must only have 10 characters max
        if (code.length() > 10) {
            throw new IllegalArgumentException("Code must have 10 characters max");
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public int compareTo(CompanyCode o) {
        return code.compareTo(o.code);
    }
}
