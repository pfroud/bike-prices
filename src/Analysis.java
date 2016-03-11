import java.awt.*;
import java.util.Vector;

/**
 * Eventually will print histograms and stuff.
 */
public class Analysis {

    private int numBins;
    private Vector<Histogram> hists = new Vector<>();

    public Analysis(int numBins) {
        this.numBins = numBins;
    }


    /**
     * This must be separate from the constructor b/c bikes vector only accessible in Diagram, not Main
     *
     * @param bikes vector of all bikes
     */
    public void init(Vector<Bike> bikes) {
        Histogram h;

        int histSize = 100;
        int half = bikes.size() / 2; // halfway through the vector of bikes

        // draw the first half of the histograms on one row
        for (int i = 0; i < half; i++) {
            Bike bike = bikes.get(i);
            h = new Histogram(bike.getHistogramData(numBins), bike.modelName);
            h.setSize(i * (histSize + 50) + 50, 200, histSize, histSize);
            hists.add(h);
        }
        // draw the second half of histograms on a second row
        for (int i = half; i < bikes.size(); i++) {
            Bike bike = bikes.get(i);
            h = new Histogram(bike.getHistogramData(numBins), bike.modelName);
            h.setSize((i - half) * (histSize + 50) + 50, 400, histSize, histSize);
            hists.add(h);
        }

    }

    public void draw(Graphics g) {
        for (Histogram hist : hists) {
            hist.draw(g);
        }
    }

}
