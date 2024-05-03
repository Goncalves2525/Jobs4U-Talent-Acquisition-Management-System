package applicationManagement.application;

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
    private CandidateRepository candidateRepository = PersistenceContext.repositories().candidates();

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
