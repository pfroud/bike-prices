import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Describes the material of a bike by how much of it is carbon fiber.
 */
public enum Carbon {
    ALL, FORK, NONE;

    //static(?) variables - http://stackoverflow.com/a/18883717
    Color  color;
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
     * Gets a shape to represent this carbon value.
     *
     * @param x    the x coordinate of the shape
     * @param y    the y coordinate of the shape
     * @param size the width of the shape
     * @return the shape to draw for this Carbon
     */
    public Shape getShape(int x, int y, int size) {
        switch (this) {
            case ALL:
                return new Ellipse2D.Float(x, y, size, size);
            case FORK:
                return new Polygon(new int[]{x + size / 2, x + size, x}, new int[]{y, y + size, y + size}, 3);
            case NONE:
                return new Rectangle(x, y, size, size);
        }
        return null;
    }


    /**
     * Parses a string into a Carbon enum.
     *
     * @param s string to parse into a Carbon enum
     * @return Carbon enum parsed from string
     */
    public static Carbon parseString(String s) {
        return Carbon.valueOf(s.toUpperCase());
    }

}
