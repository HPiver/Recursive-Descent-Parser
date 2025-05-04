
public class SyntaxErr extends RuntimeException {

    public SyntaxErr(String message, int line) {
        System.out.println(message + line);
        System.exit(1);
    }
}