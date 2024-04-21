package jobOpeningManagement.application;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.Customer;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import jobOpeningManagement.repositories.JobOpeningRepository;

import java.util.Iterator;


public class RegisterJobOpeningController {

    private JobOpeningRepository repo = PersistenceContext.repositories().jobOpenings();
    private ListCustomersService svc = new ListCustomersService();

    public boolean registerJobOpening(JobOpeningDTO dto) {
        JobOpening jobOpening = new JobOpening(dto.title(), dto.contractType(), dto.mode(), dto.address(), dto.company(), dto.numberOfVacancies(), dto.description(), dto.requirements());
        jobOpening = repo.save(jobOpening);
        return jobOpening != null;
    }

    public Iterable<Customer> allCustomers() {
        return svc.allCustomers();
    }
}
