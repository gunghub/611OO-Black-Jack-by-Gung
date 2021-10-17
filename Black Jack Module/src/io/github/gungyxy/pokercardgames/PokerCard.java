package io.github.gungyxy.pokercardgames;


public class PokerCard {
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;
    public final static int ACE = 1;
    public final static String[] RANKS_PRINTED={"ERROR!","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    public final static boolean FACE_UP=true;
    public final static boolean FACE_DOWN=false;
    public final static int NUMBER_OF_RANKS=13;
    public final static String INVISIBLE="INVISIBLE";

    private int rankCode; /* 1 for A, 11 for J, 12 for Q, 13 for K, 2 to 10 for 2 to 10*/
    private PokerCardSuit pokerCardSuit;
    private boolean faceOrientation;

    private PokerCard(){}

    public PokerCard(int rankCode, boolean faceOrientation, PokerCardSuit pokerCardSuit){
        this.rankCode = rankCode;
        this.faceOrientation = faceOrientation;
        this.pokerCardSuit=pokerCardSuit;
    }

    public int getRankCode(){
        return rankCode;
    }

    /**
     * peeked by others, which are NOT the owner of the pock card
     * @return
     */
    public String peekedByNonOwner(){
        if (faceOrientation==FACE_UP) return RANKS_PRINTED[rankCode]+"-"+ pokerCardSuit;
        else return INVISIBLE;
    }

    public String peekedByOwner(){
        return RANKS_PRINTED[rankCode]+"-"+ pokerCardSuit.toString();
    }

    public boolean isFaceOrientation() {
        return faceOrientation;
    }

    public void setFaceOrientation(boolean faceOrientation) {
        this.faceOrientation = faceOrientation;
    }
}
