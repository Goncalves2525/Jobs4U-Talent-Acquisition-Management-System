grammar CandidateRequirementsGrammarValidation;

file: line+ EOF;

line: key ':' value NEWLINE;

key: ID;
value: TEXT;
NEWLINE: '\r'? '\n';

ID: [a-zA-Z]+;
TEXT: [a-zA-Z0-9 ]+;

WS: [ \t]+ -> skip;
