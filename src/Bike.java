import java.util.ListIterator;
import java.util.Vector;

/**
 * A model of bike, made up of versions of the model.
 * For example, a model is "Specialized Diverge" and a version is "Elite A1".
 */
class Bike {
    String modelName; //name of the model

    //yay, everything is default privacy!
    Vector<String> versions = new Vector<>(); //list of versions of the model
    Vector<Integer> costs = new Vector<>(); //list of prices of the versions
    Vector<Carbon> carbons = new Vector<>(); //list of materials of the versions

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
        ListIterator<String> n = versions.listIterator();
        ListIterator<Integer> c = costs.listIterator();
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
        costs.add(c); //add to vector

        //update min and max costs
        if (c > maxCost) {
            maxCost = c;
        }
        if (c < minCost) {
            minCost = c;
        }
    }


}