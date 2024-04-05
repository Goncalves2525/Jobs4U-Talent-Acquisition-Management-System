// Generated from C:/Users/paulo/Desktop/sem4pi-23-24-2nb1/antlr/test.g4 by ANTLR 4.13.1
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link testParser}.
 */
public interface testListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link testParser#prule}.
	 * @param ctx the parse tree
	 */
	void enterPrule(testParser.PruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link testParser#prule}.
	 * @param ctx the parse tree
	 */
	void exitPrule(testParser.PruleContext ctx);
}