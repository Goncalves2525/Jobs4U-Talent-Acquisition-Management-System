package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;

public class RankingController {

    private ApplicationRepository repo = PersistenceContext.repositories().applications();
    private ListApplicationsController appController = new ListApplicationsController();
    private CandidateController candidateController = new CandidateController();
    private ListJobOpeningsController jobOpeningController = new ListJobOpeningsController();

    public boolean defineRanking(Candidate candidate, String jobReference, String rank) {
        return repo.defineRanking(candidate, jobReference, rank);
    }

    public void rankingCandidatesOfJobOpening(String jobReference, String rank) {

        String[] emails = rank.trim().split(";");
        int i = 1;
        for (String email : emails) {
            defineRanking(candidateController.findCandidateByEmail(email).get(), jobReference, String.valueOf(i));
            i++;
        }

        Iterable<Application> applications = appController.listApplicationsOfJobRefence(jobReference);
        for (Application application : applications) {
            if (application.getRankNumber() == null || application.getRankNumber().getRank().equals("Not Ranked")){
                defineRanking(candidateController.findCandidateByEmail(application.getCandidate().email()).get(), jobReference, "Not selected");
            }
            System.out.println(application.getRankNumber().getRank());
        }
    }

    public boolean validateIfJobOpeningClosed(String jobReference){
        Iterable <JobOpening> jobOpenings = jobOpeningController.listJobOpenings();
        for (JobOpening jobOpening : jobOpenings) {
            if (jobOpening.getJobReference().equals(jobReference) && jobOpening.getState().equals(RecruitmentState.RESULT)){
                return true;
            }
        }
        return false;
    }
}
