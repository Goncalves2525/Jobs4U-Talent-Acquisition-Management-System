package appUserManagement.application;

import appUserManagement.domain.Role;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.Optional;

public class AuthzController {

    private final UserRepository repo = PersistenceContext.repositories().users();

    public AuthzController() {}

    public Optional<String> doLogin(String user, String pwd){ return repo.authenticate(user, pwd); };

    public boolean validateAccess(String sessionToken, Role roleRequired) { return repo.authorized(sessionToken, roleRequired); }

    public Role getValidBackofficeRole(String sessionToken){ return repo.getValidBackofficeRole(sessionToken); }

    public boolean doLogout(String sessionToken) { return repo.endSession(sessionToken); }

    public String findCurrentUserEmail(String sessionToken) { return repo.findCurrentUserEmail(sessionToken); }
}
