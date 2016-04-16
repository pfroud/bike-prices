import java.awt.*;
import java.util.Vector;

/**
 * Eventually will print histograms and stuff.
 */
class Analysis {

    private int numBins, x, y, histSize;
    private Vector<Histogram> hists = new Vector<>();

    Analysis(int numBins, int x, int y, int histSize) {
        this.numBins = numBins;
        this.x = x;
        this.y = y;
        this.histSize = histSize;
    }


    /**
     * This must be separate from the constructor b/c bikes vector only accessible in Diagram, not Main
     *
     * @param bikes vector of all bikes
     */
    void init(Vector<Bike> bikes) {
        Histogram h;

        Bike currBike;
        for (int i = 0, len = bikes.size(); i < len; i++) {
            currBike = bikes.get(i);
            h = new Histogram(currBike.getHistogramData(numBins), currBike.modelName);
            h.setSize(x + i * (histSize + 50), y, histSize, histSize);
            hists.add(h);
        }

        /*
        int half = bikes.size() / 2;

        // draw the first half of the histograms on one row
        for (int i = 0; i < half; i++) {
            Bike bike = bikes.get(i);
            h = new Histogram(bike.getHistogramData(numBins), bike.modelName);
            h.setSize(x + i * (histSize + 50), y, histSize, histSize);
            hists.add(h);
        }
        // draw the second half of histograms on a second row
        for (int i = half; i < bikes.size(); i++) {
            Bike bike = bikes.get(i);
            h = new Histogram(bike.getHistogramData(numBins), bike.modelName);
            h.setSize(x + (i - half) * (histSize + 50), y + histSize, histSize, histSize);
            hists.add(h);
        }
        */

    }

    void draw(Graphics g) {
        for (Histogram hist : hists) hist.draw(g);
    }

}
