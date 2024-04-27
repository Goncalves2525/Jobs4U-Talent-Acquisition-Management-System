package jobOpeningManagement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import appUserManagement.domain.Email;

public class JobOpeningTest {
    private JobOpening jobOpening;


    @BeforeEach
    public void setUp(){
        String title = "Software Engineer";
        ContractType contractType = ContractType.FULL_TIME;
        JobMode mode = JobMode.REMOTE;
        Address address = new Address("Rua do Ouro", "Porto", "4000-000");
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", new Email("000@isep.ipp.pt"), new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
        int numberOfVacancies = 5;
        String description = "Software Engineer to work on a new project";
        jobOpening = new JobOpening(title, contractType, mode, address, company, numberOfVacancies, description, null);
    }



    @Test
    void ensureJobOpeningIsUnique(){
        String title = "Software Engineer";
        ContractType contractType = ContractType.FULL_TIME;
        JobMode mode = JobMode.REMOTE;
        Address address = new Address("Rua do Ouro", "Porto", "4000-000");
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", new Email("000@isep.ipp.pt"), new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
        int numberOfVacancies = 5;
        String description = "Software Engineer to work on a new project";
        JobOpening jobOpening2 = new JobOpening(title, contractType, mode, address, company, numberOfVacancies, description, null);

        String id = jobOpening.identity();
        String id2 = jobOpening2.identity();

        assert(!id.equals(id2));
    }

    @Test
    void ensureJobOpeningHasCorrectState(){
        assert(jobOpening.state() == RecruitmentState.APPLICATION);
    }

    @Test
    void ensureJobReferenceDoesNotExceedMaxChars(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CompanyCode("12345678900000"));
    }

    @Test
    void ensureJobReferenceUsesSequentialNumbers(){
        JobOpening jobOpening2 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), jobOpening.company(), 1, "test", null);
        JobOpening jobOpening3 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), jobOpening.company(), 1, "test", null);
        String id = jobOpening.identity();
        String[] parts = id.split("-");
        int number = Integer.parseInt(parts[1]);
        String id2 = jobOpening2.identity();
        String[] parts2 = id2.split("-");
        int number2 = Integer.parseInt(parts2[1]);
        String id3 = jobOpening3.identity();
        String[] parts3 = id3.split("-");
        int number3 = Integer.parseInt(parts3[1]);

        assert(number2 == (number + 1));
        assert(number3 == (number2 + 1));
    }

    @Test
    void ensureNumberOfVacanciesIsPositive(){
        //Success
        assert(jobOpening.numberOfVacancies() > 0);

        //Failure
        Customer company = new Customer(new CompanyCode("ISEP"), "ISEP", new Email("000@isep.ipp.pt"), new Address("Rua Dr. António Bernardino de Almeida", "Porto", "4200-072"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, -1, "test", null));
    }

}
