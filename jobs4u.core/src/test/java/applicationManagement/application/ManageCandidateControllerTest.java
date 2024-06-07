package applicationManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.domain.*;
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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ManageCandidateControllerTest {
    private AppUser candidateUser;
    private AppUser candidateUser2;
    private AppUser operatorUser;
    private AppUser customerUser;

    @Mock
    private AuthzController authzController;

    @Mock
    private UserRepository userRepo;

    @Mock
    private ManageCandidateController manageCandidateController;

    @Mock
    private UserRepository userRepository;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private InputStream inputStream;
    @BeforeEach
    public void setup() {
        // Mock input/output streams
        inputStream = new ByteArrayInputStream(new byte[0]);
        MockitoAnnotations.openMocks(this);
        candidateUser=new AppUser(Email.valueOf("ana@mail.pt"),new Password(),Role.CANDIDATE);
        candidateUser2= new AppUser(Email.valueOf("maria@mail.pt"),new Password(),Role.CANDIDATE);
        operatorUser=new AppUser(Email.valueOf("sofia@mail.pt"),new Password(),Role.OPERATOR);
        customerUser = new AppUser(Email.valueOf("carlos@mail.pt"), new Password(), Role.CUSTOMER);
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

        // Test with Operator role
        boolean resultOperator = manageCandidateController.swapCandidateAbility(email, operatorRole, operatorSessionToken);
        assertTrue(resultOperator);

        // Test with CustomerManager role
        boolean resultCustomerManager = manageCandidateController.swapCandidateAbility(email, customerManagerRole, customerManagerSessionToken);
        assertFalse(resultCustomerManager);

        // Verify the AuthzController method was called with correct roles and tokens
        verify(authzController).validateAccess(operatorSessionToken, operatorRole);
        verify(authzController).validateAccess(customerManagerSessionToken, customerManagerRole);

        // Verify the repository method was called with correct role
        verify(userRepo).swapCandidateAbility(email, operatorRole);
        verify(userRepo, never()).swapCandidateAbility(email, customerManagerRole);
    }


    @Test
    public void verifyAccessOnlyToEnabledCandidates(){

    }

}
