package applicationManagement.application;

import appUserManagement.application.SignUpController;
import appUserManagement.domain.Email;
import appUserManagement.domain.Role;
import applicationManagement.domain.Application;
import applicationManagement.domain.dto.CandidateDTO;
import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;

import java.util.List;
import java.util.Optional;


public class CandidateController {
    private final CandidateRepository repo = PersistenceContext.repositories().candidates();
    private final ListCandidatesService svc = new ListCandidatesService();
    private final SignUpController signUpController = new SignUpController();
    private final ApplicationRepository applicationRepo = PersistenceContext.repositories().applications();


//    public boolean registerApplication(ApplicationDTO dto) {
//        Application application = new Application(dto.jobReference(),dto.candidate(),dto.jobOpening());
//        application = repo.save(application);
//        return application != null;
//    }

    public Optional<String> registerCandidate(CandidateDTO dto) {
        if(repo.createCandidate(dto)){
            Optional<String> pwd = signUpController.signUp(new Email(dto.getEmail()), Role.CANDIDATE);
            if(pwd.isPresent()){
                return pwd;
            }
            repo.deleteOfIdentity(dto.getEmail());
        }
        return Optional.empty();
    }

    public Iterable<Candidate> allCandidates() {
        return svc.allCandidates();
    }

    public Iterable<Candidate> allCandidatesSortedByName() {
        return svc.allCandidatesSortedByName();
    }

    public Optional<Candidate> findCandidateByEmail(String email) { return repo.ofIdentity(email); }

    public List<Application> buildApplicationList(Candidate candidate) { return applicationRepo.ofCandidate(candidate); }
}
