package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;

import java.util.List;

public class EvaluateInterviewController {

    private ApplicationRepository repo = PersistenceContext.repositories().applications();

    private EvaluateInterviewManagerService srvc = new EvaluateInterviewManagerService();

    public int gradeJobOpeningInterviews(String jobReference) {
        List<Application> listOfGradableApplications = repo.findGradableApplications(jobReference);
        if (listOfGradableApplications.isEmpty()){
            return 0;
        }
        return srvc.gradeListOfApplications(listOfGradableApplications);
    }
}
