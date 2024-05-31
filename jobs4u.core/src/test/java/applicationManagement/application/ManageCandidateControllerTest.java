package applicationManagement.application;

import applicationManagement.domain.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManageCandidateControllerTest {
    private Candidate c;

    @BeforeEach
    public void setup() {
        c=new Candidate("ana@mail.pt","123456789","Ana");
    }

    @Test
    public void verifyAbilityIsSetAsEnabledWhenCandidateIsRegistered(){
        assertTrue(c.getAbility().isAbilityValue());
    }
}
