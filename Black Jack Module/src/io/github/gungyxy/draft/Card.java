package io.github.gungyxy.draft;

import java.util.Collections;
import java.util.Stack;

public class Card {

    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;
    public final static int ACE = 1;
    public final static String[] RANKS={"INVALID","Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};

    private int rank;
    public FaceValueConvertible faceValueConverter;
    private boolean faceUp=false;

    public Card(int rank, FaceValueConvertible faceValueConverter) {
        this.faceValueConverter = faceValueConverter;
        this.rank=rank;//todo 范围
    }

    public int getFaceValue(){
        return faceValueConverter.getFaceValueFromRank(rank);

    }

    public static Stack<Card> get52CardsRandom(FaceValueConvertible faceValueConverter){

        Stack<Card> cardStack=new Stack<>();

        for(int rank=1;rank<=13;rank++){
            for(int j=1;j<=4;j++){
                cardStack.push(new Card(rank,faceValueConverter));
            }
        }

        Collections.shuffle(cardStack);

        return cardStack;
    }


    public int getRank() {
        return rank;
    }

    public void setFaceUp( boolean faceUp){
        this.faceUp=faceUp;
    }

    public String peek(){
        if(faceUp) return RANKS[rank];else return "FACE_DOWN";
    }


}
