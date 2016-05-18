import java.awt.*;

/**
 * A box describing what each of the color of dots mean.
 */
class Legend {

    private int x, y, width, height, markerSize;
    private Font theFont = new Font("Arial", Font.PLAIN, 14);

    Legend(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    void setMarkerSize(int markerSize) {
        this.markerSize = markerSize;
    }

    void draw(Graphics2D g) {
        g.setColor(Color.white); // box background
        g.fillRect(x, y, width, height);
        g.setColor(Color.black); // box border
        g.drawRect(x, y, width, height);

        g.setFont(theFont);

        // actual legend part
        Carbon[] cs = Carbon.values();
        Carbon currentCarb;
        for (int i = 0; i < cs.length; i++) {
            currentCarb = cs[i];

            int theY = y + (i * 30) + 10;
            currentCarb.draw(g, x + 10, theY, markerSize, true);
            g.setColor(Color.black);
            g.drawString(currentCarb.description, x + 20 + markerSize, theY + (markerSize / 2) + 4);
        }

        Color[] colors = Groupset.getGradient();

        for (int i = 0, len = colors.length; i < len; i++) {
            g.setColor(colors[i]);
            g.fillRect(0, i*50, 300, 50);
        }



    }


}
