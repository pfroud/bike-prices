import java.io.IOException;
import java.util.Vector;

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

        findCarbonPercents(Bike.readBikes("bikesInput.txt"));
        System.exit(0);

        //the number is in inches. ridiculously large because that's what I started with. it's vector anyway...
        int width = (int) (INCH_TO_MM * 75);
        int height = (int) (INCH_TO_MM * 42);
        int margin = (int) (INCH_TO_MM * 4);
        int gridStep = 500; //this is in dollars

        Diagram d = new Diagram(Bike.readBikes("bikesInput.txt"), width, height, margin, gridStep);


        d.addCustomRange(500, 10000); // $500 through $10,000
        d.addLegend(new Legend(1650, 10, 200, 90)); // x, y, width, height
        d.addAnalysis(new Analysis(3, 30, height - 50, 70)); // 4 histogram bins then x, y

        d.writePDF("testing_3bins.pdf");

    }


    static private void findCarbonPercents(Vector<Bike> bikes) {
        double versionsTotal = 0, versionsFullCarbon = 0;
        int currPrice;


        Vector<Integer> priceData = new Vector<>(32);
        Vector<Double> percentData = new Vector<>(32);

        for (int price = 0; price < 16000; price += 500) {
            priceData.add(price);

            versionsTotal = versionsFullCarbon = 0;

            for (Bike bike : bikes) {
                for (int i = 0; i < bike.numModels; i++) {
                    currPrice = bike.versionPrices.get(i);
                    if (currPrice < price) continue;
                    versionsTotal++;
                    if (bike.versionCarbons.get(i) == Carbon.ALL) versionsFullCarbon++;
                }
            }

            System.out.println("at price " + price + ": " + versionsTotal + " versions total, " + versionsFullCarbon + " versions with full carbon = "+ (versionsFullCarbon/versionsTotal));

            percentData.add( versionsFullCarbon / versionsTotal);

        }

        System.out.println(priceData);
        System.out.println(percentData);

    }

}
