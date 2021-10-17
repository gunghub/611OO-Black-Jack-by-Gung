package io.github.gungyxy.pokercardgames.blackjacklikegames;

import io.github.gungyxy.pokercardgames.PokerCard;

import java.util.List;

import static io.github.gungyxy.Utility.getIntegerFromUser;

/**
 * Created by gung on 10/15/21.
 */
public interface OrdinaryPlayer {
    void receiveAPokerCard(PokerCard pokerCard);
    void receiveAPokerCard(PokerCard pokerCard, int handNumber);
    void printHandsOfPokerCards();
    int getSerialNumber();
    int getAmountOfMoney();
    int bet(int specificMoney);
    int bet(int lowerBound, int upperBound);
    void setAmountOfMoneyByUser();
    List<List<PokerCard>> getHandsOfPokerCards();
    Action act();
    void setAmountOfMoney(int amountOfMoney);

}
