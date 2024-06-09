package applicationManagement.application;

import appUserManagement.application.AuthzController;
import appUserManagement.repositories.UserRepository;
import eapli.framework.application.ApplicationService;
import infrastructure.persistance.PersistenceContext;
import applicationManagement.domain.Candidate;
import applicationManagement.repositories.CandidateRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
public class ListCandidatesService {
    private CandidateRepository candidateRepository;

    public ListCandidatesService(CandidateRepository repo) {
       this.candidateRepository=repo;
    }

    public Iterable<Candidate> allCandidates() {
        return candidateRepository.findAll();
    }

    public Iterable<Candidate> allCandidatesSortedByName() {
        Iterable<Candidate> candidates = candidateRepository.findAll();

        List<Candidate> candidateList = new ArrayList<>();
        candidates.forEach(candidateList::add);

        return candidateList.stream()
                .sorted(Comparator.comparing(Candidate::name))
                .collect(Collectors.toList());
    }

}
