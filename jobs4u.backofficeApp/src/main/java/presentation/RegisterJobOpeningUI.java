package presentation;

import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.RegisterJobOpeningController;

import java.util.Scanner;

import static java.lang.System.console;

public class RegisterJobOpeningUI extends AbstractUI{

    RegisterJobOpeningController ctrl = new RegisterJobOpeningController();


    @Override
    protected boolean doShow() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the job reference:");
        String jobReference = scanner.nextLine();
        ctrl.registerJobOpening(jobReference);
        System.out.println("Job Opening registered successfully!(or not)");
        return true;
    }

    @Override
    public String headline() {
        return "JOB OPENING REGISTRATION";
    }
}
