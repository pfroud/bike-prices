import java.io.IOException;

/**
 * Makes a PDF of bikes from input file.
 * There is also Main_applet which is old and not on git.
 */
public class Main {

    static final String FILE_INPUT = "bikesInput.txt"; //location of text file containing bike information
    static final String FILE_OUTPUT = "testing.pdf"; //location of PDF file to write diagram to


    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {

        Diagram d = new Diagram(1900, 940, 100, 500);
        d.setRangeOverride(500, 10000);
        d.readFromFile(FILE_INPUT);

        d.writeToFile(FILE_OUTPUT);

    }



}
