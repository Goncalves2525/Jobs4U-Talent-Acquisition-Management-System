import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.CompanyCode;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.repositories.CustomerRepository;
import presentation.ListJobOpeningsUI;
import presentation.RegisterJobOpeningUI;

public class Main {
    public static void main(String[] args) {
        RegisterJobOpeningUI ui = new RegisterJobOpeningUI();
        ui.show();
        ListJobOpeningsUI listJobOpeningsUI = new ListJobOpeningsUI();
        listJobOpeningsUI.show();
    }
}
