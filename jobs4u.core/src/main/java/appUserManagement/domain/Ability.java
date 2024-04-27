package appUserManagement.domain;

import lombok.Getter;

@Getter
public enum Ability {

    ENABLED("enabled", true),
    DISABLED("disabled", false);

    private final String abilityName;

    private final boolean abilityValue;

    Ability(String abilityName, boolean abilityValue) {
        this.abilityName = abilityName;
        this.abilityValue = abilityValue;
    }

}
