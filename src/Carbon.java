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

        ALL.color = Color.cyan;
        FORK.color = Color.yellow;
        NONE.color = Color.red;

        ALL.description = "full carbon-fiber frame";
        FORK.description = "carbon-fiber fork only";
        NONE.description = "no carbon-fiber";
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
     * @param s string to parseString into Carbon
     * @return Carbon parsed from string
     */
    public static Carbon parseString(String s) {
         return Carbon.valueOf(s.toUpperCase());
    }

}
