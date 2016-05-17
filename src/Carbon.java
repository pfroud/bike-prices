import java.awt.*;

/**
 * Describes the material of a bike by how much of it is carbon fiber.
 */
public enum Carbon {
    ALL, FORK, NONE;

    //http://stackoverflow.com/a/18883717
    Color color;
    String description;

    static {

        int alpha = 100;

        ALL.color = Color.cyan;
//        ALL.color = new Color(0, 255, 255, alpha);
        FORK.color = Color.yellow;
//        FORK.color = new Color(255, 255, 0, alpha);
        NONE.color = Color.red;
//        NONE.color = new Color(255, 0, 0, alpha);

        ALL.description = "full carbon-fiber frame";
        FORK.description = "carbon-fiber fork only";
        NONE.description = "no carbon-fiber";
    }

    public static Carbon[] getAllValues() {
        return new Carbon[]{NONE, FORK, ALL};
    }



    /**
     * Draws a dot for the carbon value.
     *
     * @param g        graphic context
     * @param dotX     x location of the dot
     * @param dotY     y location of the dot
     * @param size     diameter of the dot
     * @param inLegend true if drawing in the legend, false if drawing in bar
     */
    public void draw(Graphics g, int dotX, int dotY, int size, boolean inLegend) {
        g.setColor(this.color);
        g.fillOval(dotX, dotY, size, size);
        if (inLegend) {
            g.setColor(Color.black);
            g.drawOval(dotX, dotY, size, size);
        }
    }

    /**
     * Parses a string into a Carbon enum.
     *
     * @param s string to parse into Carbon
     * @return Carbon parsed from string
     */
    public static Carbon parseString(String s) {
         return Carbon.valueOf(s.toUpperCase());
    }

}
