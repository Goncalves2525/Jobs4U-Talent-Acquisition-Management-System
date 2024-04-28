package bootstrap;

import appUserManagement.application.SignUpController;
import applicationManagement.application.CandidateController;
import applicationManagement.application.RegisterApplicationController;
import applicationManagement.domain.dto.ApplicationDTO;
import applicationManagement.domain.dto.CandidateDTO;
import console.ConsoleUtils;
import jobOpeningManagement.application.ListJobOpeningsController;
import jobOpeningManagement.domain.JobOpening;
import textformat.AnsiColor;

import java.util.Iterator;
import java.util.Optional;

public class ApplicationsBootstrapper {

    RegisterApplicationController ctrl = new RegisterApplicationController();
    SignUpController signUpController = new SignUpController();
    ListJobOpeningsController listJobOpeningsController = new ListJobOpeningsController();
    CandidateController candidateController = new CandidateController();

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

        // create application
        ApplicationDTO dto1 = new ApplicationDTO(jo1.jobReference(), candidateController.findCandidateByEmail(cand1email).get(), jo1);
        ctrl.registerApplication(dto1);

        ApplicationDTO dto2 = new ApplicationDTO(jo2.jobReference(), candidateController.findCandidateByEmail(cand3email).get(), jo2);
        ctrl.registerApplication(dto2);

        ApplicationDTO dto3 = new ApplicationDTO(jo2.jobReference(), candidateController.findCandidateByEmail(cand1email).get(), jo2);
        ctrl.registerApplication(dto3);
    }
}
