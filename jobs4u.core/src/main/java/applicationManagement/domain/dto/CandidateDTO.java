package applicationManagement.domain.dto;

import lombok.Getter;

@Getter
public class CandidateDTO {

    private String name;
    private String email;
    private String phone;

    public CandidateDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "[" + name + "] " + email +" <"+ phone;
    }
}
