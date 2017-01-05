import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

/**
 * A model of bike, made up of versions of the model.
 * For example, a model is "Specialized Diverge" and a version is "Elite A1".
 */
public class Bike implements Comparable<Bike> {

    // region statics

    static int priceMinGlobal = 888888, priceMaxGlobal; // min and max price of ALL bikes

    /**
     * Opens an input file and reads all of it into a vector of Bikes.
     *
     * @param filename name of text file with bike info
     * @return a vector of bikes built from the input file
     */
    static Vector<Bike> readBikes(String filename) {
        Scanner fileScan = null;

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
     * Reads and returns a single bike model.
     * Steps:
     *
     * (1) Reads the header, which contains the model name and the number of versions.
     *     Example: "Trek Domane: 19"
     *
     * (2) Does four for loops to read:
     *     (a) version names
     *     (b) version prices
     *     (c) version materials
     *     (d) version groupsets
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
        Bike newBike = new Bike(name);

        // read number of versions
        int numModels = sc.nextInt();
        newBike.numModels = numModels;

        sc.useDelimiter(", |\r?\n|\r"); // only use newline as delimiter

        // read version names
        for (int i = 0; i < numModels; i++) {
            newBike.names.add(sc.next());
        }

        // read version prices
        for (int i = 0; i < numModels; i++) {
            newBike.addPrice(sc.nextInt());
        }

        // read version materials
        for (int i = 0; i < numModels; i++) {
            newBike.carbons.add(Carbon.parseString(sc.next()));
        }

        // read version groupsets
        for (int i = 0; i < numModels; i++) {
            newBike.groupsets.add(Groupset.parseString(sc.next()));
        }

        return newBike;
    }


    // endregion statics

    // region instance stuff

    // I have decided everything can be default privacy

    String modelName;
    int    numModels;

    Vector<String>   names     = new Vector<>();
    Vector<Integer>  prices    = new Vector<>();
    Vector<Carbon>   carbons   = new Vector<>();
    Vector<Groupset> groupsets = new Vector<>();

    int minPriceModel = 999999, maxPriceModel; // price of most and least expensive version of the model

    public int compareTo(Bike other) {
        return Integer.compare(minPriceModel, other.minPriceModel);
    }


    /**
     * Constructs new Bike given the name of the model.
     *
     * @param name the name of the model to construct.
     */
    public Bike(String name) {
        this.modelName = name;
    }

    /**
     * Gives human-readable representation of a bike with all the versions.
     *
     * @return a human-readable representation of the bike model.
     */
    public String toString() {
        NumberFormat numFmt = NumberFormat.getInstance();
        String       out    = modelName + "\n-----------------------\n";
        for (int i = 0; i < names.size(); i++) {
            out += String.format("%s: $%s\n", names.get(i), numFmt.format(prices.get(i)));
        }
        return out;
    }

    /**
     * Used to add a price to the vector of prices.
     * prices isn't private but you should still use this.
     *
     * @param p the price of the version to add.
     */
    private void addPrice(int p) {
        prices.add(p);

        if (p < priceMinGlobal) priceMinGlobal = p;
        if (p > priceMaxGlobal) priceMaxGlobal = p;

        if (p < minPriceModel) minPriceModel = p;
        if (p > maxPriceModel) maxPriceModel = p;
    }

    /**
     * Prints the header for the CSV populated by CSV_printRow().
     */
    static void CSV_printHeader() {
        System.out.println("\"Model\", \"Absolute price range\", \"Relative price range\", \"" +
                "Least expensive version\", \"Least expensive version price\", \"" +
                "Most expensive version\", \"Most expensive version price\"");
    }

    /**
     * Prints a CSV row of: model name, absolute price range, relative price range, name of least expensive version,
     * price of least expensive version, name of most expensive version, price of most expensive version.
     *
     * When writing CSV files, use CSV_printHeader() first.
     */
    void CSV_printRow() {
        NumberFormat numFmt = NumberFormat.getNumberInstance();
        System.out.printf("\"%s\", \"$%s\", \"%.2fx\", \"%s\", \"$%s\", \"%s\", \"$%s\"\n",
                modelName,
                numFmt.format(maxPriceModel - minPriceModel),
                (double) (maxPriceModel) / minPriceModel,
                names.firstElement(),
                numFmt.format(prices.firstElement()),
                names.lastElement(),
                numFmt.format(prices.lastElement()));
    }

    /**
     * Returns data for input to a histogram.
     * Partitions the bikes by price into numBins bins. Each element of the vector
     * is the number of versions in that price range.
     */
    Vector<Integer> getHistogramData(int numBins) {
        Vector<Integer> bins = new Vector<>(numBins);
        for (int i = 0; i < numBins; i++) bins.add(i, 0);


        double binWidth = (double) (maxPriceModel - minPriceModel) / numBins; // width in dollars of each bin
        double priceCutoff; // max price for a version to be in a bin

        for (int currentPrice : prices) {
            for (int currentBin = 1; currentBin <= numBins; currentBin++) {
                priceCutoff = minPriceModel + (binWidth * currentBin);

                if (currentPrice <= priceCutoff) {
                    // this is gross, mixing zero-based and one-based
                    bins.set(currentBin - 1, bins.get(currentBin - 1) + 1);
                    break;
                }
            }
        }
        return bins;
    }

    // endregion instance stuff

}

