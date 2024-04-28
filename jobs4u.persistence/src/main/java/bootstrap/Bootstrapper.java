package bootstrap;

import console.ConsoleUtils;

public class Bootstrapper {

    UsersBootsrapper usersBootsrapper = new UsersBootsrapper();
    CandidatesBootstrapper candidatesBootstrapper = new CandidatesBootstrapper();
    JobOpeningsBootstrapper jobOpeningsBootstrapper = new JobOpeningsBootstrapper();
    ApplicationsBootstrapper applicationsBootstrapper = new ApplicationsBootstrapper();

    public Bootstrapper() {}

    public void execute(){
        ConsoleUtils.buildUiHeader("BOOTSTRAP");
        System.out.println();
        usersBootsrapper.execute();
        candidatesBootstrapper.execute();
        jobOpeningsBootstrapper.execute();
        applicationsBootstrapper.execute();
    }
}
