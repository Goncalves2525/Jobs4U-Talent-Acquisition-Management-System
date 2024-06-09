package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.List;

public class ListOrderedInterviewGradeController {

    private ApplicationRepository repoApplication = PersistenceContext.repositories().applications();
    private JobOpeningRepository repoJobOpening = PersistenceContext.repositories().jobOpenings();

    private EvaluateInterviewManagerService srvc = new EvaluateInterviewManagerService();


    public List<Application> listInterviewGradedOrdered(String jobReference) {

        List<Application> listOfApplications = repoApplication.ofJobReference(jobReference);
        if (listOfApplications == null){
            return null;
        }

        return srvc.orderApplicationsByInterviewGrade(listOfApplications);
    }
}
