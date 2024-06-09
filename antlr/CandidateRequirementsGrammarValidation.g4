grammar CandidateRequirementsGrammarValidation;

@header {
    package gen.antlr;
}

file: (qaPair NEWLINE?)+ EOF;

qaPair: question NEWLINE answer NEWLINE?;

question: TEXT;

answer: TEXT;

TEXT: ~[\r\n]+;
NEWLINE: '\r'? '\n';

WS: [ \t]+ -> skip;
