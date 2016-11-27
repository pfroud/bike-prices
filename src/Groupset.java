import java.awt.*;

/**
 * A groupset refers to a collection of mechanical parts on a bike. The quality of the groupset is a good measurement
 * of the quality of the bike.
 *
 * This enum was hacked together and there's probably a better way to do this.
 */
enum Groupset {
    // this is terrible.
    CLARIS(8, "Claris"), SORA(7, "Sora"), TIAGRA(6, "Tiagra"), _105(5, "105"), ULTEGRA(4, "Ultegra"),
    ULTEGRA_DI2(3, "Ultegra Di2"), DURA_ACE(2, "Dura-Ace"), DURA_ACE_DI2(1, "Dura-Ace Di2"),

    RIVAL(5, "Rival"), FORCE(4, "Force"), RED(2, "Red"), RED_ETAP(1, "Red eTap"); // Sram groupsets

    int rank;
    String name;

    // indexed by rank
    static final Color[] rankColors =
            {
                    Color.decode("#ff43f4"), // pink
                    Color.decode("#ff0000"), // red
                    Color.decode("#ff9400"), // orange
                    Color.decode("#fff000"), // yellow
                    Color.decode("#00ff00"), // green
                    Color.decode("#0054ff"), // blue
                    Color.decode("#5824e0"), // purple
                    Color.decode("#735132")  // brown
            };

    Groupset(int rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    /**
     * Returns an array of Shimano Groupsets sorted from most expensive to least expensive.
     *
     * @return an arrya of Groupsets in descending price
     */
    public static Groupset[] getRanked() {
        return new Groupset[]{DURA_ACE_DI2, DURA_ACE, ULTEGRA_DI2, ULTEGRA, _105, TIAGRA, SORA, CLARIS};
    }

    /**
     * Returns the color to print this groupset's shape in.
     *
     * @return the color to print this groupset using.
     */
    public Color getColor() {
        return rankColors[this.rank - 1];
    }

    /**
     * Parses a Groupset from a string.
     *
     * @param s the string to parse into a Groupset
     * @return a Groupset parsed from a string
     */
    public static Groupset parseString(String s) {
        if (s.equals("105")) return _105;
        return Groupset.valueOf(s.replaceAll(" |-", "_").toUpperCase());
    }


}
