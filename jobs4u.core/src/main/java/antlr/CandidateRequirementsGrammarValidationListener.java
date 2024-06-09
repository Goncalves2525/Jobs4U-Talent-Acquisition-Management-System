// Generated from /Users/diogoespiritosanto/LAPR4/sem4pi-23-24-2nb1/antlr/CandidateRequirementsGrammarValidation.g4 by ANTLR 4.13.1

    package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CandidateRequirementsGrammarValidationParser}.
 */
public interface CandidateRequirementsGrammarValidationListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(CandidateRequirementsGrammarValidationParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(CandidateRequirementsGrammarValidationParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#qaPair}.
	 * @param ctx the parse tree
	 */
	void enterQaPair(CandidateRequirementsGrammarValidationParser.QaPairContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#qaPair}.
	 * @param ctx the parse tree
	 */
	void exitQaPair(CandidateRequirementsGrammarValidationParser.QaPairContext ctx);
	/**
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(CandidateRequirementsGrammarValidationParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(CandidateRequirementsGrammarValidationParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(CandidateRequirementsGrammarValidationParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(CandidateRequirementsGrammarValidationParser.AnswerContext ctx);
}