package appUserManagement.domain;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class G007_AuthzTest {

    private Password pwd;
    private AuthzController controller;

    @Mock
    private UserRepository repo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        pwd = new Password();
    }

    @Test
    public void isPasswordValidationGuaranteingRules(){
        boolean invalidBadLength = pwd.createPassword("123");
        boolean invalidMissingUpper = pwd.createPassword("abc123!!!");
        boolean invalidMissingLower = pwd.createPassword("ABC123!!!");
        boolean invalidMissingNumber = pwd.createPassword("ABCabc!!!");
        boolean invalidMissingSymbol = pwd.createPassword("ABCabc123");
        boolean valid = pwd.createPassword("ABCabc123!!!");

        assertFalse(invalidBadLength);
        assertFalse(invalidMissingUpper);
        assertFalse(invalidMissingLower);
        assertFalse(invalidMissingNumber);
        assertFalse(invalidMissingSymbol);
        assertTrue(valid);
    }

    @Test
    void testFindCurrentUserEmail() {
       controller = new AuthzController(repo);

        // Stub the repository method to return a known email when called with a session token
        String expectedEmail = "test@example.com";
        String sessionToken = "testSessionToken";
        when(repo.findCurrentUserEmail(sessionToken)).thenReturn(expectedEmail);

        String actualEmail = controller.findCurrentUserEmail(sessionToken);

        verify(repo, times(1)).findCurrentUserEmail(sessionToken);

        // Assert that the returned email matches the expected email
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    void testGetValidBackofficeRole() {
        controller = new AuthzController(repo);

        Role expectedRole = Role.ADMIN;
        String sessionToken = "testSessionToken";
        when(repo.getValidBackofficeRole(sessionToken)).thenReturn(expectedRole);

        // Call the method under test
        Role actualRole = controller.getValidBackofficeRole(sessionToken);

        // Verify that the repository's method was called with the correct session token
        verify(repo, times(1)).getValidBackofficeRole(sessionToken);

        // Assert that the returned role matches the expected role
        assertEquals(expectedRole, actualRole);
    }

    @Test
    void testValidateAccess() {
        controller = new AuthzController(repo);

        // Define test data
        String sessionToken = "testSessionToken";
        Role roleRequired = Role.CUSTOMERMANAGER; // Or any other role required

        // Stub the repository method to return true when called with the provided session token and role
        when(repo.authorized(sessionToken, roleRequired)).thenReturn(true);

        // Call the method under test
        boolean accessValidated = controller.validateAccess(sessionToken, roleRequired);

        // Verify that the repository's method was called with the correct session token and role
        verify(repo, times(1)).authorized(sessionToken, roleRequired);

        // Assert that the access validation result is true
        assertTrue(accessValidated);

        Role roleRequired2 = Role.CANDIDATE; // Or any other role required

        // Stub the repository method to return true when called with the provided session token and role
        when(repo.authorized(sessionToken, roleRequired2)).thenReturn(false);

        // Call the method under test
        boolean accessValidated2 = controller.validateAccess(sessionToken, roleRequired2);

        // Verify that the repository's method was called with the correct session token and role
        verify(repo, times(1)).authorized(sessionToken, roleRequired2);

        // Assert that the access validation result is true
        assertFalse(accessValidated2);
    }

}