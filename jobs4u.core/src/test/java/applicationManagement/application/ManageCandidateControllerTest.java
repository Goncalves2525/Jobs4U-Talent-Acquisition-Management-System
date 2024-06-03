package applicationManagement.application;

import appUserManagement.domain.AppUser;
import appUserManagement.domain.Email;
import appUserManagement.domain.Password;
import appUserManagement.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManageCandidateControllerTest {
    private AppUser candidateUser;
    private AppUser operatorUser;

    @BeforeEach
    public void setup() {
        candidateUser=new AppUser(Email.valueOf("ana@mail.pt"),new Password(),Role.CANDIDATE);
        operatorUser=new AppUser(Email.valueOf("sofia@mail.pt"),new Password(),Role.OPERATOR);
    }

    @Test
    public void verifyOnlyOperatorsCanEnableDisableCandidates(){

    }

    @Test
    public void verifyAccessOnlyToEnabledCandidates(){}
}
