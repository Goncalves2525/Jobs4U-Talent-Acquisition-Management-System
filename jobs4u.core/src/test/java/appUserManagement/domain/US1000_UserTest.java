package appUserManagement.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class US1000_UserTest {

    private  AppUser appUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void verifyAbilityIsSetAsEnabledWhenUserIsRegistered(){
        appUser = new AppUser(new Email("teste@teste.pt"), new Password(), Role.OPERATOR);
        assertTrue(appUser.getAbility().isAbilityValue());
    }
}
