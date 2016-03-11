import java.util.Vector;

/**
 * Eventually will print histograms and stuff.
 */
public class Analysis {

    private int numBins;
    private Vector<Bike> bikes;

    public Analysis(int numBins){
        this.numBins = numBins;
    }

    public void setBikes(Vector<Bike> bikes){
        this.bikes = bikes;
    }

    /**
     * Prints histogram info for each bike.
     */
    public void printHistograms(int numHistogramBins, Bike[] allBikes) {
        for (Bike bike : allBikes) {
            bike.getHistogram(numHistogramBins);
        }
    }

    /**
     * Prints range info for each bike.
     */
    public void printRanges(Bike[] allBikes) {
        System.out.println("model\tabsolute range\tfactor"); //header for use in csv file
        for (Bike bike : allBikes) {
            bike.printRange();
        }
    }


}
