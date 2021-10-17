package io.github.gungyxy.pokercardgames.blackjacklikegames;

import io.github.gungyxy.Utility;
import io.github.gungyxy.pokercardgames.PokerCard;
import io.github.gungyxy.pokercardgames.PokerCardGame;
import io.github.gungyxy.pokercardgames.blackjacklikegames.triantaenagame.TriantaEnaFaceValueCalculator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.gungyxy.Utility.*;
import static io.github.gungyxy.pokercardgames.PokerCard.FACE_DOWN;


public abstract class BlackJackLikeGame extends PokerCardGame {
    protected Map<Integer,Map<Integer, Integer>> bets;
    protected List<OrdinaryPlayer> players;
    protected Dealer dealer;
    protected FaceValueCalculable faceValueCalculable=new TriantaEnaFaceValueCalculator();
    protected boolean humanVersusMachine=false;
    public static final boolean ACTION_SUCCESSFUL=true;
    public static final boolean ACTION_FAILED=false;

    public void printTable(Set<OrdinaryPlayer> playersThatStood, Set<OrdinaryPlayer> playersThatFold, boolean allFaceUp){


        promptUser(ANSI_BLUE+"----------------------------------------------------------------");
        for (OrdinaryPlayer player:players
                ) {

            if(playersThatFold!=null&&playersThatFold.contains(player)){continue;}
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("|");
            if (player.getSerialNumber()==dealer.getSerialNumber()) stringBuilder.append("Dealer ");
            stringBuilder.append("Player ").append(player.getSerialNumber()).append(" has ");
            for (List<PokerCard> handOfPokerCards:player.getHandsOfPokerCards()
                    ) {
                stringBuilder.append("[");
                for (PokerCard pokerCard :
                        handOfPokerCards) {
                    if(allFaceUp) stringBuilder.append(pokerCard.peekedByOwner()).append(" ");
                    else stringBuilder.append(pokerCard.peekedByNonOwner()).append(" ");
                }
                stringBuilder.append("] ");
            }
            if(playersThatStood.contains(player)) stringBuilder.append(" STOOD");
            promptUser(stringBuilder.toString());
        }
        promptUser("----------------------------------------------------------------"+ANSI_RESET);

    }

    public void printTable(Set<OrdinaryPlayer> playersThatStood,  boolean allFaceUp){
        printTable(playersThatStood,null,allFaceUp);
    }



    public void printTable(Set<OrdinaryPlayer> playersThatStood, Set<OrdinaryPlayer> playersThatFold ){
        printTable(playersThatStood,playersThatFold,false);
    }


    public void printTable(Set<OrdinaryPlayer> playersThatStood){
        printTable(playersThatStood,null,false);

    }

    public void playSeveralRounds(){
        while(true) {
            playOneRound();
            if(2== Utility.getIntegerFromUser("Do you want to play next round? 1 for Yes, 2 for No",1,2)){
                break;
            };
        }
    }

    public void playOneRound(){
    }

    public void summaryAndQuit(){
        promptUser("quiting...");
    }


    public boolean processHit(OrdinaryPlayer player, Dealer dealer) {

        int handNumber;
        if(player.getHandsOfPokerCards().size()==1){
            handNumber=0;
        }else{
            handNumber=getIntegerFromUser("Please input which hand of poker cards you want to add the new card to",0,player.getHandsOfPokerCards().size()-1);
        }
        PokerCard pokerCard=dealer.dealAPokerCard(FACE_DOWN);
        promptUser("Hey, Player "+player.getSerialNumber()+", you got " +pokerCard.peekedByOwner());
        player.receiveAPokerCard(pokerCard,handNumber);
        return ACTION_SUCCESSFUL;
    }


    public boolean processStand(Set<OrdinaryPlayer> playersThatStood, OrdinaryPlayer player) {
        playersThatStood.add(player);
        return ACTION_SUCCESSFUL;
    }

}
