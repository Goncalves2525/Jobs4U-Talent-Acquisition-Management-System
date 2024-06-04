package inMemory;

import appUserManagement.repositories.UserRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import infrastructure.persistance.RepositoryFactory;
import applicationManagement.repositories.ApplicationRepository;
import applicationManagement.repositories.CandidateRepository;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.repositories.JobOpeningRepository;
import notificationManagement.repositories.NotificationRepository;

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

    @Override
    public ApplicationRepository applications() {
        return null;
    }

    @Override
    public NotificationRepository notifications() { return null; }

    @Override
    public CandidateRepository candidates() {
        return null;
    }
}
