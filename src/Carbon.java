import java.awt.*;

/**
 * Describes the material of a bike by how much of it is carbon fiber.
 */
public enum Carbon {
    ALL, FORK, NONE;

    public static Color getColor(Carbon c){
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

}
