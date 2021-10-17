package io.github.gungyxy.pokercardgames.blackjacklikegames;

import io.github.gungyxy.pokercardgames.PokerCard;

import java.util.Stack;

/**
 * Created by gung on 10/15/21.
 */
public interface Dealer extends OrdinaryPlayer {

    boolean isAutomatic();
    PokerCard dealAPokerCard();
    PokerCard dealAPokerCard(boolean faceOrientation);
    Stack<PokerCard> getPokerCardDeck();
    void setPokerCardDeck(Stack<PokerCard> pokerCardDeck);
}
