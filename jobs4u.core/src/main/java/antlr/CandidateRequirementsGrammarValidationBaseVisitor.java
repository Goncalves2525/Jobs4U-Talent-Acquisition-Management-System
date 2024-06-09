// Generated from /Users/diogoespiritosanto/LAPR4/sem4pi-23-24-2nb1/antlr/CandidateRequirementsGrammarValidation.g4 by ANTLR 4.13.1

    package antlr;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link gen.antlr.CandidateRequirementsGrammarValidationVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class CandidateRequirementsGrammarValidationBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements gen.antlr.CandidateRequirementsGrammarValidationVisitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitFile(CandidateRequirementsGrammarValidationParser.FileContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitQaPair(CandidateRequirementsGrammarValidationParser.QaPairContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitQuestion(CandidateRequirementsGrammarValidationParser.QuestionContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitAnswer(CandidateRequirementsGrammarValidationParser.AnswerContext ctx) { return visitChildren(ctx); }
}