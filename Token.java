public class Token {

    public String value;
    public int line;
    public TokenType type;
    public String lexeme;
    
    public enum TokenType{
        //define token types
        PROGRAM, BEGIN, END, IF, THEN, ELSE, INPUT, OUTPUT, INT, WHILE, LOOP, FLOAT, DOUBLE,
        ASSIGNMENT, LESS_THAN, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN_EQUAL, EQUALS, NOT_EQUALS, PLUS, MINUS,
        MULTIPLY, DIVIDE,SEMICOLON, COMMA, COLON, IDENTIFIER, NUM, EOT, ERROR, EOF, LPAREN, RPAREN
    }
    
    
 public Token(TokenType type, String value, int line, String lexeme){
        this.type = type;
        this.value = value;
        this.line = line;
        this.lexeme = lexeme;
       
    }
}
    
    
    
    
    
    