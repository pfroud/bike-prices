import java.awt.*;

enum Groupset {
    CLARIS(6), SORA(5), TIAGRA(4), _105(3), ULTEGRA(2), ULTEGRA_DI2(2), DURA_ACE(1), DURA_ACE_DI2(1),
    RIVAL(3), FORCE(2), RED(1), RED_ETAP(1);

    static final int NUM_RANKS = 6;
    static Color[] rankColors = new Color[NUM_RANKS];

    int rank;

    Groupset(int rank) {
        this.rank = rank;
    }

    public static Groupset parseString(String s) {
        if (s.equals("105")) return _105;
        return Groupset.valueOf(s.replaceAll(" |-", "_").toUpperCase());
    }

    public static void initRankColors() {
        for (int i = 0; i < NUM_RANKS; i++) {
            rankColors[i] = blendColors(Color.red, Color.blue, i / (float) NUM_RANKS);
        }
    }

    public Color getColor() {
        return rankColors[this.rank];
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
