import java.awt.*;

/**
 * A box describing what each of the color of dots mean.
 */
class Legend {

    private int x, y, markerSize;
    private Font theFont = new Font("Arial", Font.PLAIN, 14);

    Legend(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void setMarkerSize(int markerSize) {
        this.markerSize = markerSize;
    }

    void draw(Graphics2D g) {
        final int WIDTH = 200, HEIGHT = 320;

        //background
        g.setColor(Color.white); // box background
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.black); // box border
        g.drawRect(x, y, WIDTH, HEIGHT);

        g.setFont(theFont);

        // draw carbon legend
        Carbon[] carbons = Carbon.values();
        Carbon currCarb;
        Shape shapeToDraw;
        int theY;
        for (int i = 0; i < carbons.length; i++) {
            currCarb = carbons[i];
            theY = y + (i * 30) + 10;
            shapeToDraw = currCarb.getShape(x + 10, theY, markerSize);
            g.setColor(Color.black);
            g.draw(shapeToDraw);
            g.drawString(currCarb.description, x + 20 + markerSize, theY + (markerSize / 2) + 4);
        }

        // draw groupset legend
        Groupset[] gs = Groupset.getRanked();
//        final int barHeight = 40, barWidth = 60;
        final int barHeight = 25, barWidth = 40;
        Groupset currGroup;
        for (int i = 0; i < gs.length; i++) {
            currGroup = gs[i];
            g.setColor(currGroup.getColor());
            theY = y + 100 + i * (barHeight + 2);
            g.fillRect(x + 10, theY, barWidth, barHeight);
            g.setColor(Color.black);
            g.drawString(currGroup.name, x + barWidth + 20, theY + barHeight / 2 + 3);
        }


    }


}
