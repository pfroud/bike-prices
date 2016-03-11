import java.awt.*;
import java.util.Vector;

public class Histogram {

    private Vector<Integer> data;

    private int x, y, width, height, barWidth, barSpacing, vertScale, marginToCenter;

    public Histogram(Vector<Integer> data, int x, int y, int width, int height) {
        this.data = data;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        barWidth = 20;
        barSpacing = 10;
        vertScale = 10;

        int totalBarWidth = (barWidth + barSpacing) * data.size() - barSpacing;
        marginToCenter = (width-totalBarWidth) / 2;

    }

    public void draw(Graphics g) {
        // coordinate system: positive is down and right.
        // positive being right is okay, but we want positive to go up.

        g.setColor(Color.lightGray); //bounding box
        g.drawRect(x, y - height, width, height);
        g.setColor(Color.red);         //origin
        g.fillOval(x - 5, y - 5, 10, 10);
        g.setColor(Color.black);

        g.drawLine(x, y, x + width, y);

        for (int i = 0; i < data.size(); i++) {
            int height = data.get(i) * vertScale;
            g.fillRect(x + i * (barWidth + barSpacing) + marginToCenter, y - height, barWidth, height);
        }

    }


}
