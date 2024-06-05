import utils.AnsiColor;
import utils.ConsoleUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Option 1 : generateFile()
        // Option 2 : validateFile()
        // Option 3 : gradeFile()

        int option = 0;
        String filePath = "";
        boolean isEmptyArgs = true;

        if (args.length > 0) {
            option = Integer.parseInt(args[0]);
            filePath = args[1];
            isEmptyArgs = false;
        } else {
            // setup menu
            List<String> options = new ArrayList<>();
            options.add("generateFile");        // 1
            options.add("validateFile");        // 2
            options.add("gradeFile");           // 3
            String message = "What do you want to do?";
            String exit = "Exit";
            // run options menu
            do {
                // build UI header
                ConsoleUtils.buildUiHeader("PLUGIN : job7interview");
                // read option selected
                option = ConsoleUtils.showAndSelectIndex(options, message, exit);
            } while (option < 0 || option > 3);
        }

        switch (option) {
            case 0:
                ConsoleUtils.showMessageColor("Closing Plugin...", AnsiColor.CYAN);
                ConsoleUtils.readLineFromConsole("Press ENTER to continue");
                return;
            case 1:
                generateFile();
                break;
            case 2:
                if (isEmptyArgs) {
                    filePath = ConsoleUtils.readLineFromConsole("Type the full path to where the file is located: ");
                }
                validateFile(filePath);
                break;
            case 3:
                if (isEmptyArgs) {
                    filePath = ConsoleUtils.readLineFromConsole("Type the full path to where the file is located: ");
                }
                gradeFile(filePath);
                break;
            default:
                ConsoleUtils.showMessageColor("Invalid option.\nClosing Plugin...", AnsiColor.RED);
        }
    }

    private static void generateFile() {

        String fileName = "job7interview_model.txt";
        String filePath = "plugins/interviews/txt/" + fileName;

        String content = "How many years of experience do you have in IT?\n\n"
                + "Select your Degree (None, Bachelor, Master, PhD)\n\n"
                + "Which operating systems are you proficient in? (Windows, Linux) Note: Seperated by \",\"\n\n"
                + "Which programming or scripting languages are you proficient in? (Python, Java, C++, Bash, PowerShell) Note: Seperated by \",\"\n\n"
                + "How many years of experience do you have in penetration testing?\n\n"
                + "Do you have any of the following certifications? (OSCP, CEH, CISSP, GPEN, CISM) Note: Seperated by \",\"\n\n"
                + "Which penetration testing tools are you proficient in? (Metasploit, Burp Suite, Nmap, Wireshark, Nessus) Note: Seperated by \",\"\n\n"
                + "Are you familiar with the OWASP Top Ten vulnerabilities? (Yes or No)\n\n"
                + "How would you rate your analytical and problem-solving skills? (1-5)\n\n"
                + "Can you start working within the next month? (Yes or No)\n\n";

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(content);
            fileWriter.close();
            ConsoleUtils.showMessageColor("File model generated successfuly", AnsiColor.GREEN);
        } catch (IOException e) {
            ConsoleUtils.showMessageColor("An error occured while generating file model.", AnsiColor.RED);
            e.printStackTrace(); // TESTING
        }
    }

    private static void validateFile(String filePath) {
        System.out.println("To be implemented...");
    }

    private static void gradeFile(String filePath) {
        System.out.println("To be implemented...");
    }
}