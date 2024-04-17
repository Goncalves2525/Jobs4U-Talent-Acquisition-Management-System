package presentation;

import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.ListJobOpeningsController;

public class ListJobOpeningsUI extends AbstractUI {
    ListJobOpeningsController ctrl = new ListJobOpeningsController();

    @Override
    protected boolean doShow() {
        System.out.println("Job Openings:");
        ctrl.listJobOpenings();
        return true;
    }

    @Override
    public String headline() {
        return "LIST JOB OPENINGS";
    }
}
