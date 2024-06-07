package presentation.CustomerManager;

import appUserManagement.domain.Role;
import applicationManagement.application.UploadInterviewController;
import applicationManagement.domain.Application;
import infrastructure.authz.AuthzUI;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UploadInterviewUI {

    private UploadInterviewController ctrl = new UploadInterviewController();
    static Role managerRole;

    protected void doShow(AuthzUI authzUI) {
        String interview = selectTxtFile();
        String appID = extractApplicationNumber(interview);

        boolean success = false;
        Application application = ctrl.findApplicationById(appID);
        if (application == null) {
            System.out.println("Application not found");
        }
        success = ctrl.checkIfApplicationIsPending(application);
        if (success) {
            System.out.println("Application already has been uploaded and checked");
        }
    }

    public String selectTxtFile() {
        String directoryPath = "C:\\Users\\paulo\\Desktop\\sem4pi-23-24-2nb1\\Interviews";

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            List<String> txtFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".txt"))
                    .map(Path::toString)
                    .collect(Collectors.toList());

            if (txtFiles.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No .txt files found in the directory.");
                return null;
            }

            String selectedFile = (String) JOptionPane.showInputDialog(
                    null,
                    "Select a .txt file:",
                    "File Selection",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    txtFiles.toArray(),
                    txtFiles.get(0)
            );

            if (selectedFile != null) {
                JOptionPane.showMessageDialog(null, "You selected: " + selectedFile);
                return selectedFile;
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while accessing the directory.");
        }

        return null;
    }

    public String extractApplicationNumber(String interview) {
        if (interview == null) {
            return null; // or throw an exception
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(interview))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Application:")) {
                    String[] parts = line.split("Application:");
                    if (parts.length > 1) {
                        String numberPart = parts[1].trim();
                        // Assuming the number is the first integer after "Application:"
                        String[] numberParts = numberPart.split("\\D+");
                        if (numberParts.length > 0) {
                            return numberParts[0];
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception according to your application's logic
        }

        return null; // Return null if the number is not found or any error occurs
    }
}