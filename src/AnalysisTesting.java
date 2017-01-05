import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Used this to test the Analysis and Histogram classes.
 * It only draws histograms, no diagram.
 */
public class AnalysisTesting extends JPanel {

    private static Analysis a;

    public static void main(String[] args) {
        a = new Analysis(4, 10, 110, 100);
        a.init(Bike.readBikes("bikesInput.txt"));

        // http://php.scripts.psu.edu/djh300/cmpsc221/notes-graphics-intro.php
        AnalysisTesting panel       = new AnalysisTesting();
        JFrame          application = new JFrame();
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(panel);
        application.setSize(1000, 1200);
        application.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        a.draw(g);
    }

    /**
     * Prints how many bikes there are for each brand.
     *
     * @param bikes a vector of bikes to print counts for
     */
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


    /**
     * At multuple price points, pritns what percentage of bikes have a carbon-fiber frame.
     *
     * @param bikes vector of bikes to look at
     */
    static private void findCarbonPercents(Vector<Bike> bikes) {
        double versionsTotal, versionsFullCarbon;
        int    currPrice;


        Vector<Integer> priceData   = new Vector<>(32);
        Vector<Double>  percentData = new Vector<>(32);

        for (int price = 0; price < 16000; price += 500) {
            priceData.add(price);

            versionsTotal = versionsFullCarbon = 0;

            for (Bike bike : bikes) {
                for (int i = 0; i < bike.numModels; i++) {
                    currPrice = bike.prices.get(i);
                    if (currPrice < price) continue;
                    versionsTotal++;
                    if (bike.carbons.get(i) == Carbon.ALL) versionsFullCarbon++;
                }
            }

            System.out.println("at price " + price + ": " + versionsTotal + " versions total, " + versionsFullCarbon +
                    " versions with full carbon = " + (versionsFullCarbon / versionsTotal));

            percentData.add(versionsFullCarbon / versionsTotal);

        }

        System.out.println(priceData);
        System.out.println(percentData);

    }


}
