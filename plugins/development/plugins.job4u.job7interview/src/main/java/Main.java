//import job7interviewParser.job7interviewLexer;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import utils.AnsiColor;
import utils.ConsoleUtils;

import java.io.File;
import java.io.FileInputStream;
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

        StringBuilder content = new StringBuilder();

        content.append("Job 7 Interview Model\n\n");
        content.append("#1 Is Java an object-oriented programming language? (True or False)\n" +
                "Answer:\n\n");
        content.append("#2 How do you describe yourself in 5 words: (type each word separated by a semi-colon, with no spaces)\n" +
                "Answer:\n\n");
        content.append("#3 Enter one degree: (None; Bachelor; Master; PHD)\n" +
                "Answer:\n\n");
        content.append("#4 Enter one or more programming languages you are proficient in: (java; javascript; python; c) (type each language separated by a semi-colon, with no spaces)\n" +
                "Answer:\n\n");
        content.append("#5 Enter the number of years of experience: (type one integer)\n" +
                "Answer:\n\n");
        content.append("#6 Enter your salary expectations: (use only 2 decimal numbers)\n" +
                "Answer:\n\n");
        content.append("#7 On what specific date can you start working? (dd/mm/yyyy)\n" +
                "Answer:\n\n");
        content.append("#8 How many overtime hours are you available to work per week? (hh:mm)\n" +
                "Answer:\n\n");
        content.append("#9 How capable do you feel to carry out the duties described in the job offer? [0-5]\n" +
                "Answer:\n\n");
        content.append("#10 Where are our headquarters? (type one word only)\n" +
                "Answer:\n\n");

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(content.toString());
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
        try {
            FileInputStream file = new FileInputStream(filePath);
            job7interviewLexer lexer = new job7interviewLexer(CharStreams.fromStream(file));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            job7interviewParser parser = new job7interviewParser(tokens);
            ParseTree tree = parser.start();
            job7InterviewGradingVisitor visitor = new job7InterviewGradingVisitor();
            int grade = visitor.visit(tree);
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                if(grade > 70){
                    fileWriter.append("\n\n#RESULT: APPROVED with " + grade + "/100");
                }else{
                    fileWriter.append("\n\n#RESULT: REJECTED with " + grade + "/100");
                }
                fileWriter.close();
                ConsoleUtils.showMessageColor("Appended result to file", AnsiColor.GREEN);
            } catch (IOException e) {
                ConsoleUtils.showMessageColor("An error occurred while appending result to file", AnsiColor.RED);
                e.printStackTrace(); // TESTING
            }

        } catch (Exception e) {
            ConsoleUtils.showMessageColor("An error occurred while reading the file.", AnsiColor.RED);
            e.printStackTrace(); // TESTING
        }
    }
}