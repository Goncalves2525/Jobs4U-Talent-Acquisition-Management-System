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
