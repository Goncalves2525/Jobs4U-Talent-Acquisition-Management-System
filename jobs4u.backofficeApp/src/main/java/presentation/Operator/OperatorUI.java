package presentation.Operator;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import infrastructure.persistance.PersistenceContext;

public class OperatorUI {
    UserRepository userRepo = PersistenceContext.repositories().users();
    AuthzController authzController = new AuthzController();
    public OperatorUI() {
    }

    public boolean doShow(AuthzUI authzUI) {
        int option = 0;
        do{
            System.out.println("1. Register Application");
            System.out.println("2. Register Candidate");
            System.out.println("3. List Candidates");
            System.out.println("4. Manage Candidates");
            System.out.println("5. Generate Template To Collect Candidate Fields");
            System.out.println("0. Exit");
            option = ConsoleUtils.readIntegerFromConsole("Option: ");
            switch (option){
                case 1:
                    RegisterApplicationUI registerApplicationUI = new RegisterApplicationUI();
                    registerApplicationUI.show();
                    break;
                case 2:
                    RegisterCandidateUI registerCandidateUI = new RegisterCandidateUI();
                    registerCandidateUI.show();
                    break;
                case 3:
                    ListCandidatesUI listCandidatesUI = new ListCandidatesUI();
                    listCandidatesUI.show();
                    break;
                case 4:
                    ManageCandidateUI manageCandidateUI = new ManageCandidateUI(userRepo,authzController);
                    manageCandidateUI.doShow(authzUI);
                    break;
                case 5:
                    GenerateCandidateFieldsFileUI generateCandidateFieldsFileUI = new GenerateCandidateFieldsFileUI();
                    generateCandidateFieldsFileUI.doShow(authzUI);
                    break;
                case 0:
                    return false;
                default:
                    System.out.println("Invalid option");
            }
        }while(option != 0);
        return true;
    }

}
