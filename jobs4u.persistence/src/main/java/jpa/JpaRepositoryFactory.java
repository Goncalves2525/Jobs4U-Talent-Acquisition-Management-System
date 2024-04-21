package jpa;

import authzManagement.persistence.UserRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import infrastructure.persistance.RepositoryFactory;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.repositories.JobOpeningRepository;

public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public TransactionalContext newTransactionalContext() {
        return null;
    }

    @Override
    public JobOpeningRepository jobOpenings() {
        return new JpaJobOpeningRepository();
    }

    @Override
    public CustomerRepository customers() {
        return new JpaCustomerRepository();
    }

    @Override
    public UserRepository users() {
        return null;
    }

//    @Override
//    public UserRepository users(TransactionalContext autoTx) {
//        return null;
//    }
//
//    @Override
//    public UserRepository users() {
//        return null;
//    }
//
//    @Override
//    public EventConsumptionRepository eventConsumption() {
//        return null;
//    }
//
//    @Override
//    public EventRecordRepository eventRecord() {
//        return null;
//    }

}
