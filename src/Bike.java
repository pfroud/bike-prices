import java.io.File;
import java.io.FileNotFoundException;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Vector;

/**
 * A model of bike, made up of versions of the model.
 * For example, a model is "Specialized Diverge" and a version is "Elite A1".
 */
public class Bike {

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

        //go through input text file
        while (fileScan.hasNextLine()) {
            allBikes.add(readOneBike(fileScan));

            fileScan.nextLine(); //skip a blank line

            //this works but is terrible
            if (fileScan.hasNextLine()) {
                fileScan.nextLine();
            } else {
                break;
            }
        }
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
     * @param fileScan scanner on the input text file
     * @return Bike object containing all of the version names, prices, and materials
     */
    //@formatter:on
    private static Bike readOneBike(Scanner fileScan) {
        // separate scanner for the header (model name and number of models)
        Scanner headerScan = new Scanner(fileScan.nextLine());
        Bike bike = new Bike(headerScan.next()); //read model name
        int numModels = headerScan.nextInt();
        bike.numModels = numModels;
        headerScan.close();

        // add version names
        for (int i = 0; i < numModels; i++) {
            bike.versionNames.add(fileScan.nextLine());
        }

        // add version prices
        int currentPrice;
        for (int i = 0; i < numModels; i++) {
            currentPrice = fileScan.nextInt();

            //update min and max price
            if (currentPrice > priceMin) priceMin = currentPrice;
            if (currentPrice < priceMax) priceMax = currentPrice;

            bike.addPrice(currentPrice);
        }

        // add version materials
        for (int i = 0; i < numModels; i++) {
            bike.versionCarbons.add(Carbon.parseString(fileScan.next()));
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
    Vector<String> versionNames = new Vector<>(); //list of versions of the model
    Vector<Integer> versionPrices = new Vector<>(); //list of prices of the versions
    Vector<Carbon> versionCarbons = new Vector<>(); //list of materials of the versions

    // why do I have this in instance and statis??
    int minPrice = 999999, maxPrice = 0; // most and least expensive version of the model


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
        ListIterator<String> names = versionNames.listIterator();
        ListIterator<Integer> prices = versionPrices.listIterator();
        String out = modelName + "\n-----------------------\n";

        while (names.hasNext()) {
            out += names.next() + ": $" + prices.next() + "\n";
        }
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
     * Prints the price range, both in absolute price and multiples of least price. Currently not used.
     *
     * Example: "Specialized_Diverge	$7400	7.73x"
     */
    public void printRange() {
        System.out.printf("%s\t$%d\t%.2fx\n", modelName, maxPrice - minPrice, (double) (maxPrice) / minPrice);
    }

    /**
     * Divides the bikes by price into numHistogramBins bins.
     * each int is the number of versions in that price range
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

