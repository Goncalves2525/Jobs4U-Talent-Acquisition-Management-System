package jobOpeningManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefineRecruitmentPhaseController {

    JobOpeningRepository repo;

    public DefineRecruitmentPhaseController(JobOpeningRepository repo) {
        this.repo=repo;
    }

    public JobOpening findJobOpeningById(String id){
        return repo.ofIdentity(id).get();
    }

    public Iterable<JobOpening> listJobOpenings() {
        return repo.findAll();
    }

    public List<String> getAllRecruitmentStates() {
        return Arrays.stream(RecruitmentState.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public String findJobOpeningRecruitmentState(JobOpening jobOpening) {
        return String.valueOf(jobOpening.getState());
    }

    public void setJobOpeningRecruitmentState(JobOpening jobOpening, RecruitmentState state) {
        Optional<JobOpening> retrievedJobOpeningOptional = repo.ofIdentity(jobOpening.identity());
        if (retrievedJobOpeningOptional.isPresent()) {
            JobOpening retrievedJobOpening = retrievedJobOpeningOptional.get();
            retrievedJobOpening.setState(state);
            repo.save(retrievedJobOpening); // Ensure changes are saved back to the repository
        } else {
            throw new NoSuchElementException("Job opening with id " + jobOpening.identity() + " not found");
        }
    }
}
