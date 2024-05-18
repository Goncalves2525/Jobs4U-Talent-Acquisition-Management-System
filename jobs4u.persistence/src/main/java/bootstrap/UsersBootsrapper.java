package bootstrap;

import appUserManagement.application.SignUpController;
import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import console.ConsoleUtils;
import jpa.JpaUserRepository;
import textformat.AnsiColor;

import java.util.Optional;

public class UsersBootsrapper {

    private final SignUpController signUpController = new SignUpController();

    public UsersBootsrapper() {}

    public void execute() {
        ConsoleUtils.showMessageColor("** Users created **", AnsiColor.CYAN);

        String adminEmail = "admin@mail.pt";
        Optional<String> adminPwd = signUpController.signUp(Email.valueOf(adminEmail), Role.ADMIN);
        System.out.println("User: " + adminEmail + " | Password: " + adminPwd.get());

        String custmanEmail = "custman@mail.pt";
        Optional<String> custmanPwd = signUpController.signUp(Email.valueOf(custmanEmail), Role.CUSTOMERMANAGER);
        System.out.println("User: " + custmanEmail + " | Password: " + custmanPwd.get());

        String operatEmail = "operat@mail.pt";
        Optional<String> operatPwd = signUpController.signUp(Email.valueOf(operatEmail), Role.OPERATOR);
        System.out.println("User: " + operatEmail + " | Password: " + operatPwd.get());

//        String customerEmail = "customer@mail.pt";
//        Optional<String> customerPwd = signUpController.signUp(Email.valueOf(customerEmail), Role.CUSTOMER);
//        System.out.println("User: " + customerEmail + " | Password: " + customerPwd.get());

        String candEmail = "candidate@mail.pt";
        Optional<String> candPwd = signUpController.signUp(Email.valueOf(candEmail), Role.CANDIDATE);
        System.out.println("User: " + candEmail + " | Password: " + candPwd.get());

//        String custmanEmailDisabled = "custmanDIS@mail.pt";
//        Optional<String> custmanDisabledPwd = signUpController.signUp(Email.valueOf(custmanEmailDisabled), Role.CUSTOMERMANAGER);
//        System.out.println("User: " + custmanEmailDisabled + " | Password: " + custmanDisabledPwd.get());
//        JpaUserRepository repo = new JpaUserRepository();
//        repo.swapAbility(custmanEmailDisabled, Role.ADMIN);
    }
}
