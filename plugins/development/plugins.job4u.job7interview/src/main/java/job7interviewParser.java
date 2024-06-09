// Generated from C:/Users/rcaro/PI4/sem4pi-23-24-2nb1/plugins/development/plugins.job4u.job7interview/antlr/job7interview.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class job7interviewParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, BOOLEAN=12, SEPARATOR=13, DEGREE=14, PROGLANGUAGES=15, 
		PROGLANGUAGE=16, WORD=17, INTEGER=18, DECIMAL=19, DATE=20, TIME=21, ANSWER=22, 
		QUESTIONNUM=23, RESULTTAG=24, RESULTTAGSKIP=25, SLASH=26, WITH=27, NEWLINE=28, 
		WS=29;
	public static final int
		RULE_start = 0, RULE_header = 1, RULE_question1 = 2, RULE_question2 = 3, 
		RULE_question3 = 4, RULE_question4 = 5, RULE_question5 = 6, RULE_question6 = 7, 
		RULE_question7 = 8, RULE_question8 = 9, RULE_question9 = 10, RULE_question10 = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "header", "question1", "question2", "question3", "question4", 
			"question5", "question6", "question7", "question8", "question9", "question10"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Job 7 Interview Model'", "'Is Java an object-oriented programming language? (true or false)'", 
			"'How do you describe yourself in 5 words: (type each word separated by a semi-colon)'", 
			"'Enter one degree: (None; Bachelor; Master; PHD)'", "'Enter one or more programming languages you are proficient in: (java; javascript; python; c) (type each language separated by a semi-colon)'", 
			"'Enter the number of years of experience: (type as integer)'", "'Enter your salary expectations: (use only 2 decimal numbers)'", 
			"'On what specific date can you start working? (dd/mm/yyyy)'", "'How many overtime hours are you available to work per week? (hh:mm)'", 
			"'How capable do you feel to carry out the duties described in the job offer? [0-5]'", 
			"'Where are our headquarters? (type one word only)'", null, "'; '", null, 
			null, null, null, null, null, null, null, "'Answer:'", null, null, null, 
			"'/'", "'with'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"BOOLEAN", "SEPARATOR", "DEGREE", "PROGLANGUAGES", "PROGLANGUAGE", "WORD", 
			"INTEGER", "DECIMAL", "DATE", "TIME", "ANSWER", "QUESTIONNUM", "RESULTTAG", 
			"RESULTTAGSKIP", "SLASH", "WITH", "NEWLINE", "WS"
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
	public String getGrammarFileName() { return "job7interview.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public job7interviewParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public Question1Context question1() {
			return getRuleContext(Question1Context.class,0);
		}
		public Question2Context question2() {
			return getRuleContext(Question2Context.class,0);
		}
		public Question3Context question3() {
			return getRuleContext(Question3Context.class,0);
		}
		public Question4Context question4() {
			return getRuleContext(Question4Context.class,0);
		}
		public Question5Context question5() {
			return getRuleContext(Question5Context.class,0);
		}
		public Question6Context question6() {
			return getRuleContext(Question6Context.class,0);
		}
		public Question7Context question7() {
			return getRuleContext(Question7Context.class,0);
		}
		public Question8Context question8() {
			return getRuleContext(Question8Context.class,0);
		}
		public Question9Context question9() {
			return getRuleContext(Question9Context.class,0);
		}
		public Question10Context question10() {
			return getRuleContext(Question10Context.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			header();
			setState(25);
			question1();
			setState(26);
			question2();
			setState(27);
			question3();
			setState(28);
			question4();
			setState(29);
			question5();
			setState(30);
			question6();
			setState(31);
			question7();
			setState(32);
			question8();
			setState(33);
			question9();
			setState(34);
			question10();
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
	public static class HeaderContext extends ParserRuleContext {
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_header);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(T__0);
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
	public static class Question1Context extends ParserRuleContext {
		public Token answer1;
		public List<TerminalNode> BOOLEAN() { return getTokens(job7interviewParser.BOOLEAN); }
		public TerminalNode BOOLEAN(int i) {
			return getToken(job7interviewParser.BOOLEAN, i);
		}
		public Question1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question1Context question1() throws RecognitionException {
		Question1Context _localctx = new Question1Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_question1);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(T__1);
			setState(42);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(39);
					((Question1Context)_localctx).answer1 = match(BOOLEAN);
					}
					} 
				}
				setState(44);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
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
	public static class Question2Context extends ParserRuleContext {
		public Token answer2;
		public List<TerminalNode> SEPARATOR() { return getTokens(job7interviewParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(job7interviewParser.SEPARATOR, i);
		}
		public List<TerminalNode> WORD() { return getTokens(job7interviewParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(job7interviewParser.WORD, i);
		}
		public Question2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question2Context question2() throws RecognitionException {
		Question2Context _localctx = new Question2Context(_ctx, getState());
		enterRule(_localctx, 6, RULE_question2);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(T__2);
			setState(49);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(46);
					((Question2Context)_localctx).answer2 = match(WORD);
					}
					} 
				}
				setState(51);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				{
				setState(52);
				match(SEPARATOR);
				}
				break;
			}
			setState(58);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(55);
					match(WORD);
					}
					} 
				}
				setState(60);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(62);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(61);
				match(SEPARATOR);
				}
				break;
			}
			setState(67);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(64);
					match(WORD);
					}
					} 
				}
				setState(69);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(71);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(70);
				match(SEPARATOR);
				}
				break;
			}
			setState(76);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(73);
					match(WORD);
					}
					} 
				}
				setState(78);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(80);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEPARATOR) {
				{
				setState(79);
				match(SEPARATOR);
				}
			}

			setState(85);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(82);
					match(WORD);
					}
					} 
				}
				setState(87);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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
	public static class Question3Context extends ParserRuleContext {
		public Token answer3;
		public List<TerminalNode> DEGREE() { return getTokens(job7interviewParser.DEGREE); }
		public TerminalNode DEGREE(int i) {
			return getToken(job7interviewParser.DEGREE, i);
		}
		public Question3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion3(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question3Context question3() throws RecognitionException {
		Question3Context _localctx = new Question3Context(_ctx, getState());
		enterRule(_localctx, 8, RULE_question3);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(T__3);
			setState(92);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(89);
					((Question3Context)_localctx).answer3 = match(DEGREE);
					}
					} 
				}
				setState(94);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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
	public static class Question4Context extends ParserRuleContext {
		public Token answer4;
		public List<TerminalNode> PROGLANGUAGES() { return getTokens(job7interviewParser.PROGLANGUAGES); }
		public TerminalNode PROGLANGUAGES(int i) {
			return getToken(job7interviewParser.PROGLANGUAGES, i);
		}
		public Question4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion4(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion4(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question4Context question4() throws RecognitionException {
		Question4Context _localctx = new Question4Context(_ctx, getState());
		enterRule(_localctx, 10, RULE_question4);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__4);
			setState(99);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(96);
					((Question4Context)_localctx).answer4 = match(PROGLANGUAGES);
					}
					} 
				}
				setState(101);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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
	public static class Question5Context extends ParserRuleContext {
		public Token answer5;
		public List<TerminalNode> INTEGER() { return getTokens(job7interviewParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(job7interviewParser.INTEGER, i);
		}
		public Question5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question5; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion5(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion5(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion5(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question5Context question5() throws RecognitionException {
		Question5Context _localctx = new Question5Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_question5);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(T__5);
			setState(106);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(103);
					((Question5Context)_localctx).answer5 = match(INTEGER);
					}
					} 
				}
				setState(108);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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
	public static class Question6Context extends ParserRuleContext {
		public Token answer6;
		public List<TerminalNode> DECIMAL() { return getTokens(job7interviewParser.DECIMAL); }
		public TerminalNode DECIMAL(int i) {
			return getToken(job7interviewParser.DECIMAL, i);
		}
		public Question6Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question6; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion6(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion6(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion6(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question6Context question6() throws RecognitionException {
		Question6Context _localctx = new Question6Context(_ctx, getState());
		enterRule(_localctx, 14, RULE_question6);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(T__6);
			setState(113);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(110);
					((Question6Context)_localctx).answer6 = match(DECIMAL);
					}
					} 
				}
				setState(115);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
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
	public static class Question7Context extends ParserRuleContext {
		public Token answer7;
		public List<TerminalNode> DATE() { return getTokens(job7interviewParser.DATE); }
		public TerminalNode DATE(int i) {
			return getToken(job7interviewParser.DATE, i);
		}
		public Question7Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question7; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion7(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion7(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion7(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question7Context question7() throws RecognitionException {
		Question7Context _localctx = new Question7Context(_ctx, getState());
		enterRule(_localctx, 16, RULE_question7);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(T__7);
			setState(120);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(117);
					((Question7Context)_localctx).answer7 = match(DATE);
					}
					} 
				}
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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
	public static class Question8Context extends ParserRuleContext {
		public Token answer8;
		public List<TerminalNode> TIME() { return getTokens(job7interviewParser.TIME); }
		public TerminalNode TIME(int i) {
			return getToken(job7interviewParser.TIME, i);
		}
		public Question8Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question8; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion8(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion8(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion8(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question8Context question8() throws RecognitionException {
		Question8Context _localctx = new Question8Context(_ctx, getState());
		enterRule(_localctx, 18, RULE_question8);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(T__8);
			setState(127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(124);
					((Question8Context)_localctx).answer8 = match(TIME);
					}
					} 
				}
				setState(129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
	public static class Question9Context extends ParserRuleContext {
		public Token answer9;
		public List<TerminalNode> INTEGER() { return getTokens(job7interviewParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(job7interviewParser.INTEGER, i);
		}
		public Question9Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question9; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion9(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion9(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion9(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question9Context question9() throws RecognitionException {
		Question9Context _localctx = new Question9Context(_ctx, getState());
		enterRule(_localctx, 20, RULE_question9);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(T__9);
			setState(134);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(131);
					((Question9Context)_localctx).answer9 = match(INTEGER);
					}
					} 
				}
				setState(136);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
	public static class Question10Context extends ParserRuleContext {
		public Token answer10;
		public TerminalNode WORD() { return getToken(job7interviewParser.WORD, 0); }
		public Question10Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question10; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).enterQuestion10(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof job7interviewListener ) ((job7interviewListener)listener).exitQuestion10(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof job7interviewVisitor ) return ((job7interviewVisitor<? extends T>)visitor).visitQuestion10(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Question10Context question10() throws RecognitionException {
		Question10Context _localctx = new Question10Context(_ctx, getState());
		enterRule(_localctx, 22, RULE_question10);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(T__10);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WORD) {
				{
				setState(138);
				((Question10Context)_localctx).answer10 = match(WORD);
				}
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

	public static final String _serializedATN =
		"\u0004\u0001\u001d\u008e\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0005\u0002)\b\u0002"+
		"\n\u0002\f\u0002,\t\u0002\u0001\u0003\u0001\u0003\u0005\u00030\b\u0003"+
		"\n\u0003\f\u00033\t\u0003\u0001\u0003\u0003\u00036\b\u0003\u0001\u0003"+
		"\u0005\u00039\b\u0003\n\u0003\f\u0003<\t\u0003\u0001\u0003\u0003\u0003"+
		"?\b\u0003\u0001\u0003\u0005\u0003B\b\u0003\n\u0003\f\u0003E\t\u0003\u0001"+
		"\u0003\u0003\u0003H\b\u0003\u0001\u0003\u0005\u0003K\b\u0003\n\u0003\f"+
		"\u0003N\t\u0003\u0001\u0003\u0003\u0003Q\b\u0003\u0001\u0003\u0005\u0003"+
		"T\b\u0003\n\u0003\f\u0003W\t\u0003\u0001\u0004\u0001\u0004\u0005\u0004"+
		"[\b\u0004\n\u0004\f\u0004^\t\u0004\u0001\u0005\u0001\u0005\u0005\u0005"+
		"b\b\u0005\n\u0005\f\u0005e\t\u0005\u0001\u0006\u0001\u0006\u0005\u0006"+
		"i\b\u0006\n\u0006\f\u0006l\t\u0006\u0001\u0007\u0001\u0007\u0005\u0007"+
		"p\b\u0007\n\u0007\f\u0007s\t\u0007\u0001\b\u0001\b\u0005\bw\b\b\n\b\f"+
		"\bz\t\b\u0001\t\u0001\t\u0005\t~\b\t\n\t\f\t\u0081\t\t\u0001\n\u0001\n"+
		"\u0005\n\u0085\b\n\n\n\f\n\u0088\t\n\u0001\u000b\u0001\u000b\u0003\u000b"+
		"\u008c\b\u000b\u0001\u000b\r*1:CLU\\cjqx\u007f\u0086\u0000\f\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0000\u0093\u0000"+
		"\u0018\u0001\u0000\u0000\u0000\u0002$\u0001\u0000\u0000\u0000\u0004&\u0001"+
		"\u0000\u0000\u0000\u0006-\u0001\u0000\u0000\u0000\bX\u0001\u0000\u0000"+
		"\u0000\n_\u0001\u0000\u0000\u0000\ff\u0001\u0000\u0000\u0000\u000em\u0001"+
		"\u0000\u0000\u0000\u0010t\u0001\u0000\u0000\u0000\u0012{\u0001\u0000\u0000"+
		"\u0000\u0014\u0082\u0001\u0000\u0000\u0000\u0016\u0089\u0001\u0000\u0000"+
		"\u0000\u0018\u0019\u0003\u0002\u0001\u0000\u0019\u001a\u0003\u0004\u0002"+
		"\u0000\u001a\u001b\u0003\u0006\u0003\u0000\u001b\u001c\u0003\b\u0004\u0000"+
		"\u001c\u001d\u0003\n\u0005\u0000\u001d\u001e\u0003\f\u0006\u0000\u001e"+
		"\u001f\u0003\u000e\u0007\u0000\u001f \u0003\u0010\b\u0000 !\u0003\u0012"+
		"\t\u0000!\"\u0003\u0014\n\u0000\"#\u0003\u0016\u000b\u0000#\u0001\u0001"+
		"\u0000\u0000\u0000$%\u0005\u0001\u0000\u0000%\u0003\u0001\u0000\u0000"+
		"\u0000&*\u0005\u0002\u0000\u0000\')\u0005\f\u0000\u0000(\'\u0001\u0000"+
		"\u0000\u0000),\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000\u0000*(\u0001"+
		"\u0000\u0000\u0000+\u0005\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000"+
		"\u0000-1\u0005\u0003\u0000\u0000.0\u0005\u0011\u0000\u0000/.\u0001\u0000"+
		"\u0000\u000003\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u00001/\u0001"+
		"\u0000\u0000\u000025\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u0000"+
		"46\u0005\r\u0000\u000054\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u0000"+
		"6:\u0001\u0000\u0000\u000079\u0005\u0011\u0000\u000087\u0001\u0000\u0000"+
		"\u00009<\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000:8\u0001\u0000"+
		"\u0000\u0000;>\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000=?\u0005"+
		"\r\u0000\u0000>=\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?C\u0001"+
		"\u0000\u0000\u0000@B\u0005\u0011\u0000\u0000A@\u0001\u0000\u0000\u0000"+
		"BE\u0001\u0000\u0000\u0000CD\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000"+
		"\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000FH\u0005\r\u0000"+
		"\u0000GF\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000\u0000HL\u0001\u0000"+
		"\u0000\u0000IK\u0005\u0011\u0000\u0000JI\u0001\u0000\u0000\u0000KN\u0001"+
		"\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000"+
		"MP\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000OQ\u0005\r\u0000\u0000"+
		"PO\u0001\u0000\u0000\u0000PQ\u0001\u0000\u0000\u0000QU\u0001\u0000\u0000"+
		"\u0000RT\u0005\u0011\u0000\u0000SR\u0001\u0000\u0000\u0000TW\u0001\u0000"+
		"\u0000\u0000UV\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000V\u0007"+
		"\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000X\\\u0005\u0004\u0000"+
		"\u0000Y[\u0005\u000e\u0000\u0000ZY\u0001\u0000\u0000\u0000[^\u0001\u0000"+
		"\u0000\u0000\\]\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000]\t"+
		"\u0001\u0000\u0000\u0000^\\\u0001\u0000\u0000\u0000_c\u0005\u0005\u0000"+
		"\u0000`b\u0005\u000f\u0000\u0000a`\u0001\u0000\u0000\u0000be\u0001\u0000"+
		"\u0000\u0000cd\u0001\u0000\u0000\u0000ca\u0001\u0000\u0000\u0000d\u000b"+
		"\u0001\u0000\u0000\u0000ec\u0001\u0000\u0000\u0000fj\u0005\u0006\u0000"+
		"\u0000gi\u0005\u0012\u0000\u0000hg\u0001\u0000\u0000\u0000il\u0001\u0000"+
		"\u0000\u0000jk\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000k\r\u0001"+
		"\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000mq\u0005\u0007\u0000\u0000"+
		"np\u0005\u0013\u0000\u0000on\u0001\u0000\u0000\u0000ps\u0001\u0000\u0000"+
		"\u0000qr\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000r\u000f\u0001"+
		"\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000tx\u0005\b\u0000\u0000uw\u0005"+
		"\u0014\u0000\u0000vu\u0001\u0000\u0000\u0000wz\u0001\u0000\u0000\u0000"+
		"xy\u0001\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000y\u0011\u0001\u0000"+
		"\u0000\u0000zx\u0001\u0000\u0000\u0000{\u007f\u0005\t\u0000\u0000|~\u0005"+
		"\u0015\u0000\u0000}|\u0001\u0000\u0000\u0000~\u0081\u0001\u0000\u0000"+
		"\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000"+
		"\u0080\u0013\u0001\u0000\u0000\u0000\u0081\u007f\u0001\u0000\u0000\u0000"+
		"\u0082\u0086\u0005\n\u0000\u0000\u0083\u0085\u0005\u0012\u0000\u0000\u0084"+
		"\u0083\u0001\u0000\u0000\u0000\u0085\u0088\u0001\u0000\u0000\u0000\u0086"+
		"\u0087\u0001\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0087"+
		"\u0015\u0001\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0089"+
		"\u008b\u0005\u000b\u0000\u0000\u008a\u008c\u0005\u0011\u0000\u0000\u008b"+
		"\u008a\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c"+
		"\u0017\u0001\u0000\u0000\u0000\u0012*15:>CGLPU\\cjqx\u007f\u0086\u008b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}