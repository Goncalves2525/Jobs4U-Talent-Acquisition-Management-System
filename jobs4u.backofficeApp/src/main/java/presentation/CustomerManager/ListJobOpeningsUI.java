package presentation.CustomerManager;

import eapli.framework.presentation.console.AbstractUI;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;

public class ListJobOpeningsUI extends AbstractUI {
    ListJobOpeningsController ctrl = new ListJobOpeningsController();

    @Override
    protected boolean doShow() {
        System.out.println("Job Openings:");
        Iterable<JobOpening> jobOpenings = ctrl.listJobOpenings();
        for(JobOpening jobOpening : jobOpenings){
            System.out.println(jobOpening.toString());
        }
        return true;
    }

    @Override
    public String headline() {
        return "LIST JOB OPENINGS";
    }
}
