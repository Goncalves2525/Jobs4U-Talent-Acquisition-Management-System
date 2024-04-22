package presentation.CustomerManager;

import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ShowUiAction;
import utils.Utils;

public class CustomerManagerUI extends AbstractUI {


    @Override
    protected boolean doShow() {
        int option = 0;
        do{
            System.out.println("1. Register Job Opening");
            System.out.println("2. List Job Openings");
            System.out.println("3. Register Customer");
            System.out.println("0. Exit");
            option = Utils.readIntegerFromConsole("Option: ");
            switch (option){
                case 1:
                    RegisterJobOpeningUI registerJobOpeningUI = new RegisterJobOpeningUI();
                    registerJobOpeningUI.show();
                    break;
                case 2:
                    ListJobOpeningsUI listJobOpeningsUI = new ListJobOpeningsUI();
                    listJobOpeningsUI.show();
                    break;
                case 3:
                    RegisterCustomerUI registerCustomerUI = new RegisterCustomerUI();
                    registerCustomerUI.show();
                    break;
                case 0:
                    return false;
                default:
                    System.out.println("Invalid option");
            }
        }while(option != 0);

        return true;
    }


    @Override
    public String headline() {
        return "Customer Manager UI";
    }
}
