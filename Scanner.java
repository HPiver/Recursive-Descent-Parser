import java.io.*;


public class Scanner {
    //trackingCurrentLines and char + getting an input
    private char CurrentChar;
    private int CurrentLine = 1;
    private BufferedReader inFile;
    
   
 
    public Scanner(BufferedReader inFile){
        this.inFile = inFile;
        advance();
       
    }


    private void advance(){
        try{
            int i =this.inFile.read();
            if(i == -1){
                CurrentChar = '\u0000';
            }
            else{
                CurrentChar = (char) i;

            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }


    public Token nextToken(){
        SkipWhiteSpace();
       
        if(CurrentChar == '\u0000'){
         return new Token(Token.TokenType.EOT, "EOF", CurrentLine, "EOF");
        }
        if(Character.isLetter(CurrentChar) || CurrentChar == '_'){
         return ScanId();
        }
        else if(Character.isDigit(CurrentChar)){
         return ScanNumbers();
        }
        else{
         return ScanOperators();
        }
        
    }


    private void SkipWhiteSpace(){
        while(Character.isWhitespace(CurrentChar)){
            if (CurrentChar == '\n'){
                CurrentLine++;
            }
            advance();
        }
    }

    //lexical errors
    
    private Token ScanId(){
        //define identifiers
        StringBuilder identifier = new StringBuilder();

        if(Character.isLetter(CurrentChar)|| CurrentChar == '_'){
            identifier.append(CurrentChar);
            advance();
        }
        else{
            return new Token(Token.TokenType.ERROR, "Illegal Symbol: " + CurrentChar, CurrentLine, String.valueOf(CurrentChar));
        }
        
        while(Character.isLetterOrDigit(CurrentChar) || CurrentChar == '_'){
            identifier.append(CurrentChar);
            advance();
        }
        
        String id = identifier.toString();
        switch(id){
            case "program":
                return new Token(Token.TokenType.PROGRAM, id, CurrentLine, id);
            case "begin":
                return new Token(Token.TokenType.BEGIN, id, CurrentLine, id);
            case "end":
                return new Token(Token.TokenType.END, id, CurrentLine, id);
            case "if":
                return new Token(Token.TokenType.IF, id, CurrentLine, id);
            case "then":
                return new Token(Token.TokenType.THEN, id, CurrentLine, id);
            case "else":
                return new Token(Token.TokenType.ELSE, id, CurrentLine, id);
            case "input":
                return new Token(Token.TokenType.INPUT, id, CurrentLine, id);
            case "output":
                return new Token(Token.TokenType.OUTPUT, id, CurrentLine, id);
            case "int":
                return new Token(Token.TokenType.INT, id, CurrentLine, id);
            case "double":
                return new Token(Token.TokenType.DOUBLE, id, CurrentLine, id);
            case "float":
                return new Token(Token.TokenType.FLOAT, id, CurrentLine, id);
            case "while":
                return new Token(Token.TokenType.WHILE, id, CurrentLine, id);
            case "loop":
                return new Token(Token.TokenType.LOOP, id, CurrentLine, id);
            default:
                return new Token(Token.TokenType.IDENTIFIER, id, CurrentLine, id);
        }
    }   


    private Token ScanNumbers(){
        StringBuilder number = new StringBuilder();
        boolean isFloat = true;

        //negatives
        if (CurrentChar == '-') {
            number.append(CurrentChar);
            advance();
        }
    
       
        if (!Character.isDigit(CurrentChar)) {
            return new Token(Token.TokenType.ERROR, "Illegal Number: expected digit after '-' at line " + CurrentLine, CurrentLine, String.valueOf(CurrentChar));
        }
    
        
        while (Character.isDigit(CurrentChar)) {
            number.append(CurrentChar);
            advance();
        }
    
        //floats
        if (CurrentChar == '.') {
            isFloat = true;
            number.append(CurrentChar);
            advance();
    
            if (!Character.isDigit(CurrentChar)) {
                return new Token(Token.TokenType.ERROR, "Illegal float format at line ", CurrentLine, number.toString());
            }
    
            while (Character.isDigit(CurrentChar)) {
                number.append(CurrentChar);
                advance();
            }
        }
        
        String numStr = number.toString();

        if (isFloat) {
            return new Token(Token.TokenType.NUM, number.toString(), CurrentLine, numStr);
        } else {
            return new Token(Token.TokenType.NUM, number.toString(), CurrentLine, numStr);
        }
    }


    private Token ScanOperators(){
        switch(CurrentChar){
            case ':':
                advance(); 

                if (CurrentChar == '=') {
                    advance();
                    return new Token(Token.TokenType.ASSIGNMENT, ":=", CurrentLine, ":=");
                }
                    return new Token(Token.TokenType.COLON, ":", CurrentLine, ":");
            case '<':
                advance();

                if (CurrentChar == '>') {
                    advance();
                    return new Token(Token.TokenType.NOT_EQUALS, "<>", CurrentLine, "<>");
                }
                return new Token(Token.TokenType.LESS_THAN, "<", CurrentLine, "<");
            case '>':
                advance();
                return new Token(Token.TokenType.GREATER_THAN, ">", CurrentLine, ">");
            case '=':
                advance();
                return new Token(Token.TokenType.EQUALS, "=", CurrentLine, "=");
            case '+':
                advance();
                return new Token(Token.TokenType.PLUS, "+", CurrentLine,  "+");
            case '-':
                advance();
                return new Token(Token.TokenType.MINUS, "-", CurrentLine, "-");
            case '*':
                advance();
                return new Token(Token.TokenType.MULTIPLY, "*", CurrentLine, "*");
            case '/':
                advance();
                return new Token(Token.TokenType.DIVIDE, "/", CurrentLine, "/");
            case '(':
                advance();
                return new Token(Token.TokenType.LPAREN, "(", CurrentLine, "(");
            case ')':
                advance();
                return new Token(Token.TokenType.RPAREN, ")", CurrentLine, ")");
            case ',':
                advance();
                return new Token(Token.TokenType.COMMA, ",", CurrentLine, ","); 
            case ';':
                advance();
                return new Token(Token.TokenType.SEMICOLON, ";", CurrentLine, ";");  
            default:
                String errorChar = Character.toString(CurrentChar);
                advance();
                return new Token(Token.TokenType.ERROR, "Illegal character: ", CurrentLine, errorChar);
        }
    }
}
