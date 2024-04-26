package presentation.CustomerManager;


import utils.Utils;

public class CustomerManagerUI{


    public boolean doShow() {
        int option = 0;
        do{
            System.out.println("1. Register Job Opening");
            System.out.println("2. List Job Openings");
            System.out.println("3. Register Customer");
            System.out.println("4. Test Plugin");
            System.out.println("5. Register Application");
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
                case 4:
                    TestPluginUI testPluginUI = new TestPluginUI();
                    testPluginUI.show();
                    break;
                case 5:
                    RegisterApplicationUI registerApplicationUI = new RegisterApplicationUI();
                    registerApplicationUI.show();
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
