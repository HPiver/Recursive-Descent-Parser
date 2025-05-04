import java.io.*;

public class SourceFile {
    private BufferedReader reader;

    public SourceFile(String filename) {
        try {
            FileReader fr = new FileReader("C:\\Users\\hliza\\Projects\\Term Project\\TestCases\\input1.txt");
            reader = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("Source file " + filename + " not found.");
        }
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Error closing file.");
        }
    }
}