package jobOpeningManagement.domain;

import appUserManagement.domain.Email;
import jobOpeningManagement.application.DefineRecruitmentPhaseController;
import jobOpeningManagement.repositories.JobOpeningRepository;
import org.hibernate.annotations.ManyToAny;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class DefineRecruitmentPhaseControllerTest {

    private JobOpening jobOpening;

    @Mock
    private JobOpeningRepository repo;

    private DefineRecruitmentPhaseController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        String title = "Software Engineer";
        ContractType contractType = ContractType.FULL_TIME;
        JobMode mode = JobMode.REMOTE;
        Address address = new Address("Rua do Ouro", "Porto", "4000-000");
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", Email.valueOf("000@isep.ipp.pt"), new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
        int numberOfVacancies = 5;
        String description = "Software Engineer to work on a new project";
        Requirements requirements = new Requirements();
        jobOpening = new JobOpening(title, contractType, mode, address, company, numberOfVacancies, description, requirements);
        jobOpening.generateJobReference();

        controller = new DefineRecruitmentPhaseController(repo);
    }

    @Test
    public void testSetJobOpeningRecruitmentState() {
        RecruitmentState newState = RecruitmentState.INTERVIEWS;

        when(repo.ofIdentity(jobOpening.identity())).thenReturn(Optional.of(jobOpening));

        controller.setJobOpeningRecruitmentState(jobOpening, newState);

        assertEquals(newState, jobOpening.getState());

        JobOpening retrievedJobOpening = repo.ofIdentity(jobOpening.identity()).get();
        assertEquals(newState, retrievedJobOpening.getState());
    }

    @Test
    public void testSetJobOpeningRecruitmentStateToSameState() {
        RecruitmentState initialState = RecruitmentState.APPLICATION;
        jobOpening.setState(initialState);

        // Setup mock to return the jobOpening when queried by its identity
        when(repo.ofIdentity(jobOpening.identity())).thenReturn(Optional.of(jobOpening));

        controller.setJobOpeningRecruitmentState(jobOpening, initialState);

        assertEquals(initialState, jobOpening.getState());

        JobOpening retrievedJobOpening = repo.ofIdentity(jobOpening.identity()).get();
        assertEquals(initialState, retrievedJobOpening.getState());
    }

    @Test
    public void testSetJobOpeningRecruitmentStateToSubsequentState() {
        RecruitmentState initialState = RecruitmentState.RESULT;
        RecruitmentState finalState = RecruitmentState.SCREENING;
        jobOpening.setState(initialState);

        // Setup mock to return the jobOpening when queried by its identity
        when(repo.ofIdentity(jobOpening.identity())).thenReturn(Optional.of(jobOpening));

        controller.setJobOpeningRecruitmentState(jobOpening, finalState);

        assertEquals(finalState, jobOpening.getState());

        JobOpening retrievedJobOpening = repo.ofIdentity(jobOpening.identity()).get();
        assertEquals(finalState, retrievedJobOpening.getState());
    }

}