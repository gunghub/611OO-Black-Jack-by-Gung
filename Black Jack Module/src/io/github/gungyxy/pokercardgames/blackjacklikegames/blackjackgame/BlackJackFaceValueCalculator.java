package io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame;

import io.github.gungyxy.pokercardgames.PokerCard;
import io.github.gungyxy.pokercardgames.blackjacklikegames.FaceValueCalculable;

import java.util.List;

import static io.github.gungyxy.pokercardgames.PokerCard.ACE;
import static io.github.gungyxy.pokercardgames.PokerCard.JACK;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame.BlackJackGame.BUST_THRESHOLD;


public class BlackJackFaceValueCalculator implements FaceValueCalculable {
    private static final int[] FACE_VALUES ={Integer.MIN_VALUE,1,2,3,4,5,6,7,8,9,10,10,10,10};
    private static final int ACE_ALTERNATIVE_VALUE=11;

    @Override
    public int convert(int rankCode){
            return FACE_VALUES[rankCode];
    }

    @Override
    public int getTotalFaceValue(List<PokerCard> handOfPokerCards){
        int totalFaceValue=0;
        int aceNumber=0;
        for (PokerCard pokerCard :
                handOfPokerCards) {
            totalFaceValue+= FACE_VALUES[pokerCard.getRankCode()];
            if (pokerCard.getRankCode()==1) aceNumber++;
        }
        for(int i=0;i<aceNumber;i++){
            if(totalFaceValue+ACE_ALTERNATIVE_VALUE-FACE_VALUES[1]<= BUST_THRESHOLD){
                totalFaceValue+=ACE_ALTERNATIVE_VALUE-FACE_VALUES[1];
            }
        }
        return totalFaceValue;
    }

    @Override
    /**
     * A special case includes natural Blackjacks versus the value of a 21.
     * A natural Blackjack is defined as the starting hand having a value of 21 (i.e. an Ace and any face card).
     * While a value of 21 can be any amount of cards (e.g. a 9, 2, and a face card).
     * Natural Blackjacks always triumphs over a value of 21.
     */
    public boolean isNatural(List <PokerCard> handOfPokerCards){
        int NUMBER_OF_NATURAL_POKER_CARDS=2;

        if(getTotalFaceValue(handOfPokerCards)!=BUST_THRESHOLD) return false;
        if(handOfPokerCards.size()!=NUMBER_OF_NATURAL_POKER_CARDS) return false;
        if(handOfPokerCards.get(0).getRankCode()==ACE&&handOfPokerCards.get(1).getRankCode()>=JACK) return true;
        if(handOfPokerCards.get(1).getRankCode()==ACE&&handOfPokerCards.get(0).getRankCode()>=JACK) return true;
        return false;
    }
}
