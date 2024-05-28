package applicationManagement.domain.dto;

import applicationManagement.domain.CandidateAbility;
import lombok.Getter;

@Getter
public class CandidateDTO {

    private String name;
    private String email;
    private String phone;
    private final CandidateAbility ability;

    public CandidateDTO(String name, String email, String phone, CandidateAbility ability) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.ability=ability;
    }

    @Override
    public String toString() {
        return "[" + name + "] " + email +" <"+ phone +" <" + ability.name() + ">";
    }
}
