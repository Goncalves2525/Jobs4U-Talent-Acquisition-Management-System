package presentation;

import eapli.framework.presentation.console.AbstractUI;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.application.RegisterJobOpeningController;
import jobOpeningManagement.domain.*;
import jobOpeningManagement.domain.dto.JobOpeningDTO;
import jobOpeningManagement.repositories.CustomerRepository;

import java.util.Scanner;

import static java.lang.System.console;

public class RegisterJobOpeningUI extends AbstractUI{

    RegisterJobOpeningController ctrl = new RegisterJobOpeningController();


    @Override
    protected boolean doShow() {
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", "isep.ipp.pt", new Address("street", "city", "postal code"));
        CustomerRepository repo = PersistenceContext.repositories().customers();
        repo.save(company);


        String title = "test title";
        ContractType contractType = ContractType.FULL_TIME;
        JobMode mode = JobMode.REMOTE;
        Address address = new Address("test street", "test city", "test postal code");
        int numberOfVacancies = 2;
        String description = "test description";
        Requirements requirements = new Requirements("test requirements");
        RecruitmentState state = RecruitmentState.APPLICATION;
        JobOpeningDTO jobOpeningDTO = new JobOpeningDTO(title, contractType, mode, address, company, numberOfVacancies, description, requirements, state);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        ctrl.registerJobOpening(jobOpeningDTO);
        return true;
    }

    @Override
    public String headline() {
        return "JOB OPENING REGISTRATION";
    }
}
