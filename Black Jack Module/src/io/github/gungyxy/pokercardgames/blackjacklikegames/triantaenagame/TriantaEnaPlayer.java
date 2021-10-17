package io.github.gungyxy.pokercardgames.blackjacklikegames.triantaenagame;

import io.github.gungyxy.pokercardgames.blackjacklikegames.Action;
import io.github.gungyxy.pokercardgames.blackjacklikegames.BlackJackLikePlayer;
import io.github.gungyxy.pokercardgames.blackjacklikegames.FaceValueCalculable;
import io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame.BlackJackFaceValueCalculator;

import static io.github.gungyxy.Utility.getIntegerFromUser;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.Action.HIT;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.Action.STAND;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame.BlackJackGame.DEALER_HIT_THRESHOLD;


public class TriantaEnaPlayer extends BlackJackLikePlayer {

    public TriantaEnaPlayer(int serialNumber){
        super(serialNumber);
    }

    public Action act(){

        if(!automatic) {
            int inputNumber = getIntegerFromUser("Player " + serialNumber + ", Please input your action, 1 for hit, 2 for stand.", 1, 2);
            switch (inputNumber) {
                case 1:
                    return HIT;
                case 2:
                    return Action.STAND;
            }
            return null;
        }

        return null;

    }

}
