import java.awt.*;
import java.util.Vector;

/**
 * Represents a histogram of bike prices.
 * Histograms's aren't used at all right now.
 */
class Histogram {

    private Vector<Integer> data; // histogram bins

    private int x, y, size, barWidth, barSpacing, verticalScale;
    private String caption;
    private Font fontHistogram = new Font("Arial", Font.PLAIN, 12);

    Histogram(Vector<Integer> data, String caption) {
        this.data = data;
        this.caption = caption;
    }

    void init(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        //System.out.printf("%s: %d, %d\n", caption, x, y);

        barSpacing = 1;
        barWidth = (size + barSpacing) / data.size();
        verticalScale = size / 12;
    }

    void draw(Graphics g) {
        // Coordinate system: positive is down and right.

        // Uncomment to draw bounding box
        g.setColor(Color.lightGray);
        g.drawRect(x, y - size, size, size);
        //dot for origin
        g.setColor(Color.red);
        int originSize = 10;
        g.fillOval(x - originSize / 2, y - originSize / 2, originSize, originSize);
        //g.setColor(Color.black);
        //g.drawString(String.format("(%d,%d)", x, y), x - 20, y + 12);

        // uncomment to draw x axis
        //g.setColor(Color.black);
        //g.drawLine(x-2, y, x + size, y);

        g.setFont(fontHistogram);
        g.setColor(Color.black);

        // draw bars
        int currentData, xStart, yStart, barHeight;
        for (int i = 0, len = data.size(); i < len; i++) {
            currentData = data.get(i);

            barHeight = currentData * verticalScale;
            //if (barHeight == 0) barHeight = 1;
            xStart = x + i * (barWidth + barSpacing);
            yStart = y - barHeight;

            g.fillRect(xStart, yStart, barWidth, barHeight);
            g.drawString(currentData + "", xStart + barWidth / 2 - 3, yStart - 2); // bar label
        }

        // http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Centertext.htm
        int centerOffset = (size - g.getFontMetrics().stringWidth(caption)) / 2;
        g.drawString(caption, x + centerOffset, y + 20);

    }


}
