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

//        printCounts(Bike.readBikes("new_test_input.txt"));
//        System.exit(0);

        //the number is in inches. ridiculously large because that's what I started with. it's vector anyway...
        int width = (int) (INCH_TO_MM * 75); //was 75
        int height = (int) (INCH_TO_MM * 42);
        int margin = (int) (INCH_TO_MM * 2);
        int gridStep = 1000; //this is in dollars

        Diagram d = new Diagram(Bike.readBikes("bikesInput.txt"), width, height, margin, gridStep);


//        d.addCustomRange(500, 2000); // $500 through $2,000
        d.addLegend(new Legend(1650, 15)); // x, y
        //d.addAnalysis(new Analysis(3, 30, height - 50, 70)); // 4 histogram bins thenimp x, y


        d.writePDF("working_output.pdf");

    }

    private static void printCounts(Vector<Bike> bikes) {
        int cannondale = 0, giant = 0, spec = 0, trek = 0;

        String name;
        for (Bike b : bikes) {
            name = b.modelName.split(" ")[0];
            switch (name) {
                case "Cannondale":
                    cannondale += b.numModels;
                    break;
                case "Giant":
                    giant += b.numModels;
                    break;
                case "Trek":
                    trek += b.numModels;
                    break;
                case "Specialized":
                    spec += b.numModels;
                    break;
                default:
                    System.err.println("I don't know what " + name + "is!");
            }
        }

        System.out.println("cannondale: " + cannondale);
        System.out.println("giant: " + giant);
        System.out.println("spec: " + spec);
        System.out.println("trek: " + trek);
        System.out.println("total: " + (cannondale + giant + spec + trek));
    }


    static private void findCarbonPercents(Vector<Bike> bikes) {
        double versionsTotal, versionsFullCarbon;
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

            System.out.println("at price " + price + ": " + versionsTotal + " versions total, " + versionsFullCarbon + " versions with full carbon = " + (versionsFullCarbon / versionsTotal));

            percentData.add(versionsFullCarbon / versionsTotal);

        }

        System.out.println(priceData);
        System.out.println(percentData);

    }

}
