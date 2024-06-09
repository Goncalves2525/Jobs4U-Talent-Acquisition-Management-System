// Generated from /Users/diogoespiritosanto/LAPR4/sem4pi-23-24-2nb1/antlr/CandidateRequirementsGrammarValidation.g4 by ANTLR 4.13.1
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
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(CandidateRequirementsGrammarValidationParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(CandidateRequirementsGrammarValidationParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(CandidateRequirementsGrammarValidationParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(CandidateRequirementsGrammarValidationParser.KeyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(CandidateRequirementsGrammarValidationParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CandidateRequirementsGrammarValidationParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(CandidateRequirementsGrammarValidationParser.ValueContext ctx);
}