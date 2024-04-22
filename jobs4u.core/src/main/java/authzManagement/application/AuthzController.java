package authzManagement.application;

import authzManagement.domain.Authz;
import authzManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

public class AuthzController {

    private final UserRepository repo = PersistenceContext.repositories().users();
    private final Authz authz = new Authz();

    public AuthzController() {}

    public boolean doLogin(String user, String pwd){ return authz.doLogin(user, pwd); };

}
