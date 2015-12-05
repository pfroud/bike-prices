import java.util.ListIterator;
import java.util.Vector;

/**
 * A model of bike, made up of versions of the model.
 * For example, a model is "Specialized Diverge" and a version is "Elite A1".
 */
class Bike {
    String modelName; //name of the model

    //yay, everything is default privacy! - sort of
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


    public void printRangeAndFactor() {
        //System.out.println("model\tabsolute range\tfactor");
        System.out.printf("%s\t$%d\t%.2f\n", modelName, maxCost - minCost, (double) (maxCost) / minCost);
    }

    public void printHistogram(int numBins) {
        Vector<Integer> bins = new Vector<>(numBins);
        for (int i = 0; i < numBins; i++) {
            bins.add(i, 0);
        }

        double binWidth = (double) (maxCost - minCost) / numBins;
        double costCutoff;

        for (Integer currentVersionCost : versionCosts) {

            for (int currentBin = 1; currentBin <= numBins; currentBin++) {
                costCutoff = minCost + (binWidth * currentBin);

                if (currentVersionCost <= costCutoff) {
                    bins.set(currentBin - 1, bins.get(currentBin - 1) + 1); //need currentBin-1 bc index starts at 0
                    break;
                }
            }

        }
        System.out.println(modelName + ": " + bins);

    }


}

