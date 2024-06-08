import utils.AnsiColor;
import utils.ConsoleUtils;

public class job7InterviewGradingVisitor extends job7interviewBaseVisitor<Integer> {

    Integer totalPoints = 0;

    @Override
    public Integer visitQuestion1(job7interviewParser.Question1Context ctx) {
        System.out.println("Question 1: " + ctx.children.get(0));
        System.out.println("Answer 1: " + ctx.answer1.getText());
        String isJavaObjectOriented = ctx.answer1.getText();

        if (isJavaObjectOriented.equals("true")) {
            ConsoleUtils.showMessageColor("Correct: 10/10", AnsiColor.GREEN);
            totalPoints += 10;
        } else {
            ConsoleUtils.showMessageColor("Wrong: 0/10", AnsiColor.RED);
            totalPoints += 0;
        }
        return super.visitQuestion1(ctx);
    }

    @Override
    public Integer visitQuestion2(job7interviewParser.Question2Context ctx) {

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

        if(finalFiveWords.contains("STRONG")) { answerPoints += 2; }
        if(finalFiveWords.contains("DEDICATED")) { answerPoints += 2; }
        if(finalFiveWords.contains("LUCKY")) { answerPoints += 2; }
        if(finalFiveWords.contains("RESILIENT")) { answerPoints += 2; }
        if(finalFiveWords.contains("COMMITTED")) { answerPoints += 2; }

        ConsoleUtils.showMessageColor("Correct: " + answerPoints + "/10", AnsiColor.GREEN);
        ConsoleUtils.showMessageColor("Wrong: " + (10-answerPoints) + "/10", AnsiColor.RED);

        totalPoints += answerPoints;

        return super.visitQuestion2(ctx);
    }

    @Override
    public Integer visitQuestion3(job7interviewParser.Question3Context ctx) {
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
            ConsoleUtils.showMessageColor("Correct: " + answerPoints + "/10", AnsiColor.GREEN);
        } else {
            ConsoleUtils.showMessageColor("Wrong: " + answerPoints + "/10", AnsiColor.RED);
        }

        totalPoints += answerPoints;

        return super.visitQuestion3(ctx);
    }

    @Override
    public Integer visitQuestion4(job7interviewParser.Question4Context ctx) {
        return super.visitQuestion4(ctx);
    }

    @Override
    public Integer visitQuestion5(job7interviewParser.Question5Context ctx) {
        return super.visitQuestion5(ctx);
    }

    @Override
    public Integer visitQuestion6(job7interviewParser.Question6Context ctx) {
        return super.visitQuestion6(ctx);
    }

    @Override
    public Integer visitQuestion7(job7interviewParser.Question7Context ctx) {
        return super.visitQuestion7(ctx);
    }

    @Override
    public Integer visitQuestion8(job7interviewParser.Question8Context ctx) {
        return super.visitQuestion8(ctx);
    }

    @Override
    public Integer visitQuestion9(job7interviewParser.Question9Context ctx) {
        return super.visitQuestion9(ctx);
    }

    @Override
    public Integer visitQuestion10(job7interviewParser.Question10Context ctx) {
        return super.visitQuestion10(ctx);
    }
}
