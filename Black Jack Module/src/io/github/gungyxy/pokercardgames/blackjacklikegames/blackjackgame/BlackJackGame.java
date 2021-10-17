package io.github.gungyxy.pokercardgames.blackjacklikegames.blackjackgame;

import io.github.gungyxy.Utility;
import io.github.gungyxy.pokercardgames.PokerCard;
import io.github.gungyxy.pokercardgames.blackjacklikegames.*;

import java.util.*;

import static io.github.gungyxy.Utility.*;
import static io.github.gungyxy.pokercardgames.PokerCard.*;
import static io.github.gungyxy.pokercardgames.PokerCardGameUtility.*;
import static io.github.gungyxy.pokercardgames.blackjacklikegames.Action.*;


public class BlackJackGame extends BlackJackLikeGame {

    public static final int BUST_THRESHOLD=21;
    public static final int DEALER_HIT_THRESHOLD=17;
    private static final FaceValueCalculable faceValueCalculable=new BlackJackFaceValueCalculator();
    /**
     * Singleton Pattern
     */

    private int MIN_BET=1;
    private int MAX_BET=10000;


    private BlackJackGame() {
    }
    private static BlackJackGame instance = null;
    public static BlackJackGame getInstance() {
        if(instance==null) instance=new BlackJackGame();
        return instance;
    }

    public void start(){
        initialize();
        playSeveralRounds();
        summaryAndQuit();
    }

    public void initialize(){
        players=new ArrayList<>();
        int numberOfLiveUsers= getIntegerFromUser("Please input the number of live users (1 to 10):", 1, 10);//todo
        if(numberOfLiveUsers==1){
            humanVersusMachine=true;
            players.add(new BlackJackPlayer(0));
            BlackJackPlayer automaticDealer =new BlackJackPlayer(1,true);
            players.add(automaticDealer);
            dealer=automaticDealer;
        }else{
            for(int i=0;i<numberOfLiveUsers;i++){
                players.add(new BlackJackPlayer(i));
            }
        }
    };

