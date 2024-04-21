package jobOpeningManagement.domain;

import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.repositories.CustomerRepository;
import jobOpeningManagement.repositories.JobOpeningRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JobOpeningTest {

    private JobOpeningRepository jobRepo = PersistenceContext.repositories().jobOpenings();
    private CustomerRepository customerRepo = PersistenceContext.repositories().customers();
    private JobOpening jobOpening;

    @BeforeEach
    public void setUp(){
        String title = "Software Engineer";
        ContractType contractType = ContractType.FULL_TIME;
        JobMode mode = JobMode.REMOTE;
        Address address = new Address("Rua do Ouro", "Porto", "4000-000");
        Customer company = customerRepo.ofIdentity(new CompanyCode("ISEP")).get();
        int numberOfVacancies = 5;
        String description = "Software Engineer to work on a new project";
        RecruitmentState state = RecruitmentState.APPLICATION;
        jobOpening = new JobOpening(title, contractType, mode, address, company, numberOfVacancies, description, null, state);
        jobRepo.save(jobOpening);
    }

    @AfterEach
    public void tearDown(){
        jobRepo.delete(jobOpening);
    }

    @Test
    void ensureJobOpeningIsUnique(){
        Iterable<JobOpening> jobOpenings = jobRepo.findAll();
        boolean found = false;
        for (JobOpening job : jobOpenings) {
            if (job.sameAs(jobOpening)) {
                found = true;
                break;
            }
        }
        assert(!found);
    }

    @Test
    void ensureJobOpeningHasCorrectState(){
        //Correct state
        assert(jobOpening.state() == RecruitmentState.APPLICATION);

        //Incorrect state
        Customer company = customerRepo.ofIdentity(new CompanyCode("ISEP")).get();
        JobOpening jobOpening2 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, 1, "test", null, RecruitmentState.ANALYSIS);
        Assertions.assertNull(jobRepo.save(jobOpening2));
        jobRepo.delete(jobOpening2);
    }

    @Test
    void ensureJobOpeningHasAllAttributes(){
        //Correct attributes
        assert(jobOpening.title().equals("Software Engineer"));
        assert(jobOpening.contractType() == ContractType.FULL_TIME);
        assert(jobOpening.mode() == JobMode.REMOTE);
        assert(jobOpening.address().getStreet().equals("Rua do Ouro"));
        assert(jobOpening.address().getCity().equals("Porto"));
        assert(jobOpening.address().getPostalCode().equals("4000-000"));
        assert(jobOpening.company().getCode().getCode().equals("ISEP"));
        assert(jobOpening.numberOfVacancies() == 5);
        assert(jobOpening.description().equals("Software Engineer to work on a new project"));
        assert(jobOpening.state() == RecruitmentState.APPLICATION);

        //Incorrect title
        Customer company = customerRepo.ofIdentity(new CompanyCode("ISEP")).get();
        JobOpening jobOpening2 = new JobOpening(null, ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, 1, "test", null, RecruitmentState.APPLICATION);
        Assertions.assertNull(jobRepo.save(jobOpening2));
        jobRepo.delete(jobOpening2);

        //Incorrect Description
        JobOpening jobOpening3 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, 1, null, null, RecruitmentState.APPLICATION);
        Assertions.assertNull(jobRepo.save(jobOpening3));
        jobRepo.delete(jobOpening3);
    }

    @Test
    void ensureJobReferenceIsFromExistingCustomer(){
        //Success
        Iterable<Customer> customers = customerRepo.findAll();
        boolean found = false;
        for (Customer customer : customers) {
            if (customer.getCode().getCode().equals("ISEP")) {
                found = true;
                break;
            }
        }
        assert(!found);

        //Failure
        Customer company = new Customer(new CompanyCode("TEST"), "test", "test", new Address("test", "test", "test"));
        JobOpening jobOpening2 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, 1, "test", null, RecruitmentState.APPLICATION);
        Assertions.assertNull(jobRepo.save(jobOpening2));
        jobRepo.delete(jobOpening2);
    }

    @Test
    void ensureJobReferenceDoesNotExceedMaxChars(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CompanyCode("12345678900000"));
    }

    @Test
    void ensureJobReferenceUsesSequentialNumbers(){
        JobOpening jobOpening2 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), jobOpening.company(), 1, "test", null, RecruitmentState.APPLICATION);
        JobOpening jobOpening3 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), jobOpening.company(), 1, "test", null, RecruitmentState.APPLICATION);
        String id = jobOpening.jobReference();
        String[] parts = id.split("-");
        int number = Integer.parseInt(parts[1]);
        String id2 = jobOpening2.jobReference();
        String[] parts2 = id2.split("-");
        int number2 = Integer.parseInt(parts2[1]);
        String id3 = jobOpening3.jobReference();
        String[] parts3 = id3.split("-");
        int number3 = Integer.parseInt(parts3[1]);

        assert(number2 == number + 1);
        assert(number3 == number2 + 1);
    }

    @Test
    void ensureNumberOfVacanciesIsPositive(){
        //Success
        assert(jobOpening.numberOfVacancies() > 0);

        //Failure
        Customer company = customerRepo.ofIdentity(new CompanyCode("ISEP")).get();
        JobOpening jobOpening2 = new JobOpening("test", ContractType.FULL_TIME, JobMode.ON_SITE, new Address("test", "test", "test"), company, -1, "test", null, RecruitmentState.APPLICATION);
        Assertions.assertNull(jobRepo.save(jobOpening2));
        jobRepo.delete(jobOpening2);
    }

}
