import utils.AnsiColor;
import utils.ConsoleUtils;

public class job7InterviewGradingVisitor extends job7interviewBaseVisitor<Integer> {

    Integer totalPoints = 0;

    @Override
    public Integer visitQuestion1(job7interviewParser.Question1Context ctx) {
        System.out.println("Question 1: " + ctx.getText());
        String isJavaObjectOriented = ctx.answer1.getText();
        if (isJavaObjectOriented.equals("true")){
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
        return super.visitQuestion2(ctx);
    }

    @Override
    public Integer visitQuestion3(job7interviewParser.Question3Context ctx) {
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
