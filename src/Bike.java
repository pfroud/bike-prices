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
        Scanner fileScan = null;

        // open file
        try {
            fileScan = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found.");
            System.exit(1);
        }

        Vector<Bike> allBikes = new Vector<>();

        // go through file and add bikes to the vector
        Bike tempBike;
        while (true) {
            tempBike = readBikeModel(fileScan);
            if (tempBike == null) break; // readBikeModel() stops when it reads "end"
            allBikes.add(tempBike);

            fileScan.nextLine(); //skip a blank line
        }

        Collections.sort(allBikes);
        return allBikes;
    }

    //@formatter:off (formatter kills indentation in this doc comment)
    /**
     * Reads and returns a single bike model and its versions.
     * Steps:
     *
     * (1) Reads the header, which contains the model name and the number of versions.
     *     Example: "Specialized_Diverge 7"
     *
     * (2) Does three for loops to read:
     *     (a) version names
     *     (b) version prices
     *     (c) version materials
     *
     * @param sc scanner on the input text file
     * @return a Bike object containing all of the version names, prices, and materials
     */
    //@formatter:on
    private static Bike readBikeModel(Scanner sc) {
        sc.useDelimiter(": |\r?\n|\r"); // use newline and ":" as delimiter

        //read model name
        String name = sc.next();
        if (name.equals("end")) return null;
        Bike bike = new Bike(name);

        // read number of versions
        int numModels = sc.nextInt();
        bike.numModels = numModels;

        sc.useDelimiter(", |\r?\n|\r"); // only use newline as delimiter

        // read version names
        for (int i = 0; i < numModels; i++) {
            bike.versionNames.add(sc.next());
        }

        // read version prices
        int currentPrice;
        for (int i = 0; i < numModels; i++) {
            currentPrice = sc.nextInt();

            //update min and max price
            if (currentPrice > priceMin) priceMin = currentPrice;
            if (currentPrice < priceMax) priceMax = currentPrice;

            bike.addPrice(currentPrice);
        }

        // read version materials
        for (int i = 0; i < numModels; i++) {
            bike.versionCarbons.add(Carbon.parseString(sc.next()));
        }

        // read version groupsets
        for (int i = 0; i < numModels; i++) {
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

    // these should maybe be all private
    private Vector<String> versionNames = new Vector<>();
    Vector<Integer> versionPrices = new Vector<>();
    Vector<Carbon> versionCarbons = new Vector<>();
    Vector<Groupset> versionGroupsets = new Vector<>();

    int minPrice = 999999, maxPrice = 0; // price of most and least expensive version of the model

    public int compareTo(Bike other) {
        return Integer.compare(Collections.min(versionPrices), Collections.min(other.versionPrices));
    }


    /**
     * Constructs new Bike given the name of the model.
     *
     * @param name the name of the model to construct.
     */
    public Bike(String name) {
        this.modelName = name;

        /*
//        sometimes nice to shorten long words
        modelName = modelName.replace("Specialized", "Spec.");
        modelName = modelName.replace("Cannondale", "Can.");
        modelName = modelName.replace("Advanced", "Adv.");
        */

    }

    /**
     * Gives human-readable representation of a bike with all the versions.
     *
     * @return a human-readable representation of the bike model.
     */
    public String toString() {
        NumberFormat format = NumberFormat.getInstance();
        ListIterator<String> names = versionNames.listIterator();
        ListIterator<Integer> prices = versionPrices.listIterator();
        String out = modelName + "\n-----------------------\n";

        while (names.hasNext()) {
            out += names.next() + ": $" + format.format(prices.next()) + "\n";
        }
        return out;
    }

    /**
     * Used to add a price to the vector of prices.
     * prices isn't private but you should still use this.
     *
     * @param c the price of the version to add.
     */
    private void addPrice(int c) {
        versionPrices.add(c);

        if (c > maxPrice) maxPrice = c;
        if (c < minPrice) minPrice = c;
    }

    /**
     * Prints a CSV row of: model name, absolute price range, relative price range, name of least expensive version,
     * price of least expensive version, name of most expensive version, price of most expensive version.
     *
     * When writing CSV files, use printHeader() first.
     */
    void printRange() {
        NumberFormat numFmt = NumberFormat.getNumberInstance();
        System.out.printf("%s\t$%s\t%.2fx\t%s\t$%s\t%s\t$%s\n",
                modelName.replace('_', ' '),
                numFmt.format(maxPrice - minPrice), (double) (maxPrice) / minPrice,
                versionNames.firstElement(), numFmt.format(versionPrices.firstElement()),
                versionNames.lastElement(), numFmt.format(versionPrices.lastElement()));
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
     * Returns data for input to a histogram.
     * Partitions the bikes by price into numHistogramBins bins. Each element of the vector
     * is the number of versions in that price range.
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
                    // there is probably a better way to do this
                    bins.set(currentBin - 1, bins.get(currentBin - 1) + 1); // need currentBin-1 bc index starts at 0
                    break;
                }
            }
        }
        return bins;
    }

    // endregion instance stuff

}

