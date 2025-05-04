/*Rule 01: PROGRAM -> program DECL_SEC begin STMT_SEC end; |
           program begin STMT_SEC end;
Rule 02: DECL_SEC -> DECL | DECL DECL_SEC
Rule 03: DECL -> ID_LIST : TYPE ;
Rule 04: ID_LIST -> ID | ID , ID_LIST
Rule 06: STMT_SEC -> STMT | STMT STMT_SEC
Rule 07: STMT -> ASSIGN | IFSTMT | WHILESTMT | INPUT | OUTPUT
Rule 08: ASSIGN -> ID := EXPR ;
Rule 09: IFSTMT -> if COMP then STMT_SEC end if ; |
if COMP then STMT_SEC else STMT_SEC end if ;
Rule 10: WHILESTMT -> while COMP loop STMT_SEC end loop ;
Rule 11: INPUT -> input ID_LIST;
Rule 12: OUTPUT -> output ID_LIST | output NUM;
Rule 13: EXPR -> FACTOR | FACTOR + EXPR | FACTOR - EXPR
Rule 14: FACTOR -> OPERAND | OPERAND * FACTOR | OPERAND / FACTOR
Rule 15: OPERAND -> NUM | ID | ( EXPR ) | FUNCALL
Rule 17: COMP -> ( OPERAND = OPERAND ) | ( OPERAND <> OPERAND ) |
( OPERAND > OPERAND ) | ( OPERAND < OPERAND )
Rule 19: FUNCALL -> call ID( ID_LIST ) ; */
import java.util.HashSet;
import java.util.Set;

public class Parser {
      
      private Set<String> symbolTable = new HashSet<>();
      private Token CurrentToken;
      Scanner scanner;
      boolean f = true, f1 = true, p = true;
      int count = 0;

      public Parser(Scanner scanner) {
            this.scanner = scanner;
            this.CurrentToken = scanner.nextToken();
        }

      private void Consume(Token.TokenType expectedType){
            if(CurrentToken.type == expectedType){
                 // System.out.println("CurrentToken: " + CurrentToken.type);
              CurrentToken = scanner.nextToken();
            }  
            else {
                throw new SyntaxErr("ERROR !! Expected " + expectedType + " in Line ", CurrentToken.line);
        }
      }
     
      public void Parse(){
      SourceFile sourceFile = new SourceFile("input.txt");
      scanner = new Scanner(sourceFile.getReader());
        CurrentToken = scanner.nextToken();
        ParseProgram();
        if(CurrentToken.type !=Token.TokenType.EOT){
            new SyntaxErr("ERROR !!  ", CurrentToken.line);
        }
      }

      //PROGRAM
      private void ParseProgram(){
            Consume(Token.TokenType.PROGRAM);
            System.out.println("PROGRAM");

            if(CurrentToken.type == Token.TokenType.IDENTIFIER){
                  ParseDeclSec();
            }

            Consume(Token.TokenType.BEGIN);
            ParseStmtSec();
            Consume(Token.TokenType.END);
            Consume(Token.TokenType.SEMICOLON);
      }

      //DECLARATION SECTION
      private void ParseDeclSec(){
            while(CurrentToken.type == Token.TokenType.IDENTIFIER){
                  System.out.println("DECL_SEC");
                  ParseDecl();
                 
            }
      }

       //STATEMENT SECTION
       private void ParseStmtSec(){
            while(isStmtStart(CurrentToken.type)){
                  System.out.println("STMT_SEC");
                  ParseStmt();
            }
      }

      private boolean isStmtStart(Token.TokenType type){
            return type == Token.TokenType.INPUT ||
                   type == Token.TokenType.OUTPUT ||
                   type == Token.TokenType.IDENTIFIER ||
                   type == Token.TokenType.IF ||
                   type == Token.TokenType.WHILE ||
                   type == Token.TokenType.LOOP;
      }


      //DECLARATION
      private void ParseDecl(){
            System.out.println("DECL");
            symbolTable.add(CurrentToken.lexeme);
            ParseIdList();
            Consume(Token.TokenType.COLON);
           
            if (CurrentToken.type == Token.TokenType.INT ||
                CurrentToken.type == Token.TokenType.FLOAT ||
                CurrentToken.type == Token.TokenType.DOUBLE) {
                Consume(CurrentToken.type);
            } else {
                new SyntaxErr("Expected a valid type after ':'", CurrentToken.line);
            }
        
            Consume(Token.TokenType.SEMICOLON);
            
      }

