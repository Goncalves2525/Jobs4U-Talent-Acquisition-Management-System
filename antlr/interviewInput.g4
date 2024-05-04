grammar interviewInput;

// Start rule
start: question1 question2 question3 question4 question5 question6 question7 question8 question9;

// Rule for the first question
question1: 'Is Java an object-oriented programming language? (True or False)' answer1=BOOLEAN;

// Rule for the second question
question2: 'How do you describe yourself in 5 words:' answer2=WORD WORD WORD WORD WORD;

// Rule for the third question
question3: 'Select one degree (None; Bachelor; Master; PHD)' answer3=DEGREE;

// Rule for the fourth question
question4: 'Select one or more programming languages you are proficient in (java; javascript; python; c)' answer4=language+;

// Rule for the fifth question
question5: 'Enter the number of years of experience (integer)' answer5=INTEGER;

// Rule for the sixth question
question6: 'Enter your salary expectations (decimal)' answer6=DECIMAL;

// Rule for the seventh question
question7: 'On what specific date can you start working? (dd/mm/yyyy)' answer7=DATE;

// Rule for the eighth question
question8: 'How many overtime hours are you available to work per week? (hh:mm)' answer8=TIME;

// Rule for the ninth question
question9: 'How capable do you feel to carry out the duties described in the job offer? [0-5]' answer9=CAPABILITY;

// Boolean rule for true or false answers
BOOLEAN: 'true' | 'false';

// Word rule for five words in the second question
WORD: [a-zA-Z]+;

// Rule for degree selection
DEGREE: 'None' | 'Bachelor' | 'Master' | 'PHD';

// Rule for programming language selection
language: 'java' | 'javascript' | 'python' | 'c';

// Rule for integer numbers
INTEGER: [0-9]+;

// Rule for decimal numbers
DECIMAL: [0-9]+ '.' [0-9]+;

// Rule for date format dd/mm/yyyy
DATE: [0-3]?[0-9] '/' [0-1]?[0-9] '/' [0-9]+;

// Rule for time format hh:mm
TIME: [0-2]?[0-9] ':' [0-5][0-9];

// Rule for capability rating between 0 and 5
CAPABILITY: [0-5];