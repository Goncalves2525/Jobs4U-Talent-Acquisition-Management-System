// Generated from C:/Users/rcaro/PI4/sem4pi-23-24-2nb1/plugins/development/plugins.job4u.job7interview/antlr/job7interview.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link job7interviewParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface job7interviewVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(job7interviewParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion1(job7interviewParser.Question1Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion2(job7interviewParser.Question2Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion3(job7interviewParser.Question3Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion4(job7interviewParser.Question4Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion5(job7interviewParser.Question5Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question6}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion6(job7interviewParser.Question6Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion7(job7interviewParser.Question7Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question8}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion8(job7interviewParser.Question8Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#question9}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion9(job7interviewParser.Question9Context ctx);
	/**
	 * Visit a parse tree produced by {@link job7interviewParser#language}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLanguage(job7interviewParser.LanguageContext ctx);
}