package bootstrap;

import appUserManagement.application.SignUpController;
import applicationManagement.application.CandidateController;
import applicationManagement.application.RankingController;
import applicationManagement.application.RegisterApplicationController;
import applicationManagement.domain.ApplicationStatus;
import applicationManagement.domain.dto.ApplicationDTO;
import applicationManagement.domain.dto.CandidateDTO;
import console.ConsoleUtils;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

public class ApplicationsBootstrapper {

    RegisterApplicationController ctrl = new RegisterApplicationController();
    SignUpController signUpController = new SignUpController();
    ListJobOpeningsController listJobOpeningsController = new ListJobOpeningsController();
    CandidateController candidateController = new CandidateController();

    RankingController rankingController = new RankingController();

    public ApplicationsBootstrapper() {
    }

    public void execute() {
        ConsoleUtils.showMessageColor("** Applications created **", AnsiColor.CYAN);

        // get job openings
        Iterator<JobOpening> joIterator = listJobOpeningsController.listJobOpenings().iterator();
        JobOpening jo1 = null;
        if(joIterator.hasNext()) { jo1 = joIterator.next(); }
        JobOpening jo2 = null;
        if(joIterator.hasNext()) { jo2 = joIterator.next(); }
        JobOpening jo3 = null;
        if(joIterator.hasNext()) { jo3 = joIterator.next(); }

        // create candidates
        String cand1email = "duarte@mail.pt";
        CandidateDTO cand1 = new CandidateDTO("Duarte", cand1email, "987");
        Optional<String> cand1pwd = candidateController.registerCandidate(cand1);
        System.out.println("Candidate : " + cand1.getEmail() + " | Password: " + cand1pwd.get());

        String cand2email = "elsa@mail.pt";
        CandidateDTO cand2 = new CandidateDTO("Elsa", cand2email, "654");
        Optional<String> cand2pwd = candidateController.registerCandidate(cand2);
        System.out.println("Candidate : " + cand2.getEmail() + " | Password: " + cand2pwd.get());

        String cand3email = "felisberto@mail.pt";
        CandidateDTO cand3 = new CandidateDTO("Felisberto", cand3email, "321");
        Optional<String> cand3pwd = candidateController.registerCandidate(cand3);
        System.out.println("Candidate : " + cand3.getEmail() + " | Password: " + cand3pwd.get());

        String cand4email = "genoveva@mail.pt";
        CandidateDTO cand4 = new CandidateDTO("Genoveva", cand4email, "001");
        Optional<String> cand4pwd = candidateController.registerCandidate(cand4);
        System.out.println("Candidate : " + cand4.getEmail() + " | Password: " + cand4pwd.get());

        String cand5email = "zulmira@mail.pt";
        CandidateDTO cand5 = new CandidateDTO("Zulmira", cand5email, "999");
        Optional<String> cand5pwd = candidateController.registerCandidate(cand5);
        System.out.println("Candidate : " + cand5.getEmail() + " | Password: " + cand5pwd.get());

        String cand6email = "marante@mail.pt";
        CandidateDTO cand6 = new CandidateDTO("Marante", cand6email, "777");
        Optional<String> cand6pwd = candidateController.registerCandidate(cand6);
        System.out.println("Candidate : " + cand6.getEmail() + " | Password: " + cand6pwd.get());

        String cand7email = "janedoe@email.com";
        CandidateDTO cand7 = new CandidateDTO("Jane Doe", cand7email, "967654321");
        Optional<String> cand7pwd = candidateController.registerCandidate(cand7);
        System.out.println("Candidate : " + cand7.getEmail() + " | Password: " + cand7pwd.get());

        String cand8email = "johndoe@email.com";
        CandidateDTO cand8 = new CandidateDTO("John Doe", cand8email, "961234567");
        Optional<String> cand8pwd = candidateController.registerCandidate(cand8);
        System.out.println("Candidate : " + cand8.getEmail() + " | Password: " + cand8pwd.get());

        // create application
        ApplicationDTO dto1 = new ApplicationDTO(jo1.jobReference(), candidateController.findCandidateByEmail(cand1email).get(), jo1, "comment", new Date(), null,null, ApplicationStatus.SUBMITTED, "", "", null);
        ctrl.registerApplication(dto1);

        ApplicationDTO dto2 = new ApplicationDTO(jo2.jobReference(), candidateController.findCandidateByEmail(cand3email).get(), jo2, "comment", new Date(), null, null, ApplicationStatus.SUBMITTED, "", "", null);
        ctrl.registerApplication(dto2);

        ApplicationDTO dto3 = new ApplicationDTO(jo2.jobReference(), candidateController.findCandidateByEmail(cand1email).get(), jo2, "comment", new Date(), null,null, ApplicationStatus.SUBMITTED, "", "", null);
        ctrl.registerApplication(dto3);

        ApplicationDTO dto4 = new ApplicationDTO(jo3.jobReference(), candidateController.findCandidateByEmail(cand7email).get(), jo3, "comment", new Date(), null,null, ApplicationStatus.REJECTED, "", "scomp/output/IBM-000123/janedoe@email.com", null);
        ctrl.registerApplication(dto4);
        rankingController.defineRanking(candidateController.findCandidateByEmail(cand7email).get(), jo3.jobReference(), "2");

        ApplicationDTO dto5 = new ApplicationDTO(jo3.jobReference(), candidateController.findCandidateByEmail(cand8email).get(), jo3, "comment", new Date(), null,null, ApplicationStatus.HIRED, "", "scomp/output/IBM-000123/johndoe@email.com", null);
        ctrl.registerApplication(dto5);
        rankingController.defineRanking(candidateController.findCandidateByEmail(cand8email).get(), jo3.jobReference(), "1");
    }
}
