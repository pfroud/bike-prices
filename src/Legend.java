import java.awt.*;

/**
 * A box describing what each of the color of dots mean.
 */
class Legend {

    private int x, y, markerSize;
    private Font fondLegend = new Font("Arial", Font.PLAIN, 14);

    Legend(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setMarkerSize(int markerSize) {
        this.markerSize = markerSize;
    }

    void draw(Graphics2D g) {
        final int WIDTH = 200, HEIGHT = 320;

        //white background with black box
        g.setColor(Color.white);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.black);
        g.drawRect(x, y, WIDTH, HEIGHT);

        g.setFont(fondLegend);

        // draw carbon legend
        Carbon[] carbons = Carbon.values(); // returns the constants of this enum type, in the order they're declared
        Carbon   currCarb;
        Shape    shapeToDraw;
        int      theY;
        for (int i = 0; i < carbons.length; i++) {
            currCarb = carbons[i];
            theY = y + (i * 30) + 10;
            shapeToDraw = currCarb.getShape(x + 10, theY, markerSize);
            g.setColor(Color.black);
            g.draw(shapeToDraw);
            g.drawString(currCarb.description, x + 20 + markerSize, theY + (markerSize / 2) + 4);
        }

        // draw groupset legend
        Groupset[] groupsets = Groupset.getRanked();
        final int  barHeight = 25, barWidth = 40;
        Groupset   currGroup;
        for (int i = 0; i < groupsets.length; i++) {
            currGroup = groupsets[i];
            g.setColor(currGroup.getColor());
            theY = y + 100 + i * (barHeight + 2);
            g.fillRect(x + 10, theY, barWidth, barHeight);
            g.setColor(Color.black);
            g.drawString(currGroup.name, x + barWidth + 20, theY + barHeight / 2 + 3);
        }


    }


}
