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
        final int WIDTH = 200, HEIGHT = 400;

        g.setColor(Color.white); // box background
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.black); // box border
        g.drawRect(x, y, WIDTH, HEIGHT);

        g.setFont(theFont);

        // actual legend part
        Carbon[] cs = Carbon.values();
        Carbon carb;
        Shape shape;
        for (int i = 0; i < cs.length; i++) {
            carb = cs[i];
            int theY = y + (i * 30) + 10;
            shape = carb.getShape(x+10, theY, markerSize);
            g.setColor(Color.black);
            g.draw(shape);
            g.drawString(carb.description, x + 20 + markerSize, theY + (markerSize / 2) + 4);
        }

        Color[] colors = Groupset.getGradient();
        int barHeight = 50;
        for (int i = 0, len = colors.length; i < len; i++) {
            g.setColor(colors[i]);
            g.fillRect(x, y + 100 + i*barHeight, 50, barHeight);
            g.setColor(Color.black);

        }

    }


}
