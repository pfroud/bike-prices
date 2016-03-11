import java.awt.*;
import java.util.Vector;

/**
 * Eventually will print histograms and stuff.
 */
public class Analysis {

    private int numBins;
    private Vector<Histogram> hists;

    public Analysis(int numBins) {
        this.numBins = numBins;
    }

    public void init(Vector<Bike> bikes) {
        for (Bike b : bikes) {
//            hists.add(new Histogram(b.getHistogramData(numBins)), 0, 0, 0, 0);
        }
    }

    // I don't know where this will get drawn
    public void draw(Graphics g) {
        for (Histogram hist : hists) {
            hist.draw(g);
        }
    }

}