    public void playOneRound(){

        //choose the dealer for this round
        if(!humanVersusMachine){
            int randomSerialNumber= Utility.randomInteger(0,players.size()-1);
            dealer=(Dealer) players.get(randomSerialNumber);
        }
        promptUser("Round Begin! Please note that Player "+dealer.getSerialNumber() +" is the dealer this round.");
        dealer.setPokerCardDeck(getAPokerCardDeck());




        //bet
        bets=new TreeMap<>();
        for (OrdinaryPlayer player :players
                ) {
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;//skip dealer
            bets.put(player.getSerialNumber(),new HashMap<>());
            bets.get(player.getSerialNumber()).put(0,player.bet(MIN_BET,MAX_BET));
        }



        /**
         *  the Dealer deals two cards to each player in alternating sequence.
         *  Both of the cards that the Player is dealt are kept face up and known to both the Dealer and the Player.
         */
        for (OrdinaryPlayer player :players
                ) {
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;//skip dealer
            player.receiveAPokerCard(dealer.dealAPokerCard(FACE_UP));
            player.receiveAPokerCard(dealer.dealAPokerCard(FACE_UP));
        }

        /**
         * The first card the Dealer is dealt is kept face up and known to both the Player and the Dealer;
         * the second is kept face down and only known to the Dealer.
         */
        dealer.receiveAPokerCard(dealer.dealAPokerCard(FACE_UP));
        dealer.receiveAPokerCard(dealer.dealAPokerCard(FACE_DOWN));


        /**
         * The Player begins and may take one of the following actions: HIT STAND SPLIT DOUBLE UP
         */

        Set<OrdinaryPlayer> playersThatStood=new HashSet<>();

        while(true){
            boolean allStood=true; //Until all non-dealer players stand

            for (OrdinaryPlayer player:players
                 ) {
                if(player.getSerialNumber()==dealer.getSerialNumber())continue;// skip dealer

                printTable(playersThatStood);
                player.printHandsOfPokerCards();


                while(true) {
                    boolean actionSuccessful;
                    Action action = player.act();
                    if (action == HIT) {
                        actionSuccessful = processHit(player, dealer);

                    }else if (action == STAND) {
                        //The Player ends and maintains the value of the current hand
                        actionSuccessful=processStand(playersThatStood, player);
                    }else if (action == SPLIT) {
                        //Only available if the Player has two cards of the same rank.
                        // The Player splits their hand into two separate hands,
                        // and must place a bet on the other hand equal to their original bet.
                        // The Dealer gives a single new card to each of these new hands.
                        // Each of these hands is treated as their own separate value.

                        actionSuccessful=processSplit(player,bets,dealer);
                    }else if (action == DOUBLE_UP) {
                        /**
                         * The Player doubles their bet, and takes only a
                         single hit and immediately stands afterwards.
                         */
                        actionSuccessful=processDoubleUp(player,playersThatStood,dealer,bets);

                    }else{
                        actionSuccessful=ACTION_FAILED;
                    }


                    if(actionSuccessful) break;
                }

                if(!playersThatStood.contains(player)){
                    allStood=false;
                }
            }

            if(allStood) break;
        }

        /**
         * Once the Player stands, the dealer reveals their face down card to the Player,
         * and continues to hit until the hand value of the Dealer reaches or exceeds 17.
         */
        dealer.printHandsOfPokerCards();
        while(true){
            boolean dealerStood=false;
            while(true){
                boolean actionSuccessful=ACTION_FAILED;
                printTable(playersThatStood);
                Action action = dealer.act();

                if (action == HIT) {
                    actionSuccessful = processHit(dealer, dealer);

                } else if (action == STAND) {
                    if(faceValueCalculable.getTotalFaceValue(dealer.getHandsOfPokerCards().get(0))<DEALER_HIT_THRESHOLD){
                        promptUser(ANSI_RED+"Sorry, your face value didn't get to the "+DEALER_HIT_THRESHOLD+", please go on hitting."+ANSI_RESET);
                        actionSuccessful=false;
                        dealerStood=false;
                    }else{
                        actionSuccessful = ACTION_SUCCESSFUL;
                        dealerStood = true;
                    }

                } else if (action == SPLIT) {

                    promptUser("Sorry, as a dealer, you cannot split.");
                    actionSuccessful = ACTION_FAILED;
                } else if (action == DOUBLE_UP) {
                    /**
                     * The Player doubles their bet, and takes only a
                     single hit and immediately stands afterwards.
                     */
                    promptUser("Sorry, as a dealer, you cannot double up");
                    actionSuccessful = ACTION_FAILED;

                }


                if(actionSuccessful)break;

            }

            if(dealerStood) break;
        }
        dealer.printHandsOfPokerCards();

        /**
         *
         * find the winner
         *
         */

        printTable(playersThatStood,true);
        int dealerTotalFaceValue=faceValueCalculable.getTotalFaceValue(dealer.getHandsOfPokerCards().get(0));
        boolean dealerBust=(dealerTotalFaceValue>BUST_THRESHOLD);
        boolean dealerIsNatural=faceValueCalculable.isNatural(dealer.getHandsOfPokerCards().get(0));
        promptUser("Dealer's Total Face Value: "+dealerTotalFaceValue);

        for (int serialNumber=0;serialNumber< players.size();serialNumber++) {

            OrdinaryPlayer player=players.get(serialNumber);
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;// skip dealer


            for(int i=0;i<player.getHandsOfPokerCards().size();i++){
                List<PokerCard> handOfPokerCards=player.getHandsOfPokerCards().get(i);
                int playerTotalFaceValue=faceValueCalculable.getTotalFaceValue(handOfPokerCards);
                boolean playerBust=(playerTotalFaceValue>BUST_THRESHOLD);
                boolean playerIsNatural= faceValueCalculable.isNatural(handOfPokerCards);

                String result="";
                if(playerBust&&dealerBust){
                    result="DRAW";
                }else if(playerBust){
                    result="LOSE";
                }else if(dealerBust){
                    result="WIN";
                }else{// neither bust
                    if(playerTotalFaceValue>dealerTotalFaceValue){
                        result="WIN";
                    }
                    if(playerTotalFaceValue<dealerTotalFaceValue){
                        result="LOSE";
                    }
                    if(playerTotalFaceValue==dealerTotalFaceValue){
                        if(playerIsNatural) result="LOSE";
                        else if(dealerIsNatural) result="LOSE";
                        else {
                            result="DRAW";
                        }
                    }
                }


                if(result.equals("WIN")){
                    player.setAmountOfMoney(player.getAmountOfMoney()+bets.get(serialNumber).get(i)*2);
                    result=ANSI_GREEN+"WIN"+ANSI_RESET;
                }

                if(result.equals("DRAW")){
                    try {
                        player.setAmountOfMoney(player.getAmountOfMoney() + bets.get(serialNumber).get(i));
                    }catch(Exception e){

                    }
                    result=ANSI_YELLOW+"DRAW"+ANSI_RESET;
                }

                if(result.equals("LOST")){
                    player.setAmountOfMoney(player.getAmountOfMoney()+bets.get(serialNumber).get(i));
                    result=ANSI_RED+"DRAW"+ANSI_RESET;
                }

                promptUser("Player "+player.getSerialNumber()+" No."+i+" hand of poker cards total face value is "+playerTotalFaceValue+". "+result);
            }
        }



    }





