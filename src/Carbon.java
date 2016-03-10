import java.awt.*;

/**
 * Describes the material of a bike by how much of it is carbon fiber.
 */
public enum Carbon {
    ALL, FORK, NONE;

    /**
     * Gets a color to draw the dot for a Carbon enum.
     *
     * @param c Carbon enum to get a color for
     * @return Color for the Carbon enum
     */
    public static Color getColor(Carbon c) {
        switch (c) {
            case ALL:
                return Color.cyan;
            case FORK:
                return Color.yellow;
            case NONE:
                return Color.red;
        }
        return null;
    }

    /**
     * Parses a string into a Carbon enum.
     *
     * @param s string to parse into Carbon
     * @return Carbon parsed from string
     */
    public static Carbon parseString(String s) {
        switch (s) {
            case "all":
                return Carbon.ALL;
            case "fork":
                return Carbon.FORK;
            case "none":
                return Carbon.NONE;
            default:
                System.err.println("unrecognized carbon value \"" + s + "\"");
        }
        return null;
    }

}
