package inMemory;

import authzManagement.repositories.UserRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import infrastructure.persistance.RepositoryFactory;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class InMemoryRepositoryFactory implements RepositoryFactory {
    @Override
    public TransactionalContext newTransactionalContext() {
        return null;
    }

    @Override
    public JobOpeningRepository jobOpenings() {
        return null;
    }

    @Override
    public CustomerRepository customers() {
        return null;
    }

    @Override
    public UserRepository users() {
        return null;
    }
}
