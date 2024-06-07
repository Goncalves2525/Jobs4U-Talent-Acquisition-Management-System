package applicationManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.domain.*;
import appUserManagement.domain.dto.AppUserDTO;
import appUserManagement.repositories.UserRepository;
import applicationManagement.domain.Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.mockito.InjectMocks;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ManageCandidateControllerTest {
    private AppUser candidateUser;

    @Mock
    private AuthzController authzController;

    @Mock
    private UserRepository userRepo;


    private ManageCandidateController manageCandidateController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        candidateUser= new AppUser(Email.valueOf("maria@mail.pt"),new Password(),Role.CANDIDATE);
        manageCandidateController = new ManageCandidateController(userRepo, authzController);
    }

    @Test
    public void verifyOnlyOperatorsCanEnableDisableCandidates(){
        String email = "ana@mail.pt";
        Role operatorRole = Role.OPERATOR;
        Role customerManagerRole = Role.CUSTOMER;
        String operatorSessionToken = "operator-session-token";
        String customerManagerSessionToken = "customer-manager-session-token";

        // Mock the AuthzController behavior
        when(authzController.validateAccess(operatorSessionToken, operatorRole)).thenReturn(true);
        when(authzController.validateAccess(customerManagerSessionToken, customerManagerRole)).thenReturn(false);

        // Mock the repository behavior
        when(userRepo.swapCandidateAbility(email, operatorRole)).thenReturn(true);

        boolean resultOperator = manageCandidateController.swapCandidateAbility(email, operatorRole, operatorSessionToken);
        assertTrue(resultOperator);

        boolean resultCustomerManager = manageCandidateController.swapCandidateAbility(email, customerManagerRole, customerManagerSessionToken);
        assertFalse(resultCustomerManager);

        verify(authzController).validateAccess(operatorSessionToken, operatorRole);
        verify(authzController).validateAccess(customerManagerSessionToken, customerManagerRole);

        verify(userRepo).swapCandidateAbility(email, operatorRole);
        verify(userRepo, never()).swapCandidateAbility(email, customerManagerRole);
    }


    @Test
    public void verifyAccessOnlyToEnabledCandidates(){
        Role candidateRole = Role.CANDIDATE;
        String sessionToken = "session_token";

        when(authzController.validateAccess(sessionToken, Role.OPERATOR)).thenReturn(true);

        AppUser disabledAppUser = candidateUser;

        when(userRepo.findByEmail(disabledAppUser.getEmail().toString())).thenReturn(Optional.of(disabledAppUser));

        disabledAppUser.swapAbility();

        assertEquals(Ability.DISABLED, disabledAppUser.getAbility());

        when(authzController.validateAccess(sessionToken, candidateRole)).thenReturn(false);

        boolean hasAccess = manageCandidateController.swapCandidateAbility(disabledAppUser.getEmail().toString(), candidateRole, sessionToken);

        assertFalse(hasAccess);

        verify(authzController).validateAccess(sessionToken, candidateRole);
    }

}
