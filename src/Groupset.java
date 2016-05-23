import java.awt.*;

enum Groupset {
    CLARIS(8, "Claris"), SORA(7, "Sora"), TIAGRA(6, "Tiagra"), _105(5, "105"), ULTEGRA(4, "Ultegra"),
    ULTEGRA_DI2(3, "Ultegra Di2"), DURA_ACE(2, "Dura-Ace"), DURA_ACE_DI2(1, "Dura-Ace Di2"),

    RIVAL(5, "Rival"), FORCE(4, "Force"), RED(2, "Red"), RED_ETAP(1, "Red eTap");

    int rank;
    String name;

    static final Color[] rankColors =
            {
                    Color.magenta,
                    Color.red,
                    Color.orange,
                    Color.yellow,
                    Color.decode("#23DB45"), // green
                    Color.decode("#27DBDB"), // sky blue
                    Color.blue,
                    Color.decode("#5924E0"), // purple
            };

    Groupset(int rank, String name) {
        this.rank = rank;
        this.name = name;
    }

    public static Groupset[] getRanked() {
        return new Groupset[]{DURA_ACE_DI2, DURA_ACE, ULTEGRA_DI2, ULTEGRA, _105, TIAGRA, SORA, CLARIS};
    }

    public Color getColor() {
        return rankColors[this.rank - 1];
    }

    public static Groupset parseString(String s) {
        if (s.equals("105")) return _105;
        return Groupset.valueOf(s.replaceAll(" |-", "_").toUpperCase());
    }

    // http://stackoverflow.com/a/17544598
    private static Color blendColors(Color x, Color y, float bl) {
        if (bl < 0 || bl > 1) throw new IllegalArgumentException("blend factor must be between 0 and 1");
        float inv = 1 - bl;

        float r = x.getRed() * bl + y.getRed() * inv;
        float g = x.getGreen() * bl + y.getGreen() * inv;
        float b = x.getBlue() * bl + y.getBlue() * inv;

        return new Color(r / 255, g / 255, b / 255);
    }

}
