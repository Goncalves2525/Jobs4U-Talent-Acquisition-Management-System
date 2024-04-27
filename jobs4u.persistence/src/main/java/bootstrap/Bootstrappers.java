package bootstrap;

import console.ConsoleUtils;

public class Bootstrappers {

    UsersBootsrapper usersBootsrapper = new UsersBootsrapper();

    public Bootstrappers() {}

    public void execute(){
        ConsoleUtils.buildUiHeader("BOOTSTRAP");
        usersBootsrapper.execute();
        // TODO: implement and add other bootstraps
    }
}
