grammar scratch;

// Parser Rules
expression: term ((PLUS | MINUS) term)*;

term: factor ((TIMES | DIVIDE) factor)*;

factor: NUMBER | '(' expression ')';

// Lexer Rules
PLUS: '+';
MINUS: '-';
TIMES: '*';
DIVIDE: '/';

NUMBER: DIGIT+ ('.' DIGIT+)?;

fragment DIGIT: [0-9];

WS: [ \t\r\n]+ -> skip;