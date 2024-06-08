grammar job7interview;

// Start rule
start:
    header
    question1
    question2
    question3
    question4
    question5
    question6
    question7
    question8
    question9
    question10;

// Rule for the header
header: 'Job 7 Interview Model';

// Rule for the first question
question1: 'Is Java an object-oriented programming language? (true or false)' answer1=BOOLEAN*?;

// Rule for the second question
question2: 'How do you describe yourself in 5 words: (type each word separated by a semi-colon)' answer2=WORD*? SEPARATOR? WORD*? SEPARATOR? WORD*? SEPARATOR? WORD*? SEPARATOR? WORD*?;

// Rule for the third question
question3: 'Enter one degree: (None; Bachelor; Master; PHD)' answer3=DEGREE*?;

// Rule for the fourth question
question4: 'Enter one or more programming languages you are proficient in: (java; javascript; python; c) (type each language separated by a semi-colon)' answer4=PROGLANGUAGES*?;

// Rule for the fifth question
question5: 'Enter the number of years of experience: (type as integer)' answer5=INTEGER*?;

// Rule for the sixth question
question6: 'Enter your salary expectations: (use only 2 decimal numbers)' answer6=DECIMAL*?;

// Rule for the seventh question
question7: 'On what specific date can you start working? (dd/mm/yyyy)' answer7=DATE*?;

// Rule for the eighth question
question8: 'How many overtime hours are you available to work per week? (hh:mm)' answer8=TIME*?;

// Rule for the ninth question
question9: 'How capable do you feel to carry out the duties described in the job offer? [0-5]' answer9=INTEGER*?;

// Rule for the tenth question
question10: 'Where are our headquarters? (type one word only)' answer10=WORD*?;

// Boolean rule for true or false answers
BOOLEAN: 'true' | 'false';

// Rule for separator
SEPARATOR: '; ';

// Rule for degree selection
DEGREE: 'None' | 'Bachelor' | 'Master' | 'PHD';

// Rule for several programming languages
PROGLANGUAGES: (PROGLANGUAGE SEPARATOR?)+;

// Rule for programming language selection
PROGLANGUAGE: 'java' | 'javascript' | 'python' | 'c';

// Word rule
WORD: [a-zA-Z]+;

// Rule for integer numbers
INTEGER: [0-9]+;

// Rule for decimal numbers
DECIMAL: [0-9]+ [.|,] [0-9]+;

// Rule for date format dd/mm/yyyy
DATE: [0-3]?[0-9] '/' [0-1]?[0-9] '/' [0-9]+;

// Rule for time format hh:mm
TIME: [0-2]?[0-9] ':' [0-5][0-9];

// Rule for '\nAnswer:' start of answer line
ANSWER: 'Answer:' -> skip;

// Rule for #(number) start of question line
QUESTIONNUM: '#'INTEGER -> skip;

// Rule for a newline
NEWLINE : [\r\n]+ -> skip;

// Rule for text spaces
WS: [ \t\r]+ -> skip ;