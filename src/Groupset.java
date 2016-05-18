import java.awt.*;

enum Groupset {
    CLARIS(8), SORA(7), TIAGRA(6), _105(5), ULTEGRA(4), ULTEGRA_DI2(3), DURA_ACE(2), DURA_ACE_DI2(1),
    RIVAL(5), FORCE(4), RED(2), RED_ETAP(1);

    int rank;

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

    Groupset(int rank) {
        this.rank = rank;
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