    private boolean processDoubleUp(OrdinaryPlayer player,Set<OrdinaryPlayer> playersThatStood, Dealer dealer,Map<Integer,Map<Integer, Integer>> bets) {
        //double bet
        Map<Integer, Integer> originalBets=bets.get(player.getSerialNumber());
        for(Map.Entry<Integer, Integer> HandIndexAndBet : originalBets.entrySet()){
            player.bet(HandIndexAndBet.getValue());
            HandIndexAndBet.setValue(HandIndexAndBet.getValue()*2);
        }


        //hit
        int handNumber;
        if(player.getHandsOfPokerCards().size()==1){
            handNumber=0;
        }else{
            handNumber=getIntegerFromUser("Please input which hand of poker cards you want to add the new card to",0,player.getHandsOfPokerCards().size()-1);
        }
        PokerCard pokerCard=dealer.dealAPokerCard(FACE_DOWN);
        promptUser("Hey, Player "+player.getSerialNumber()+", you got " +pokerCard.peekedByOwner());
        player.receiveAPokerCard(pokerCard,handNumber);


        //stand
        playersThatStood.add(player);


        return ACTION_SUCCESSFUL;
    }





    public boolean processSplit(OrdinaryPlayer player, Map<Integer, Map<Integer,Integer>> bets, Dealer dealer){
        //Only available if the Player has two cards of the same rank.
        // Each of these hands is treated as their own separate value.


        List<List<PokerCard>> handsOfPokerCards=player.getHandsOfPokerCards();
        if(handsOfPokerCards.size()>1){
            promptUser(ANSI_RED+"Sorry, you've already split."+ANSI_RESET);
            return ACTION_FAILED;
        }

        if(handsOfPokerCards.get(0).size()>2){
            promptUser(ANSI_RED+"Sorry, Split only available at the beginning"+ANSI_RESET);
            return ACTION_FAILED;
        }

        if(handsOfPokerCards.get(0).get(0).getRankCode()!=handsOfPokerCards.get(0).get(1).getRankCode()){
            promptUser(ANSI_RED+"Sorry, Split only available if the Player has two cards of the same rank."+ANSI_RESET);
            return ACTION_FAILED;
        }


        // The Player splits their hand into two separate hands,
        handsOfPokerCards.add(1,new ArrayList<>());
        handsOfPokerCards.get(1).add(handsOfPokerCards.get(0).remove(1));

        // and must place a bet on the other hand equal to their original bet.
        int firstBet=bets.get(player.getSerialNumber()).get(0);
        bets.get(player.getSerialNumber()).put(1,player.bet(firstBet));

        // The Dealer gives a single new card to each of these new hands.
        player.receiveAPokerCard(dealer.dealAPokerCard(FACE_DOWN),0);
        player.receiveAPokerCard(dealer.dealAPokerCard(FACE_DOWN),1);
        return ACTION_SUCCESSFUL;
    }


}