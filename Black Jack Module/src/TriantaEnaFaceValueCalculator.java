import java.util.List;



public class TriantaEnaFaceValueCalculator implements FaceValueCalculable {
    private static final int[] FACE_VALUES ={Integer.MIN_VALUE,11,2,3,4,5,6,7,8,9,10,10,10,10};
    private static final int ACE_ALTERNATIVE_VALUE=1;

    @Override
    public int convert(int rankCode){
        return FACE_VALUES[rankCode];
    }

    @Override
    /**
     * If the hand consists of one Ace, the player can choose to count it as a 1 or an 11.
     * If the hand consists of more than one Ace, only one Ace can count as 1. All others have a value of 11.

     */
    public int getTotalFaceValue(List<PokerCard> handOfPokerCards){
        int totalFaceValue=0;
        int aceNumber=0;
        for (PokerCard pokerCard :
                handOfPokerCards) {
            totalFaceValue+= FACE_VALUES[pokerCard.getRankCode()];
            if (pokerCard.getRankCode()==1) aceNumber++;
        }

        if(aceNumber>=1){

            if(totalFaceValue> TriantaEnaGame.BUST_THRESHOLD){
                totalFaceValue-= FACE_VALUES[1]-ACE_ALTERNATIVE_VALUE;
            }
        }

        return totalFaceValue;
    }

    //todo

    /**
     * A special case includes natural Trianta Ena versus the hand value of a 31.
     * A natural Trianta Ena is defined as a hand having a value of 31 (i.e. an Ace and two face cards).
     * While a value of 31 can be any amount of cards (e.g. a 9, 2, and a face card).
     * Natural Trianta Ena always triumphs over a value of 31.
     * A natural 31 of the Banker results in the Banker winning the bets from all players.

     * @param handOfPokerCards
     * @return
     */
    public boolean isNatural(List <PokerCard> handOfPokerCards){
        int NUMBER_OF_NATURAL_POKER_CARDS=3;

        if(getTotalFaceValue(handOfPokerCards)!= TriantaEnaGame.BUST_THRESHOLD) return false;
        if(handOfPokerCards.size()!=NUMBER_OF_NATURAL_POKER_CARDS) return false;

        if(handOfPokerCards.get(0).getRankCode()==PokerCard.ACE&&handOfPokerCards.get(1).getRankCode()>=PokerCard.JACK&&handOfPokerCards.get(2).getRankCode()>=PokerCard.JACK) return true;
        if(handOfPokerCards.get(1).getRankCode()==PokerCard.ACE&&handOfPokerCards.get(0).getRankCode()>=PokerCard.JACK&&handOfPokerCards.get(2).getRankCode()>=PokerCard.JACK) return true;
        if(handOfPokerCards.get(2).getRankCode()==PokerCard.ACE&&handOfPokerCards.get(0).getRankCode()>=PokerCard.JACK&&handOfPokerCards.get(1).getRankCode()>=PokerCard.JACK) return true;
        return false;
    }


}
