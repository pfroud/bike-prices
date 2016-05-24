import java.awt.*;
import java.util.Vector;

/**
 * Eventually will print histograms and stuff.
 */
class Analysis {

    private int numBins, x, y, histSize, width = 1;
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


        final int histsPerRow = 10;
        final int size = bikes.size();
        final int SPACING = 50;



        Bike bike;
        int mod;
        for (int start = 0, end = histsPerRow; end <= size; start = end + 1, end += histsPerRow) {
            mod = start / histsPerRow;
            for (int i = start; i < end; i++) {
                bike = bikes.get(i);
                h = new Histogram(bike.getHistogramData(numBins), bike.modelName);
                h.setSize(x + (histSize + SPACING) * (i%10), y + mod * (histSize+50), histSize, histSize);
                hists.add(h);
            }
        }

    }

    void draw(Graphics g) {
        for (Histogram hist : hists) hist.draw(g);
    }

}
