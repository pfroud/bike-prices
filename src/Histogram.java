import java.awt.*;
import java.util.Collections;
import java.util.Vector;

public class Histogram {

    private Vector<Integer> data;

    private int x, y, width, height, barWidth, barSpacing, vertScale, marginToCenter;
    private String caption;
    Font fontDefault = new Font("Arial", Font.PLAIN, 12);
    Font fontCaption = new Font("Arial", Font.PLAIN, 20);

    public Histogram(Vector<Integer> data, String caption, int x, int y, int width, int height) {
        this.data = data;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.caption = caption;

        barWidth = 20;
        barSpacing = 10;

        int totalBarWidth = (barWidth + barSpacing) * data.size() - barSpacing;
        marginToCenter = (width - totalBarWidth) / 2;

//        vertScale = 10;
        int max = Collections.max(data);
        int marginTop = 15;
        vertScale = (height - marginTop) / max;

    }

    public void draw(Graphics g) {
        // coordinate system: positive is down and right.
        // positive being right is okay, but we want positive to go up.

        g.setColor(Color.lightGray); //bounding box
        g.drawRect(x, y - height, width, height);
        g.setColor(Color.red);         //origin
        g.fillOval(x - 5, y - 5, 10, 10);
        g.setColor(Color.black);
        g.setFont(fontDefault);

        FontMetrics fm = g.getFontMetrics();

        g.drawLine(x, y, x + width, y);

        for (int i = 0; i < data.size(); i++) {
            int theData = data.get(i);

            int xStart = x + i * (barWidth + barSpacing) + marginToCenter;
            int height = theData * vertScale;
            int yStart = y - height;

            g.fillRect(xStart, yStart, barWidth, height);
            g.drawString(theData + "", xStart + barWidth / 2 - 3, yStart - 2);
        }

        g.setFont(fontCaption);

        // http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Centertext.htm
        int centerOffset = (width - fm.stringWidth(caption)) / 2;

        g.drawString(caption, x + centerOffset, y + 25);

    }


}
