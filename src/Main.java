import java.io.IOException;

/**
 * Makes a PDF of bikes from input file.
 * There is also Main_applet which is old and not on git.
 */
public class Main {

    static final double INCH_TO_MM = 25.4;

    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {

        //the number is in inches
        int width = (int) (INCH_TO_MM * 75);
        int height = (int) (INCH_TO_MM * 37);
        int margin = (int) (INCH_TO_MM * 4);
        int gridStep = 500; //this is in dollars
        Diagram d = new Diagram(width, height, margin, gridStep);


        d.setRangeOverride(500, 10000);


        d.readFromFile("bikesInput.txt");

        d.writeToFile("testing.pdf");

    }


}
