package applicationManagement.domain;

import lombok.Getter;

@Getter
public enum CandidateAbility {

    ENABLED("enabled", true),
    DISABLED("disabled", false);

    private final String abilityName;

    private final boolean abilityValue;

    CandidateAbility(String abilityName, boolean abilityValue) {
        this.abilityName = abilityName;
        this.abilityValue = abilityValue;
    }
}
