package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.RecruitmentState;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefineRecruitmentPhaseController {

    JobOpeningRepository repo;

    public DefineRecruitmentPhaseController(JobOpeningRepository jobOpeningRepository){
        this.repo=jobOpeningRepository;
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
        jobOpening.setState(state);
    }
}
