// Generated from /Users/diogoespiritosanto/LAPR4/sem4pi-23-24-2nb1/antlr/CandidateRequirementsGrammarValidation.g4 by ANTLR 4.13.1

    package antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class CandidateRequirementsGrammarValidationParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TEXT=11, NEWLINE=12, WS=13;
	public static final int
		RULE_file = 0, RULE_qaPair = 1, RULE_question = 2, RULE_answer = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "qaPair", "question", "answer"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'How many years of experience do you have in IT?'", "'Select your Degree (None, Bachelor, Master, PhD)'", 
			"'Which operating systems are you proficient in? (Windows, Linux) Note: Seperated by \",\"'", 
			"'Which programming or scripting languages are you proficient in? (Python, Java, C++, Bash, PowerShell) Note: Seperated by \",\"'", 
			"'How many years of experience do you have in penetration testing?'", 
			"'Do you have any of the following certifications? (OSCP, CEH, CISSP, GPEN, CISM) Note: Seperated by \",\"'", 
			"'Which penetration testing tools are you proficient in? (Metasploit, Burp Suite, Nmap, Wireshark, Nessus) Note: Seperated by \",\"'", 
			"'Are you familiar with the OWASP Top Ten vulnerabilities? (Yes or No)'", 
			"'How would you rate your analytical and problem-solving skills? (1-5)'", 
			"'Can you start working within the next month? (Yes or No)'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "TEXT", 
			"NEWLINE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CandidateRequirementsGrammarValidation.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CandidateRequirementsGrammarValidationParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(CandidateRequirementsGrammarValidationParser.EOF, 0); }
		public List<QaPairContext> qaPair() {
			return getRuleContexts(QaPairContext.class);
		}
		public QaPairContext qaPair(int i) {
			return getRuleContext(QaPairContext.class,i);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(CandidateRequirementsGrammarValidationParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(CandidateRequirementsGrammarValidationParser.NEWLINE, i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener) ((antlr.CandidateRequirementsGrammarValidationListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gen.antlr.CandidateRequirementsGrammarValidationVisitor) return ((gen.antlr.CandidateRequirementsGrammarValidationVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(8);
				qaPair();
				setState(10);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEWLINE) {
					{
					setState(9);
					match(NEWLINE);
					}
				}

				}
				}
				setState(14); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 2046L) != 0) );
			setState(16);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QaPairContext extends ParserRuleContext {
		public QuestionContext question() {
			return getRuleContext(QuestionContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(CandidateRequirementsGrammarValidationParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(CandidateRequirementsGrammarValidationParser.NEWLINE, i);
		}
		public AnswerContext answer() {
			return getRuleContext(AnswerContext.class,0);
		}
		public QaPairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qaPair; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).enterQaPair(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).exitQaPair(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gen.antlr.CandidateRequirementsGrammarValidationVisitor) return ((gen.antlr.CandidateRequirementsGrammarValidationVisitor<? extends T>)visitor).visitQaPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QaPairContext qaPair() throws RecognitionException {
		QaPairContext _localctx = new QaPairContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_qaPair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			question();
			setState(19);
			match(NEWLINE);
			setState(20);
			answer();
			setState(22);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(21);
				match(NEWLINE);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionContext extends ParserRuleContext {
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gen.antlr.CandidateRequirementsGrammarValidationVisitor ) return ((gen.antlr.CandidateRequirementsGrammarValidationVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_question);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2046L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AnswerContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(CandidateRequirementsGrammarValidationParser.TEXT, 0); }
		public AnswerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_answer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).enterAnswer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof antlr.CandidateRequirementsGrammarValidationListener ) ((antlr.CandidateRequirementsGrammarValidationListener)listener).exitAnswer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof gen.antlr.CandidateRequirementsGrammarValidationVisitor ) return ((gen.antlr.CandidateRequirementsGrammarValidationVisitor<? extends T>)visitor).visitAnswer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AnswerContext answer() throws RecognitionException {
		AnswerContext _localctx = new AnswerContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_answer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\r\u001d\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0003"+
		"\u0000\u000b\b\u0000\u0004\u0000\r\b\u0000\u000b\u0000\f\u0000\u000e\u0001"+
		"\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003"+
		"\u0001\u0017\b\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0000\u0000\u0004\u0000\u0002\u0004\u0006\u0000\u0001\u0001\u0000"+
		"\u0001\n\u001b\u0000\f\u0001\u0000\u0000\u0000\u0002\u0012\u0001\u0000"+
		"\u0000\u0000\u0004\u0018\u0001\u0000\u0000\u0000\u0006\u001a\u0001\u0000"+
		"\u0000\u0000\b\n\u0003\u0002\u0001\u0000\t\u000b\u0005\f\u0000\u0000\n"+
		"\t\u0001\u0000\u0000\u0000\n\u000b\u0001\u0000\u0000\u0000\u000b\r\u0001"+
		"\u0000\u0000\u0000\f\b\u0001\u0000\u0000\u0000\r\u000e\u0001\u0000\u0000"+
		"\u0000\u000e\f\u0001\u0000\u0000\u0000\u000e\u000f\u0001\u0000\u0000\u0000"+
		"\u000f\u0010\u0001\u0000\u0000\u0000\u0010\u0011\u0005\u0000\u0000\u0001"+
		"\u0011\u0001\u0001\u0000\u0000\u0000\u0012\u0013\u0003\u0004\u0002\u0000"+
		"\u0013\u0014\u0005\f\u0000\u0000\u0014\u0016\u0003\u0006\u0003\u0000\u0015"+
		"\u0017\u0005\f\u0000\u0000\u0016\u0015\u0001\u0000\u0000\u0000\u0016\u0017"+
		"\u0001\u0000\u0000\u0000\u0017\u0003\u0001\u0000\u0000\u0000\u0018\u0019"+
		"\u0007\u0000\u0000\u0000\u0019\u0005\u0001\u0000\u0000\u0000\u001a\u001b"+
		"\u0005\u000b\u0000\u0000\u001b\u0007\u0001\u0000\u0000\u0000\u0003\n\u000e"+
		"\u0016";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}