import java.io.IOException;


/**
 * Makes a PDF of bikes from input file.
 */
public class Main {

    private static final double INCH_TO_MM = 25.4;

    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {

        //numbers in inches
        int width = (int) (INCH_TO_MM * 75);
        int height = (int) (INCH_TO_MM * 42);
        int margin = (int) (INCH_TO_MM * 2);
        int gridStep = 1000; //this is in dollars

        Diagram d = new Diagram(Bike.readBikes("bikesInput.txt"), width, height, margin, gridStep);

        d.writePDF("price_distribution.pdf");
    }

}
