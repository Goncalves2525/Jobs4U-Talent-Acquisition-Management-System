package applicationManagement.application;

import applicationManagement.repositories.ApplicationRepository;
import infrastructure.persistance.PersistenceContext;
import jobOpeningManagement.domain.JobOpening;
import jobOpeningManagement.repositories.JobOpeningRepository;
import applicationManagement.domain.Application;
import plugins.Plugin;
import plugins.PluginLoader;

import java.io.*;
import java.nio.file.Files;

public class RequirementsVerificationController {
    private final ApplicationRepository appRepo = PersistenceContext.repositories().applications();
    private final JobOpeningRepository jobRepo = PersistenceContext.repositories().jobOpenings();

    public Application getApplicationById(int id) {
        return appRepo.ofIdentity(String.valueOf(id)).get();
    }

    public JobOpening getJobOpeningById(Application application) {
        String jobRefrenece = application.jobReference();
        return jobRepo.findByJobReference(jobRefrenece);
    }

    public String getRequirementsSpecification(JobOpening jobOpening) {
        return jobOpening.jobSpecifications();
    }

    public int verifyRequirements(String requirementsPath) throws NoSuchMethodException, IllegalAccessException, Exception{
        String txtFilePath = "";
        try{
            PluginLoader pluginLoader = new PluginLoader();
            Plugin plugin = pluginLoader.loadPlugin(requirementsPath);
            String jarName = plugin.getJarName();
            String jarPath = new File("plugins/jobRequirements/jar/" + jarName).getAbsolutePath();
            txtFilePath = new File("plugins/jobRequirements/txt/" + jarName.substring(0, jarName.length() - 4) + ".txt").getAbsolutePath();

            // Construct the command
            String command = "java -jar " + jarPath + " 1" + " " + txtFilePath;

            // Show the command
            System.out.println(command);

            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new File(System.getProperty("user.dir")));
            Process process = processBuilder.start();

            // Capture the output
            // Redirect output to console
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();

        }catch (IOException e){
            e.printStackTrace();
        }



        // Check the file content for "APPROVED" or "REJECTED"
        File file = new File(txtFilePath);
        if (file.exists()) {
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.contains("APPROVED")) {
                return 1;
            } else if (content.contains("REJECTED")) {
                return 0;
            }
        }

        return -1;
    }

    public void assotiateRequirementResultToApplication(Application application, int passed){
        application.assotiateRequirementResultToApplication(passed);
        appRepo.update(application);
    }
}
