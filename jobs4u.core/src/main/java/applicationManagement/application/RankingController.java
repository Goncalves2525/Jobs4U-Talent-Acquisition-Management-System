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
            if (application.getRankNumber() == null || application.getRankNumber().getRank().equals("Not Ranked")) {
                defineRanking(candidateController.findCandidateByEmail(application.getCandidate().email()).get(), jobReference, "Not selected");
            }
            System.out.println(application.getRankNumber().getRank());
        }
    }

    public boolean validateJobReference(String jobReference) {
        Iterable<JobOpening> jobOpenings = jobOpeningController.listJobOpenings();
        for (JobOpening jobOpening : jobOpenings) {
            if (jobOpening.getJobReference().equals(jobReference) && jobOpening.getState().equals(RecruitmentState.RESULT)) {
                System.out.println("Job Opening already in Result phase!");
                return false;
            } else if (jobOpening.getJobReference().equals(jobReference)) {
                return true;
            }
        }
        System.out.println("Job Reference: " + jobReference + " does not exist!");
        return false;
    }

    public boolean validateRank(String rank, String jobReference) {
        String[] emails = rank.trim().split(";");
        boolean check = false;
        for (String email : emails) {
            check = candidateController.findCandidateByEmail(email).isPresent();
            if (!check) {
                System.out.println("Email: " + email + " does not exist!");
                return false;
            } else {
                check = false;
                Iterable<Application> applications = appController.listApplicationsOfJobRefence(jobReference);
                for (Application application : applications) {
                    if (application.getCandidate().email().equals(email)) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    System.out.println("Email: " + email + " does not applied to this Job Opening!");
                    return check;
                }
            }
        }
        return check;
    }
}
