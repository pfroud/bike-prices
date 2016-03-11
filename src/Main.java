import java.io.IOException;
import java.util.Vector;

/**
 * Makes a PDF of bikes from input file.
 * There is also Main_applet which is old and not on git.
 */
public class Main {

    private static final double INCH_TO_MM = 25.4;

    /**
     * Program entry point.
     * Opens a text file with information about and writes a PDF file showing range and gradiation of prices.
     */
    public static void main(String[] args) throws IOException {


        Vector<Integer> data = new Vector<>();
        data.add(2);
        data.add(5);
        data.add(6);
        data.add(1);
        Histogram hist = new Histogram(new Bike("test_model"), data);



        /*

        //the number is in inches
        int width = (int) (INCH_TO_MM * 75);
        int height = (int) (INCH_TO_MM * 37);
        int margin = (int) (INCH_TO_MM * 4);
        int gridStep = 500; //this is in dollars
        Diagram d = new Diagram(width, height, margin, gridStep);

        d.loadBikes("bikesInput.txt");

        d.addCustomRange(500, 10000); // $500 through $10,000
        d.addLegend(new Legend(1650, 10, 200, 90));
        d.addAnalysis(new Analysis(4)); //4 histogram bins

        d.writePDF("testing.pdf");

        */
    }

}
