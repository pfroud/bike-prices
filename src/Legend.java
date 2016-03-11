import java.awt.*;

public class Legend {

    private int x, y, width, height, markerSize;

    public Legend(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setProps(int markerSize) {
        this.markerSize = markerSize;
    }

    /**
     * Draws a box showing what each color of dot means.
     *
     * @param g Graphics context
     */
    public void draw(Graphics2D g) {
        // box
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);

        g.setFont(new Font("Arial", Font.PLAIN, 14));

        // actual legend part
        Carbon[] cs = {Carbon.ALL, Carbon.FORK, Carbon.NONE};
        for (int i = 0; i < cs.length; i++) {
            Carbon c = cs[i];

            int theY = y + i * 30 + 10;
            c.draw(g, x + 10, theY, markerSize, true);
            g.setColor(Color.black);
            g.drawString(c.getDescription(), x + 20 + markerSize, theY + (markerSize / 2) + 4);
        }

    }
}
