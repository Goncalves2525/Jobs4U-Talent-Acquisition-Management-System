package appUserManagement.domain.dto;

import appUserManagement.domain.Ability;
import appUserManagement.domain.AppUser;
import appUserManagement.domain.Role;
import lombok.Getter;

@Getter
public class AppUserDTO {

    private final String email;
    private final Role role;
    private final Ability ability;

    public AppUserDTO(String email, Role role, Ability ability) {
        this.email = email;
        this.role = role;
        this.ability = ability;
    }

    public static AppUserDTO from(AppUser appUser) {
        return new AppUserDTO(appUser.getEmail().toString(), appUser.getRole(), appUser.getAbility());
    }

    @Override
    public String toString() {
        return "[" + role.name() + "] " + email + " <" + ability.name() + ">";
    }
}
