package io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame;

import io.github.gungyxy.pokercardgames.PokerCard;
import io.github.gungyxy.pokercardgames.blackjacklikegames.Action;
import io.github.gungyxy.pokercardgames.blackjacklikegames.BlackJackLikePlayer;
import io.github.gungyxy.pokercardgames.blackjacklikegames.FaceValueCalculable;

import static io.github.gungyxy.Utility.getIntegerFromUser;
import static io.github.gungyxy.Utility.promptUser;
import static io.github.gungyxy.pokercardgames.PokerCard.FACE_DOWN;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.Action.HIT;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.Action.STAND;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame.BlackJackGame.DEALER_HIT_THRESHOLD;
import static sun.audio.AudioPlayer.player;

/**
 * Created by gung on 10/15/21.
 */
public class BlackJackPlayer extends BlackJackLikePlayer {

    public BlackJackPlayer(int serialNumber, boolean automatic){
        super(serialNumber,automatic);
    }

    public BlackJackPlayer(int serialNumber){
        this(serialNumber,false);
    }


    public Action act(){

        if(!automatic) {
            int inputNumber = getIntegerFromUser("Player " + serialNumber + ", Please input your action, 1 for hit, 2 for stand, 3 for split, 4 for double up.", 1, 4);
            switch (inputNumber) {
                case 1:
                    return HIT;
                case 2:
                    return Action.STAND;
                case 3:
                    return Action.SPLIT;
                case 4:
                    return Action.DOUBLE_UP;
            }
            return null;
        }

        if(automatic) {
            FaceValueCalculable faceValueCalculable=new BlackJackFaceValueCalculator();
            while(faceValueCalculable.getTotalFaceValue(this.getHandsOfPokerCards().get(0))<DEALER_HIT_THRESHOLD){
                return HIT;
            }
            return STAND;
        }
        return null;

    }

}
