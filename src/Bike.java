import java.util.ListIterator;
import java.util.Vector;

class Bike {

    String lineName;
    Vector<String> names = new Vector<String>();
    Vector<Integer> costs = new Vector<Integer>();
    Vector<Carbon> carbons = new Vector<Carbon>();
    int minCost = 999999, maxCost = 0;

    public Bike(String lineName) {
        this.lineName = lineName;
    }

    public String toString() {
        ListIterator<String> n = names.listIterator();
        ListIterator<Integer> c = costs.listIterator();
        String out = lineName + "\n-----------------------\n";

        while (n.hasNext()) {
            out += n.next() + ": $" + c.next() + "\n";
        }
        return out;
    }

    public void addCost(int c) {
        costs.add(c);

        if (c > maxCost) {
            maxCost = c;
        }
        if (c < minCost) {
            minCost = c;
        }
    }


}