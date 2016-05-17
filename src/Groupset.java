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

}
