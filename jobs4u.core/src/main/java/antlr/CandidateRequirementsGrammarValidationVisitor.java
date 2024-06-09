// Generated from /Users/diogoespiritosanto/LAPR4/sem4pi-23-24-2nb1/antlr/CandidateRequirementsGrammarValidation.g4 by ANTLR 4.13.1

    package gen.antlr;

import antlr.CandidateRequirementsGrammarValidationParser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CandidateRequirementsGrammarValidationParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CandidateRequirementsGrammarValidationVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(CandidateRequirementsGrammarValidationParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#qaPair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQaPair(CandidateRequirementsGrammarValidationParser.QaPairContext ctx);
	/**
	 * Visit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(CandidateRequirementsGrammarValidationParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer(CandidateRequirementsGrammarValidationParser.AnswerContext ctx);

}