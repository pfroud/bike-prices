import java.awt.*;
import java.util.Vector;

/**
 *
 */
public class Histogram {

    private Vector<Integer> data; // contents of histogram bins

    private int x, y, width, height, barWidth, barSpacing, vertScale, marginToCenter; // height not used
    private String caption;
    Font fontDefault = new Font("Arial", Font.PLAIN, 12);
    Font fontCaption = fontDefault;

    public Histogram(Vector<Integer> data, String caption) {
        this.data = data;
        this.caption = caption;
    }

    public void setSize(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

//        barWidth = 10;
        barSpacing = 1;
        barWidth = (width + barSpacing) / data.size();

//         when the bar width is constant, use this to center them
//        int totalBarWidth = (barWidth + barSpacing) * data.size() - barSpacing;
//        marginToCenter = (width - totalBarWidth) / 2;
        marginToCenter = 0;

        int marginTop = 0;
//        vertically scales each histogram so the tallest bar reaches the top
//        int max = Collections.max(data);
//        vertScale = (height - marginTop) / max;
        vertScale = (height - marginTop) / 12;
    }

    public void draw(Graphics g) {
        // coordinate system: positive is down and right.

/*        g.setColor(Color.lightGray);
        g.drawRect(x, y - height, width, height); // bounding box
        g.setColor(Color.red);
        g.fillOval(x - 5, y - 5, 10, 10); //dot for origin*/

        g.setColor(Color.black);
//        g.drawLine(x-2, y, x + width, y); // x-axis

        g.setFont(fontDefault);

        // draw individual bars
        for (int i = 0; i < data.size(); i++) {
            int currentData = data.get(i);

            int xStart = x + i * (barWidth + barSpacing) + marginToCenter;
            int height = currentData * vertScale;
//            if (height == 0) height = 1;
            int yStart = y - height;

            g.fillRect(xStart, yStart, barWidth, height);
            g.drawString(Integer.toString(currentData), xStart + barWidth / 2 - 3, yStart - 2);
        }

        g.setFont(fontCaption);

        // http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Centertext.htm
        FontMetrics fm = g.getFontMetrics();
        int centerOffset = (width - fm.stringWidth(caption)) / 2;

        g.drawString(caption, x + centerOffset, y + 25);

    }


}
