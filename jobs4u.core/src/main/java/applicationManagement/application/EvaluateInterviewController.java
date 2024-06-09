package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.List;

public class EvaluateInterviewController {

    private ApplicationRepository repoApplication = PersistenceContext.repositories().applications();
    private JobOpeningRepository repoJobOpening = PersistenceContext.repositories().jobOpenings();

    private EvaluateInterviewManagerService srvc = new EvaluateInterviewManagerService();

    public int gradeJobOpeningInterviews(String jobReference) {
        String interviewPlugin = repoJobOpening.findInterviewModelPluginByJobReference(jobReference);
        if(interviewPlugin == null){
            return -2;
        }

        List<Application> listOfGradableApplications = repoApplication.findGradableApplications(jobReference);
        if (listOfGradableApplications.isEmpty()){
            return -1;
        }
        return srvc.gradeListOfApplications(interviewPlugin, listOfGradableApplications);
    }
}
