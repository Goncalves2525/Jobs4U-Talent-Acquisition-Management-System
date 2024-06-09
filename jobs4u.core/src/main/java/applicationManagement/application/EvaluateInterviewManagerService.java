package applicationManagement.application;

import applicationManagement.domain.Application;
import applicationManagement.repositories.ApplicationRepository;
import console.ConsoleUtils;
import eapli.framework.application.ApplicationService;
import infrastructure.persistance.PersistenceContext;
import plugins.Plugin;
import plugins.PluginLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@ApplicationService
public class EvaluateInterviewManagerService {

    private ApplicationRepository repo = PersistenceContext.repositories().applications();

    public int gradeListOfApplications(String interviewPlugin, List<Application> listOfGradableApplications) {

        if (listOfGradableApplications.isEmpty()) {
            return 0;
        }

        // Execute the Plugin for each Application
        for(Application app : listOfGradableApplications) {

            // execute plugin
            String txtFilePath = "";

            try{
                PluginLoader pluginLoader = new PluginLoader();
                Plugin plugin = pluginLoader.loadPlugin(interviewPlugin);
                String jarName = plugin.getJarName();
                String jarPath = new File(interviewPlugin).getAbsolutePath();
                txtFilePath = new File(app.getInterviewReplyPath()).getAbsolutePath();

                // Construct the command
                String command = "java -jar " + jarPath + " 3 " + txtFilePath;

                // Show the command
                //System.out.println(command); // TESTING

                //
                ConsoleUtils.buildUiTitle("Evaluation Results for " + app.getCandidate().name());

                // Execute the command
                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                processBuilder.redirectErrorStream(true);
                processBuilder.directory(new File(System.getProperty("user.dir")));
                Process process = processBuilder.start();

                // Capture the output & redirect output to console
                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                process.waitFor();

            } catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Save grades from plugin work
        return repo.saveGrades(listOfGradableApplications);
    }

    public List<Application> orderApplicationsByInterviewGrade(List<Application> listOfApplications) {

        if(listOfApplications == null) {
            return null;
        }

        if(verifyApplicationPendingInterviewEvaluation(listOfApplications)){
            return new ArrayList<>();
        }

        return orderApplicationListByInterviewGrade(listOfApplications);
    }

    private boolean verifyApplicationPendingInterviewEvaluation(List<Application> listOfApplications) {
        // verify if there's any application with default grade value (-101) and available interview reply
        for (Application app : listOfApplications) {
            if (app.getInterviewGrade() == -101 && app.getInterviewReplyPath() != null){
                return true;
            }
        }
        return false;
    }

    private List<Application> orderApplicationListByInterviewGrade(List<Application> listOfApplications) {
        listOfApplications.sort(Comparator.nullsFirst(Comparator
                .comparingInt(Application::getInterviewGrade).reversed()
                .thenComparing(app -> app.getCandidate().name())));
        return listOfApplications;
    }
}
