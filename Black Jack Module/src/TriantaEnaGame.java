import java.util.*;



public class TriantaEnaGame extends BlackJackLikeGame {

    /**
     * Singleton Pattern
     */
    private TriantaEnaGame() {
    }
    private static TriantaEnaGame instance = null;

    public static TriantaEnaGame getInstance() {
        if(instance==null) instance=new TriantaEnaGame();
        return instance;
    }

    public static final int BET_TIMES=3;
    public static final int BUST_THRESHOLD=31;
    public static final int DEALER_HIT_THRESHOLD=27;
    private int MIN_BET=1;
    private int MAX_BET=10000;
    private static final FaceValueCalculable faceValueCalculable=new TriantaEnaFaceValueCalculator();


    public void initialize(){
        players=new ArrayList<>();
        int numberOfLiveUsers= Utility.getIntegerFromUser("Please input the number of live users :", 3, 9);//todo
        for(int i=0;i<numberOfLiveUsers;i++) {
            players.add(new TriantaEnaPlayer(i));
        }
    }

    public void playOneRound(){


        //choose the dealer for this round
        ChooseDealerStrategy chooseDealerStrategy=new ChooseDealerStrategyImplOrder();
        dealer=chooseDealerStrategy.chooseDealer(players);

        /**
         * The game is played using two standard 52 card decks.
         */
        Stack<PokerCard> pokerCardDeck= PokerCardGameUtility.getAPokerCardDeck();
        pokerCardDeck.addAll(PokerCardGameUtility.getAPokerCardDeck());
        dealer.setPokerCardDeck(pokerCardDeck);


        /**
         * To start each round, the Dealer deals one card to each player.
         * The card that the Players are dealt is kept face down and known only to each Player.
         */

        for (OrdinaryPlayer player :players
                ) {
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;//skip dealer
            player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_DOWN));
        }


        /**
         * The first card the Dealer is dealt is kept face up and known to all the Players and the Dealer.
         *
         */
        dealer.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_UP));




        /**
         * Each player begins with the same amount of money;
         * the player who is the Banker, must begin with three times the amount of the Players.
         * In other words, if the players begins with $100.00 each, the Banker must have $300.00.
         *
         *
         * After each player receives their first card, they place their bet or choose to fold without betting.
         *
         *
         */
        Set<OrdinaryPlayer> playersThatStood=new HashSet<>();


        printTable(playersThatStood,PokerCard.FACE_DOWN);
        for (OrdinaryPlayer player :
                players) {
            player.printHandsOfPokerCards();
        }

        int playerBet=Utility.getIntegerFromUser("Please input the non-dealer player's bet amount for this round", MIN_BET,MAX_BET);
        Set<OrdinaryPlayer> playersThatFold=new HashSet<>();
        bets=new TreeMap<>();
        for (OrdinaryPlayer player :players
                ) {
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;//skip dealer

            int inputInteger=Utility.getIntegerFromUser("Would Player "+player.getSerialNumber()+" like to fold? 1 for yes, 2 for no",1,2);
            if(inputInteger==1){ // choose to fold
                playersThatFold.add(player);
                continue;
            }

            bets.put(player.getSerialNumber(),new HashMap<>());
            bets.get(player.getSerialNumber()).put(0,player.bet(playerBet));
        }
        if(playersThatFold.size()+1==players.size()){
            Utility.promptUser("All chose to fold");
            return;
        } // all fold

        bets.put(dealer.getSerialNumber(),new HashMap<>());
        bets.get(dealer.getSerialNumber()).put(0,dealer.bet(BET_TIMES*playerBet));

        /**
         *
         * Once all bets have been made, each player with a standing bet receives two more cards face up.
         */

        for (OrdinaryPlayer player :players
                ) {
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;//skip dealer
            if(playersThatFold.contains(player)) continue;//skip the ones who fold

            player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_UP));
            player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_UP));
        }


        /**
         * each Player in turn may take one of the following actions:
         ∙ Hit : The Players continue to receive another card
         ∙ Stand : The Player ends and maintains the value of the current hand
         * until all the Players stand or have gone bust
         */
        while(true){
            boolean allStood=true; //Until all non-dealer players stand

            for (OrdinaryPlayer player:players
                    ) {
                if(player.getSerialNumber()==dealer.getSerialNumber())continue;// skip dealer
                if(playersThatFold.contains(player))continue;//skip ones who fold

                printTable(playersThatStood, playersThatFold);
                player.printHandsOfPokerCards();


                while(true) {
                    boolean actionSuccessful=false;
                    Action action = player.act();
                    if (action == Action.HIT) {
                        actionSuccessful = this.processHit(player, dealer);

                    }else if (action == Action.STAND) {
                        //The Player ends and maintains the value of the current hand
                        actionSuccessful=processStand(playersThatStood, player);
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
         * Once all the Players stand or have gone bust, the dealer reveals their face down card to the Players,
         * and continues to take a hit until the hand value of the Banker reaches or exceeds 27.
         */

        while(true){
            boolean dealerStood=false;
            while(true){
                boolean actionSuccessful=ACTION_FAILED;
                printTable(playersThatStood,playersThatFold,PokerCard.FACE_UP);
                Action action = dealer.act();

                if (action == Action.HIT) {
                    actionSuccessful = processHit(dealer, dealer);

                } else if (action == Action.STAND) {
                    if(faceValueCalculable.getTotalFaceValue(dealer.getHandsOfPokerCards().get(0))<DEALER_HIT_THRESHOLD){
                        Utility.promptUser(Utility.ANSI_RED+"Sorry, your face value didn't get to the "+DEALER_HIT_THRESHOLD+", please go on hitting."+Utility.ANSI_RESET);
                        actionSuccessful=false;
                        dealerStood=false;
                    }else{
                        dealerStood = true;
                        actionSuccessful = ACTION_SUCCESSFUL;
                    }

                }
                if(actionSuccessful)break;
            }
            if(dealerStood) break;
        }

        printTable(playersThatStood,playersThatFold,PokerCard.FACE_UP);

        /**
         *
         * find the winner
         *
         */

        printTable(playersThatStood,true);
        int dealerTotalFaceValue=faceValueCalculable.getTotalFaceValue(dealer.getHandsOfPokerCards().get(0));
        boolean dealerBust=(dealerTotalFaceValue>BUST_THRESHOLD);
        boolean dealerIsNatural=faceValueCalculable.isNatural(dealer.getHandsOfPokerCards().get(0));
        Utility.promptUser("Dealer's Total Face Value: "+dealerTotalFaceValue);

        for (int serialNumber=0;serialNumber< players.size();serialNumber++) {

            OrdinaryPlayer player=players.get(serialNumber);
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;// skip dealer
            if(playersThatFold.contains(player))continue;//skip ones who fold


            for(int i=0;i<player.getHandsOfPokerCards().size();i++){
                List<PokerCard> handOfPokerCards=player.getHandsOfPokerCards().get(i);
                int playerTotalFaceValue=faceValueCalculable.getTotalFaceValue(handOfPokerCards);
                boolean playerBust=(playerTotalFaceValue>BUST_THRESHOLD);
                boolean playerIsNatural=faceValueCalculable.isNatural(handOfPokerCards);

                GameResult resultForPlayer = getGameResult(dealerTotalFaceValue, dealerBust, dealerIsNatural, playerTotalFaceValue, playerBust, playerIsNatural);


                String printResult="";
                if(resultForPlayer==GameResult.WIN){
                    player.setAmountOfMoney(player.getAmountOfMoney()+bets.get(serialNumber).get(i)*2);
                    printResult=Utility.ANSI_GREEN+"WIN"+Utility.ANSI_RESET;
                }

                if(resultForPlayer==GameResult.DRAW){

                    printResult=Utility.ANSI_YELLOW+"DRAW"+Utility.ANSI_RESET;
                }

                if(resultForPlayer==GameResult.LOSE){
                    player.setAmountOfMoney(player.getAmountOfMoney()+bets.get(serialNumber).get(i));
                    printResult=Utility.ANSI_RED+"LOSE"+Utility.ANSI_RESET;
                }

                Utility.promptUser("Player "+player.getSerialNumber()+" No."+i+" hand of poker cards total face value is "+playerTotalFaceValue+". "+printResult);
            }
        }


    }




}