      //ID LIST
      private void  ParseIdList(){
        symbolTable.add(CurrentToken.lexeme);
        Consume(Token.TokenType.IDENTIFIER);
        System.out.println("ID_LIST");
    
           
            while (CurrentToken.type == Token.TokenType.COMMA) {
                Consume(Token.TokenType.COMMA);

               if(CurrentToken.type == Token.TokenType.IDENTIFIER){
                symbolTable.add(CurrentToken.lexeme);
                Consume(Token.TokenType.IDENTIFIER);
                System.out.println("ID_LIST");
               }
            }
      }

      //STATEMENT
      private void ParseStmt(){
       System.out.println("STMT");
        //INPUT
        if (CurrentToken.type == Token.TokenType.INPUT) {
            Consume(Token.TokenType.INPUT);
            System.out.println("INPUT");
            ParseIdList();
            Consume(Token.TokenType.SEMICOLON);
        }
        //OUTPUT
        else if (CurrentToken.type == Token.TokenType.OUTPUT) {
            System.out.println("OUTPUT");
            Consume(Token.TokenType.OUTPUT);

            if (CurrentToken.type == Token.TokenType.NUM) {
                Consume(Token.TokenType.NUM);
            } else {
                ParseIdList();
            }
            Consume(Token.TokenType.SEMICOLON);
         }
         //ASIGNMENT
        else if (CurrentToken.type == Token.TokenType.IDENTIFIER) {
            Consume(Token.TokenType.IDENTIFIER);
            Consume(Token.TokenType.ASSIGNMENT);
            System.out.println("ASSIGN");
            ParseExpr();
            Consume(Token.TokenType.SEMICOLON);
        }
        //IF
        else if (CurrentToken.type == Token.TokenType.IF) {
            System.out.println("IF_STMT");
            Consume(Token.TokenType.IF);
            Consume(Token.TokenType.LPAREN);
            ParseComp();
            Consume(Token.TokenType.RPAREN);
            Consume(Token.TokenType.THEN);
            ParseStmtSec();

            if (CurrentToken.type == Token.TokenType.ELSE) {
                Consume(Token.TokenType.ELSE);
                ParseStmtSec();
            }

            Consume(Token.TokenType.END);
            Consume(Token.TokenType.IF);
            Consume(Token.TokenType.SEMICOLON);
            
        }
        //WHILE
        else if (CurrentToken.type == Token.TokenType.WHILE) {
            System.out.println("WHILE_STMT");
            Consume(Token.TokenType.WHILE);
            Consume(Token.TokenType.LPAREN);
            ParseComp();
            Consume(Token.TokenType.RPAREN);
            Consume(Token.TokenType.LOOP); 
            ParseStmtSec();               
            Consume(Token.TokenType.END);
            Consume(Token.TokenType.LOOP); 
            Consume(Token.TokenType.SEMICOLON);
        }
        else {
            new SyntaxErr("Syntax error in statement: ", CurrentToken.line);
        }
      }

      private void ParseComp(){
            System.out.println("COMP");
            ParseOper();
            if(CurrentToken.type == Token.TokenType.LESS_THAN  ||
            CurrentToken.type == Token.TokenType.GREATER_THAN ||
            CurrentToken.type == Token.TokenType.LESS_THAN_EQUAL ||
            CurrentToken.type == Token.TokenType.GREATER_THAN_EQUAL ||
            CurrentToken.type == Token.TokenType.EQUALS ||
            CurrentToken.type == Token.TokenType.NOT_EQUALS) {

           
            Consume(CurrentToken.type); 
        } else {
            new SyntaxErr("ERROR !! no comparison operator at line ", CurrentToken.line);
            return; 
        }
        ParseOper();
        
      }

      //EXPRESSION
      private void ParseExpr(){
            System.out.println("EXPR");
            ParseFactor();
            while(CurrentToken.type == Token.TokenType.PLUS || CurrentToken.type == Token.TokenType.MINUS){
                  Consume(CurrentToken.type);
                  System.out.println("EXPR");
                  ParseFactor();
            }
      }

      //FACTOR
      private void ParseFactor(){
            System.out.println("FACTOR");
            ParseOper();
            while(CurrentToken.type == Token.TokenType.MULTIPLY || CurrentToken.type == Token.TokenType.DIVIDE){
                  Consume(CurrentToken.type);
                  System.out.println("FACTOR");
                  ParseOper();
            }
           
      }

      //OPERANDS
      private void ParseOper(){
           System.out.println("OPERAND");

           if (CurrentToken.type == Token.TokenType.IDENTIFIER) {
            if (!symbolTable.contains(CurrentToken.lexeme)) {
                new SyntaxErr("ERROR !! identifier not declared in Line ", CurrentToken.line);
                return;
            }
            Consume(CurrentToken.type);
        } 
        else if (CurrentToken.type == Token.TokenType.NUM) {
            Consume(CurrentToken.type); 
        }
        else {
            new SyntaxErr("ERROR!! Expected operand at line: ", CurrentToken.line);
        }
    }
}