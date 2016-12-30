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
     * Initialize the vector of histograms.
     * This must be separate from the constructor and called by a Diagram instance.
     *
     * @param bikes vector of all bikes
     */
    void init(Vector<Bike> bikes) {
        Histogram h;

        final int histsPerRow = 10;
        final int numBikes = bikes.size();
        final int SPACING = 50;

        Bike bike;
        int mod;
        for (int start = 0, end = histsPerRow; end <= numBikes; start = end + 1, end += histsPerRow) {
            mod = start / histsPerRow;
            for (int i = start; i < end; i++) {
                bike = bikes.get(i);
                h = new Histogram(bike.getHistogramData(numBins), bike.modelName);
                h.setSize(x + (histSize + SPACING) * (i % histsPerRow), y + mod * (histSize + SPACING), histSize, histSize);
                hists.add(h);
            }
        }

    }

    void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 9999, 999);

        for (Histogram hist : hists) hist.draw(g);
    }

}
