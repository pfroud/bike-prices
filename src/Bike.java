import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Vector;

/**
 * A model of bike, made up of versions of the model.
 * For example, a model is "Specialized Diverge" and a version is "Elite A1".
 */
public class Bike implements Comparable<Bike> {

    // region statics

    private static int priceMin, priceMax; // maximum and minimum prices of versions of this model

    /**
     * Opens the input file and reads all bikes in the file.
     *
     * @param filename name of text file with bike info
     */
    static Vector<Bike> readBikes(String filename) {
        Scanner fileScan = null; //Scanner to read input text file

        // open file
        try {
            fileScan = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        }

        Vector<Bike> allBikes = new Vector<>();

        Bike tempBike;

        //go through input text file
        while (true) {
            tempBike = readOneBike(fileScan);
            if (tempBike == null) break;
            allBikes.add(tempBike);

            fileScan.nextLine(); //skip a blank line

        }

        Collections.sort(allBikes);
        return allBikes;
    }

    //@formatter:off (formatter kills indentation in this doc comment)
    /**
     * Reads a bike model and its versions, then returns a Bike object.
     * Steps:
     *
     * (1) Reads the header, which contains the model name and number of versions.
     *     Example: "Specialized_Diverge 7"
     *
     * (2) Does three for loops to read:
     *     (a) version names
     *     (b) version prices
     *     (c) version materials
     *
     * @param sc scanner on the input text file
     * @return Bike object containing all of the version names, prices, and materials
     */
    //@formatter:on
    private static Bike readOneBike(Scanner sc) {
        sc.useDelimiter(": |\n");
        String name = sc.next();
        if (name.equals("end")) return null;
        Bike bike = new Bike(name); //read model name
        int numModels_ = sc.nextInt();
        bike.numModels = numModels_;

        sc.useDelimiter(", |\n");

        String temp;

        // add version names
        for (int i = 0; i < numModels_; i++) {
            bike.versionNames.add(sc.next());
        }

        // add version prices
        int currentPrice;
        for (int i = 0; i < numModels_; i++) {
            currentPrice = sc.nextInt();

            //update min and max price
            if (currentPrice > priceMin) priceMin = currentPrice;
            if (currentPrice < priceMax) priceMax = currentPrice;

            bike.addPrice(currentPrice);
        }

        // add version materials
        for (int i = 0; i < numModels_; i++) {
            bike.versionCarbons.add(Carbon.parseString(sc.next()));
        }

        for (int i = 0; i < numModels_; i++) {
            bike.versionGroupsets.add(Groupset.parseString(sc.next()));
        }

        return bike;
    }

    static int getPriceMin() {
        return priceMin;
    }

    static int getPriceMax() {
        return priceMax;
    }

    // endregion statics

    // region instance stuff

    String modelName;
    int numModels;

    //TODO make these private?
    Vector<String> versionNames = new Vector<>();
    Vector<Integer> versionPrices = new Vector<>();
    Vector<Carbon> versionCarbons = new Vector<>();
    Vector<Groupset> versionGroupsets = new Vector<>();

    // why do I have this in both instance and statis??
    int minPrice = 999999, maxPrice = 0; // most and least expensive version of the model

    public int compareTo(Bike other) {
        return Integer.compare(Collections.min(versionPrices), Collections.min(other.versionPrices));
    }


    /**
     * Constructs new Bike given the name of the model.
     *
     * @param modelName the name of the model to construct.
     */
    public Bike(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Gives human-readable representation of a bike.
     *
     * @return a human-readable representation of the bike model.
     */
    public String toString() {
        NumberFormat format = NumberFormat.getInstance();
        ListIterator<String> names = versionNames.listIterator();
        ListIterator<Integer> prices = versionPrices.listIterator();
        String out = modelName; /* + "\n-----------------------\n";

        while (names.hasNext()) {
            out += names.next() + ": $" + format.format(prices.next()) + "\n";
        }*/
        return out;
    }

    /**
     * Used to add a price to the vector of prices.
     * prices isn't private but you should still use this.
     *
     * @param c the price of the version to add.
     */
    void addPrice(int c) {
        versionPrices.add(c);

        if (c > maxPrice) maxPrice = c;
        if (c < minPrice) minPrice = c;
    }

    /**
     * Prints a CSV row of model name, absolute price range, relative price range, least expensive version name,
     * least expensive version price, most expensive version name, most expensive version price.
     * Use printHeader() first.
     *
     * Example: "Specialized_Diverge	$7400	7.73x	A1	Carbon DI2"
     */
    void printRange() {
        NumberFormat numForm = NumberFormat.getNumberInstance();
        System.out.printf("%s\t$%s\t%.2fx\t%s\t$%s\t%s\t$%s\n",
                modelName.replace('_', ' '), numForm.format(maxPrice - minPrice), (double) (maxPrice) / minPrice,
                versionNames.firstElement(), numForm.format(versionPrices.firstElement()),
                versionNames.lastElement(), numForm.format(versionPrices.lastElement()));
    }

    /**
     * Prints the header for the CSV populated by printRange().
     */
    static void printHeader() {
        System.out.println("Model\tAbsolute price range\tRelative price range\t" +
                "Least expensive version\tLeast expensive version price\t" +
                "Most expensive version\tMost expensive version price");
    }

    /**
     * Divides the bikes by price into numHistogramBins bins. Each int is the number of versions in that price range.
     *
     * Example: "Specialized_Diverge: [5, 1, 1]"
     */
    Vector<Integer> getHistogramData(int numHistogramBins) {
        Vector<Integer> bins = new Vector<>(numHistogramBins);
        for (int i = 0; i < numHistogramBins; i++) {
            bins.add(i, 0);
        }

        double binWidth = (double) (maxPrice - minPrice) / numHistogramBins; // width, in dollars, of each bin
        double priceCutoff; //max price for a version to be in a bin

        //iterate over versions
        for (Integer currentPrice : versionPrices) {

            //iterate over bins
            for (int currentBin = 1; currentBin <= numHistogramBins; currentBin++) {
                priceCutoff = minPrice + (binWidth * currentBin);

                if (currentPrice <= priceCutoff) {
                    // it seems like this could be done better
                    bins.set(currentBin - 1, bins.get(currentBin - 1) + 1); // need currentBin-1 bc index starts at 0
                    break;
                }
            }
        }
        return bins;
    }

    // endregion instance stuff

}

