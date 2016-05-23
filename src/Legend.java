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
        final int WIDTH = 200, HEIGHT = 470;

        g.setColor(Color.black); // box background
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.white); // box border
        g.drawRect(x, y, WIDTH, HEIGHT);

        g.setFont(theFont);

        // actual legend part
        Carbon[] cs = Carbon.values();
        Carbon carb;
        Shape shape;
        for (int i = 0; i < cs.length; i++) {
            carb = cs[i];
            int theY = y + (i * 30) + 10;
            shape = carb.getShape(x + 10, theY, markerSize);
            g.setColor(Color.white);
            g.draw(shape);
            g.drawString(carb.description, x + 20 + markerSize, theY + (markerSize / 2) + 4);
        }

        Groupset[] gs = Groupset.getRanked();

        final int barHeight = 40, barWidth = 60;
        Groupset currGroup;
        int theY;
        for (int i = 0; i < gs.length; i++) {
            currGroup = gs[i];
            g.setColor(currGroup.getColor());
            theY = y + 120 + i * (barHeight+2);
            g.fillRect(x + 10, theY, barWidth, barHeight);
            g.setColor(Color.white);
            g.drawString(currGroup.name, x + barWidth + 20, theY + barHeight / 2 + 3);
        }


    }


}
