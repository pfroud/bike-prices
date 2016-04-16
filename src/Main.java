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

        //the number is in inches. ridiculously large because that's what I started with. it's vector anyway...
        int width = (int) (INCH_TO_MM * 75);
        int height = (int) (INCH_TO_MM * 42);
        int margin = (int) (INCH_TO_MM * 4);
        int gridStep = 500; //this is in dollars

        Diagram d = new Diagram(Bike.readBikes("bikesInput.txt"), width, height, margin, gridStep);

        d.addCustomRange(500, 10000); // $500 through $10,000
        d.addLegend(new Legend(1650, 10, 200, 90)); // x, y, width, height
        d.addAnalysis(new Analysis(4, 30, height - 50, 70)); // 4 histogram bins then x, y

        d.writePDF("testing.pdf");

    }

}
