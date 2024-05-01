package presentation.Operator;

import console.ConsoleUtils;
import infrastructure.authz.AuthzUI;
import presentation.CustomerManager.*;

public class OperatorUI {

    public OperatorUI() {
    }

    public boolean doShow(AuthzUI authzUI) {
        int option = 0;
        do{
            System.out.println("1. Register Application");
            System.out.println("2. List Candidates");
            System.out.println("3. Generate Template To Collect Candidate Fields");
            System.out.println("0. Exit");
            option = ConsoleUtils.readIntegerFromConsole("Option: ");
            switch (option){
                case 1:
                    RegisterApplicationUI registerApplicationUI = new RegisterApplicationUI();
                    registerApplicationUI.show();
                    break;
                case 2:
                    ListCandidatesUI listCandidatesUI = new ListCandidatesUI();
                    listCandidatesUI.show();
                    break;
                case 3:
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
