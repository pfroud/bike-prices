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

    // region STATIC STUFF

    private static int costMax, costMin;

    /**
     * Opens the input file and reads all bikes in the file.
     *
     * @param filename name of text file with bike info
     */
    public static Vector<Bike> readBikes(String filename) {
        Scanner fileScan = null; //Scanner to read input text file

        //open file
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

    /// PRIVATE FUNCTIONS

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
     *     (b) version costs
     *     (c) version materials
     *
     * @param fileScan scanner on the input text file
     * @return Bike object containing all of the version names, costs, and materials
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

        // add version costs
        int currentCost;
        for (int i = 0; i < numModels; i++) {
            currentCost = fileScan.nextInt();

            //update global min and max cost
            if (currentCost > costMax) costMax = currentCost;
            if (currentCost < costMin) costMin = currentCost;

            bike.addCost(currentCost);
        }

        // add version materials
        String currentCarbon;
        for (int i = 0; i < numModels; i++) {
            currentCarbon = fileScan.next();
            bike.versionCarbons.add(Carbon.parseString(currentCarbon));
        }
        return bike;
    }

    public static int getCostMax() {
        return costMax;
    }
    public static int getCostMin() {
        return costMin;
    }

    // endregion

    // region INSTANCE STUFF


    String modelName; //name of the model
    int numModels;

    //TODO make these private
    Vector<String> versionNames = new Vector<>(); //list of versions of the model
    Vector<Integer> versionCosts = new Vector<>(); //list of prices of the versions
    Vector<Carbon> versionCarbons = new Vector<>(); //list of materials of the versions

    int minCost = 999999, maxCost = 0; //tracks the cost of the most and least expensive version of the model


    /**
     * Constructs new Bike given the name of the model.
     *
     * @param modelName the name of the model to construct.
     */
    public Bike(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Converts Bike object to string.
     * Overrides Object.toString().
     *
     * @return a human-readable representation of the bike model.
     */
    public String toString() {
        ListIterator<String> n = versionNames.listIterator();
        ListIterator<Integer> c = versionCosts.listIterator();
        String out = modelName + "\n-----------------------\n";

        while (n.hasNext()) {
            out += n.next() + ": $" + c.next() + "\n";
        }
        return out;
    }

    /**
     * Used to add a cost to the vector of costs.
     * Costs isn't private but you should still use this.
     *
     * @param c the cost of the version to add.
     */
    public void addCost(int c) {
        versionCosts.add(c); //add to vector

        //update min and max versionCosts
        if (c > maxCost) {
            maxCost = c;
        }
        if (c < minCost) {
            minCost = c;
        }
    }

    /**
     * Prints the cost range, both in absolute cost and multiples of least price.
     * Called by printRanges() in Main.
     *
     * Example: "Specialized_Diverge	$7400	7.73x"
     */
    public void printRange() {
        System.out.printf("%s\t$%d\t%.2fx\n", modelName, maxCost - minCost, (double) (maxCost) / minCost);
    }

    /**
     * Divides the bikes by cost into numHistogramBins bins.
     *
     * Example: "Specialized_Diverge: [5, 1, 1]"
     */
    public Vector<Integer> getHistogramData(int numHistogramBins) {
        // each int is the number of versions in that price range
        Vector<Integer> bins = new Vector<>(numHistogramBins);
        for (int i = 0; i < numHistogramBins; i++) {
            bins.add(i, 0);
        }

        double binWidth = (double) (maxCost - minCost) / numHistogramBins; // size in cost of each bin
        double costCutoff; //max cost for a version to be in a bin

        //iterate over versions
        for (Integer currentVersionCost : versionCosts) {

            //iterate over bins
            for (int currentBin = 1; currentBin <= numHistogramBins; currentBin++) {
                costCutoff = minCost + (binWidth * currentBin);

                if (currentVersionCost <= costCutoff) {
                    bins.set(currentBin - 1, bins.get(currentBin - 1) + 1); //need currentBin-1 bc index starts at 0
                    break;
                }
            }

        }
        return bins;
    }

    // endregion

}

