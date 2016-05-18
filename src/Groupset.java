import java.awt.*;

enum Groupset {
    CLARIS, SORA, TIAGRA, _105, ULTEGRA, ULTEGRA_DI2, DURA_ACE, DURA_ACE_DI2,
    RIVAL, FORCE, RED, RED_ETAP;


    public static Groupset parseString(String s){
        if(s.equals("105")) return _105;
        return Groupset.valueOf(s.replaceAll(" |-", "_").toUpperCase());
    }

    public int getRank(){

        /*
        Rival == 105
        force == ultegra
        red == dura ace
        */


        int rank = this.ordinal();
        if(rank>7){
            rank = -1;
        }

        return rank;
    }

    public static Color[] getGradient(){
//        int len = Groupset.values().length;
        int len = 6;
        Color[] cs = new Color[len];

        for (int i = 0; i < len; i++) {
            cs[i] = blendColors(Color.red, Color.blue, i/(float)len);
        }

        return cs;
    }

    // http://stackoverflow.com/a/17544598
    private static Color blendColors(Color x, Color y, float bl) {
        if (bl < 0 || bl > 1) throw new IllegalArgumentException("blend factor must be between 0 and 1");
        float inv = 1 - bl;

        float r = x.getRed() * bl + y.getRed() * inv;
        float g = x.getGreen() * bl + y.getGreen() * inv;
        float b = x.getBlue() * bl + y.getBlue() * inv;

        return new Color(r/255, g/255, b/255);
    }

}
