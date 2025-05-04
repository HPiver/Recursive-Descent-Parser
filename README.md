# About
Implemented a scanner and a recursive descent parser for a small programming language. The program will take an input and the output will be written in the provided language. In addition to parsing the input programs, the program must generate the proper errors when encountered.

## Grammar
Rule 01: PROGRAM -> program DECL_SEC begin STMT_SEC end; | program begin STMT_SEC end;  
Rule 02: DECL_SEC -> DECL | DECL DECL_SEC  
Rule 03: DECL -> ID_LIST : TYPE ;  
Rule 04: ID_LIST -> ID | ID , ID_LIST  
Rule 05: ID -> (_ | a | b | ... | z | A | ... | Z) (_ | a | b | ... | z | A | ... | Z | 0 | 1 | ... | 9)*  
Rule 06: STMT_SEC -> STMT | STMT STMT_SEC  
Rule 07: STMT -> ASSIGN | IFSTMT | WHILESTMT | INPUT | OUTPUT  
Rule 08: ASSIGN -> ID := EXPR ;  
Rule 09: IFSTMT -> if COMP then STMT_SEC end if ; | if COMP then STMT_SEC else STMT_SEC end if ;  
Rule 10: WHILESTMT -> while COMP loop STMT_SEC end loop ;  
Rule 11: INPUT -> input ID_LIST;  
Rule 12: OUTPUT -> output ID_LIST | output NUM;  
Rule 13: EXPR -> FACTOR | FACTOR + EXPR | FACTOR - EXPR  
Rule 14: FACTOR -> OPERAND | OPERAND * FACTOR | OPERAND / FACTOR  
Rule 15: OPERAND -> NUM | ID | ( EXPR ) | FUNCALL  
Rule 16: NUM -> (0 | 1 | ... | 9)+[.(0 | 1 | ... | 9)+]  
Rule 17: COMP -> ( OPERAND = OPERAND ) | ( OPERAND <> OPERAND ) | ( OPERAND > OPERAND ) | ( OPERAND < OPERAND )  
Rule 18: TYPE -> int | float | double  
Rule 19: FUNCALL -> call ID( ID_LIST ) ; 

## Lexemes
• Reserved words: program, begin, end, if, then, else, input, output, int, while, loop.  
• Operators: assignment (:=), less than (<), greater than (>), equals (=), not equals (<>),
plus (+), minus (-) , multiply (*), divide (/) and parentheses.  
• The ‘;’ is also used to terminate statements and the ‘,’ and the ‘:’ are used when
declaring variables.  
• Identifiers: start with a letter or an ‘_’ followed by any number of letters, digits or
underscores.  
• Numbers: Either integer numbers (max 10 digits), or floating point numbers (max 10
digits).  
