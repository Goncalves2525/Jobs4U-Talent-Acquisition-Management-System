import utils.AnsiColor;
import utils.ConsoleUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class job7InterviewGradingVisitor extends job7interviewBaseVisitor<Integer> {

    Integer totalPoints = 0;

    @Override
    public Integer visitQuestion1(job7interviewParser.Question1Context ctx) {

        if(ctx.answer1 == null) {
            System.out.println("Question 1: " + ctx.children.get(0));
            System.out.println("Answer 1: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 1: " + ctx.children.get(0));
            System.out.println("Answer 1: " + ctx.answer1.getText());
            String isJavaObjectOriented = ctx.answer1.getText();

            if (isJavaObjectOriented.equals("true")) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            }
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion2(job7interviewParser.Question2Context ctx) {

        if(ctx.answer2 == null) {
            System.out.println("Question 2: " + ctx.children.get(0));
            System.out.println("Answer 2: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 2: " + ctx.children.get(0));
            System.out.print("Answer 2: ");
            StringBuilder describedInFiveWords = new StringBuilder();
            for (int i = 1; i < ctx.children.size(); i++) {
                describedInFiveWords.append(ctx.children.get(i).getText().toUpperCase());
                System.out.print(ctx.children.get(i));
            }
            System.out.print("\n");

            int answerPoints = 0;
            String finalFiveWords = describedInFiveWords.toString();

            if (finalFiveWords.contains("STRONG")) {
                answerPoints += 2;
            }
            if (finalFiveWords.contains("DEDICATED")) {
                answerPoints += 2;
            }
            if (finalFiveWords.contains("LUCKY")) {
                answerPoints += 2;
            }
            if (finalFiveWords.contains("RESILIENT")) {
                answerPoints += 2;
            }
            if (finalFiveWords.contains("COMMITTED")) {
                answerPoints += 2;
            }

            if(answerPoints >= 6) {
                ConsoleUtils.showMessageColor("GRADE: " + answerPoints + "/10", AnsiColor.GREEN);
            } else if (answerPoints >= 2) {
                ConsoleUtils.showMessageColor("GRADE: " + answerPoints + "/10", AnsiColor.YELLOW);
            } else {
                ConsoleUtils.showMessageColor("GRADE: " + answerPoints + "/10", AnsiColor.RED);
            }

            totalPoints += answerPoints;
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion3(job7interviewParser.Question3Context ctx) {

        if(ctx.answer3 == null) {
            System.out.println("Question 3: " + ctx.children.get(0));
            System.out.println("Answer 3: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 3: " + ctx.children.get(0));
            System.out.println("Answer 3: " + ctx.answer3.getText());
            String degree = ctx.answer3.getText().toUpperCase();

            int answerPoints = 0;

            switch (degree) {
                case ("NONE"):
                    answerPoints = 0;
                    break;
                case ("BACHELOR"):
                    answerPoints = 4;
                    break;
                case ("PHD"):
                    answerPoints = 6;
                    break;
                case ("MASTER"):
                    answerPoints = 10;
                    break;
                default:
                    answerPoints = -1;
            }

            if (answerPoints > 0) {
                ConsoleUtils.showMessageColor("GRADE: " + answerPoints + "/10", AnsiColor.GREEN);
            } else {
                ConsoleUtils.showMessageColor("GRADE: " + answerPoints + "/10", AnsiColor.RED);
            }

            totalPoints += answerPoints;
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion4(job7interviewParser.Question4Context ctx) {

        if(ctx.answer4 == null) {
            System.out.println("Question 4: " + ctx.children.get(0));
            System.out.println("Answer 4: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 4: " + ctx.children.get(0));
            System.out.print("Answer 4: ");
            StringBuilder progLangs = new StringBuilder();
            for (int i = 1; i < ctx.children.size(); i++) {
                progLangs.append(ctx.children.get(i).getText().toUpperCase());
                System.out.print(ctx.children.get(i));
            }
            System.out.print("\n");

            int answerPoints = 0;
            String finalProgLangs = progLangs.toString();

            if (finalProgLangs.contains("JAVA")) {
                answerPoints += 4;
            }
            if (finalProgLangs.contains("JAVASCRIPT")) {
                answerPoints += 1;
            }
            if (finalProgLangs.contains("PYTHON")) {
                answerPoints += 3;
            }
            if (finalProgLangs.contains("C")) {
                answerPoints += 2;
            }

            ConsoleUtils.showMessageColor("GRADE: " + answerPoints + "/10", AnsiColor.GREEN);

            totalPoints += answerPoints;
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion5(job7interviewParser.Question5Context ctx) {

        if(ctx.answer5 == null) {
            System.out.println("Question 5: " + ctx.children.get(0));
            System.out.println("Answer 5: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 5: " + ctx.children.get(0));
            System.out.println("Answer 5: " + ctx.answer5.getText());
            int yearsOfExperience = Integer.parseInt(ctx.answer5.getText());

            if (yearsOfExperience >= 10) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else if (yearsOfExperience >= 5) {
                ConsoleUtils.showMessageColor("GRADE: 5/10", AnsiColor.GREEN);
                totalPoints += 5;
            } else if (yearsOfExperience >= 2) {
                ConsoleUtils.showMessageColor("GRADE: 3/10", AnsiColor.YELLOW);
                totalPoints += 3;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            }
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion6(job7interviewParser.Question6Context ctx) {

        if(ctx.answer6 == null) {
            System.out.println("Question 6: " + ctx.children.get(0));
            System.out.println("Answer 6: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 6: " + ctx.children.get(0));
            System.out.println("Answer 6: " + ctx.answer6.getText());
            double salaryExpectation = Double.parseDouble(ctx.answer6.getText());

            if (salaryExpectation > 2500.00) {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            } else if (salaryExpectation > 1900.00) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 3/10", AnsiColor.YELLOW);
                totalPoints += 3;
            }
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion7(job7interviewParser.Question7Context ctx) {

        if(ctx.answer7 == null) {
            System.out.println("Question 7: " + ctx.children.get(0));
            System.out.println("Answer 7: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 7: " + ctx.children.get(0));
            System.out.println("Answer 7: " + ctx.answer7.getText());
            ;

            String dateString = ctx.answer7.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = null;

            try {
                date = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid date format: " + e.getMessage());
            }

            if (date != null && date.isAfter(LocalDate.now().plusDays(30))) {
                ConsoleUtils.showMessageColor("GRADE: 4/10", AnsiColor.YELLOW);
                totalPoints += 4;
            } else if (date != null && date.isAfter(LocalDate.now().plusDays(15))) {
                ConsoleUtils.showMessageColor("GRADE: 8/10", AnsiColor.GREEN);
                totalPoints += 8;
            } else if (date != null && date.isAfter(LocalDate.now())) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            }
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion8(job7interviewParser.Question8Context ctx) {

        if(ctx.answer8 == null) {
            System.out.println("Question 8: " + ctx.children.get(0));
            System.out.println("Answer 8: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 8: " + ctx.children.get(0));
            System.out.println("Answer 8: " + ctx.answer8.getText());
            ;

            String timeString = ctx.answer8.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = null;

            try {
                time = LocalTime.parse(timeString, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Invalid time format: " + e.getMessage());
            }

            if (time != null && time.isAfter(LocalTime.of(0, 0).plusHours(10))) {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            } else if (time != null && time.isAfter(LocalTime.of(0, 0).plusHours(5))) {
                ConsoleUtils.showMessageColor("GRADE: 5/10", AnsiColor.YELLOW);
                totalPoints += 5;
            } else if (time != null && time.isAfter(LocalTime.of(0, 0))) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            }
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion9(job7interviewParser.Question9Context ctx) {

        if(ctx.answer9 == null) {
            System.out.println("Question 9: " + ctx.children.get(0));
            System.out.println("Answer 9: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 9: " + ctx.children.get(0));
            System.out.println("Answer 9: " + ctx.answer9.getText());
            int capability = Integer.parseInt(ctx.answer9.getText());

            if (capability > 5) {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            } else if (capability >= 4) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else if (capability >= 2) {
                ConsoleUtils.showMessageColor("GRADE: 3/10", AnsiColor.YELLOW);
                totalPoints += 3;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            }
        }

        return totalPoints;
    }

    @Override
    public Integer visitQuestion10(job7interviewParser.Question10Context ctx) {

        if(ctx.answer10 == null) {
            System.out.println("Question 10: " + ctx.children.get(0));
            System.out.println("Answer 10: ");
            ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
            totalPoints += 0;
        } else {
            System.out.println("Question 10: " + ctx.children.get(0));
            System.out.println("Answer 10: " + ctx.answer10.getText());
            String hq = ctx.answer10.getText().toUpperCase();

            if (hq.equals("AARHUS")) {
                ConsoleUtils.showMessageColor("GRADE: 10/10", AnsiColor.GREEN);
                totalPoints += 10;
            } else if (hq.equals("DENMARK")) {
                ConsoleUtils.showMessageColor("GRADE: 5/10", AnsiColor.YELLOW);
                totalPoints += 5;
            } else {
                ConsoleUtils.showMessageColor("GRADE: 0/10", AnsiColor.RED);
                totalPoints += 0;
            }
        }

        return totalPoints;
    }
}
