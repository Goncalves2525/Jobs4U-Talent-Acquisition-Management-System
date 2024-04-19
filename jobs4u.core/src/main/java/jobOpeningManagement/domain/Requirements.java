package jobOpeningManagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

@Embeddable
public class Requirements implements ValueObject {
    private String requirements;

    protected Requirements() {
        // for ORM
    }

    public Requirements(String requirements) {
        this.requirements = requirements;
    }

    public String getRequirements() {
        return requirements;
    }

    @Override
    public String toString() {
        return requirements;
    }
}
