package appUserManagement.application;

import appUserManagement.domain.Role;
import appUserManagement.domain.dto.AppUserDTO;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageBackofficeUserController {

    private final UserRepository repo = PersistenceContext.repositories().users();

    public ManageBackofficeUserController() {}

    public Optional<List<AppUserDTO>> getListBackofficeUsers(Role managerRole) {
        List<Role> requiredRoles = new ArrayList<>();
        requiredRoles.add(Role.CUSTOMERMANAGER);
        requiredRoles.add(Role.OPERATOR);
        return repo.buildListByRole(requiredRoles, managerRole);
    }

    public boolean swapAbility(String email, Role managerRole) { return repo.swapAbility(email, managerRole); }
}
