
public class Main{ 

    public static void main(String[] args) {
        try{
            //change path to test case here and in SourceFile
            SourceFile sourceFile = new SourceFile("C:\\Users\\hliza\\Projects\\Term Project\\TestCases\\input1.txt");

            Scanner scanner = new Scanner(sourceFile.getReader());
            Parser parser = new Parser(scanner);
            
            parser.Parse();
        }
        catch(Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}