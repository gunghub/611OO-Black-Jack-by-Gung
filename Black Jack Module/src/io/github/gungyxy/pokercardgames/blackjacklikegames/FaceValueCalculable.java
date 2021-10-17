package io.github.gungyxy.pokercardgames.blackjacklikegames;

import io.github.gungyxy.pokercardgames.PokerCard;

import java.util.List;


public interface FaceValueCalculable {

    /**
     *
     * @return face value
     */
    int convert(int rankCode);
    int getTotalFaceValue(List<PokerCard> handOfPokerCards);
    boolean isNatural(List <PokerCard> handOfPokerCards);

}
