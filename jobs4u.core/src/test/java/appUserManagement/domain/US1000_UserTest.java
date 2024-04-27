package appUserManagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class US1000_UserTest {

    @Test
    public void verifyAbilityIsSetAsEnabledWhenUserIsRegistered(){
        AppUser appUser = new AppUser(new Email("teste@teste.pt"), new Password(), Role.OPERATOR);
        assertTrue(appUser.getAbility().isAbilityValue());
    }
}
