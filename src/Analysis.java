/**
 * Eventually will print histograms and stuff.
 */
public class Analysis {

    private int numHistogramBins;



    /**
     * Prints histogram info for each bike.
     */
    public void printHistograms(int numHistogramBins, Bike[] allBikes) {
        for (Bike bike : allBikes) {
            bike.printHistogram(numHistogramBins);
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
