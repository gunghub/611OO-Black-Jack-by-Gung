import java.util.*;



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

    public void initialize(){
        players=new ArrayList<>();
        int numberOfLiveUsers= Utility.getIntegerFromUser("Please input the number of live users (1 to 10):", 1, 10);//todo
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
        ChooseDealerStrategy chooseDealerStrategy=new ChooseDealerStrategyImplRandom();
        dealer=chooseDealerStrategy.chooseDealer(players,humanVersusMachine);




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
            player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_UP));
            player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_UP));
        }

        /**
         * The first card the Dealer is dealt is kept face up and known to both the Player and the Dealer;
         * the second is kept face down and only known to the Dealer.
         */
        dealer.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_UP));
        dealer.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_DOWN));


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
                    if (action == Action.HIT) {
                        actionSuccessful = processHit(player, dealer);

                    }else if (action == Action.STAND) {
                        //The Player ends and maintains the value of the current hand
                        actionSuccessful=processStand(playersThatStood, player);
                    }else if (action == Action.SPLIT) {
                        //Only available if the Player has two cards of the same rank.
                        // The Player splits their hand into two separate hands,
                        // and must place a bet on the other hand equal to their original bet.
                        // The Dealer gives a single new card to each of these new hands.
                        // Each of these hands is treated as their own separate value.

                        actionSuccessful=processSplit(player,bets,dealer);
                    }else if (action == Action.DOUBLE_UP) {
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

                if (action == Action.HIT) {
                    actionSuccessful = processHit(dealer, dealer);

                } else if (action == Action.STAND) {
                    if(faceValueCalculable.getTotalFaceValue(dealer.getHandsOfPokerCards().get(0))<DEALER_HIT_THRESHOLD){
                        Utility.promptUser(Utility.ANSI_RED+"Sorry, your face value didn't get to the "+DEALER_HIT_THRESHOLD+", please go on hitting."+Utility.ANSI_RESET);
                        actionSuccessful=false;
                        dealerStood=false;
                    }else{
                        actionSuccessful = ACTION_SUCCESSFUL;
                        dealerStood = true;
                    }

                } else if (action == Action.SPLIT) {

                    Utility.promptUser(Utility.ANSI_RED+"Sorry, as a dealer, you cannot split."+Utility.ANSI_RESET);
                    actionSuccessful = ACTION_FAILED;
                } else if (action == Action.DOUBLE_UP) {
                    /**
                     * The Player doubles their bet, and takes only a
                     single hit and immediately stands afterwards.
                     */
                    Utility.promptUser(Utility.ANSI_RED+"Sorry, as a dealer, you cannot double up"+Utility.ANSI_RESET);
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
        Utility.promptUser("Dealer's Total Face Value: "+dealerTotalFaceValue);

        for (int serialNumber=0;serialNumber< players.size();serialNumber++) {

            OrdinaryPlayer player=players.get(serialNumber);
            if(player.getSerialNumber()==dealer.getSerialNumber())continue;// skip dealer


            for(int i=0;i<player.getHandsOfPokerCards().size();i++){
                List<PokerCard> handOfPokerCards=player.getHandsOfPokerCards().get(i);
                int playerTotalFaceValue=faceValueCalculable.getTotalFaceValue(handOfPokerCards);
                boolean playerBust=(playerTotalFaceValue>BUST_THRESHOLD);
                boolean playerIsNatural= faceValueCalculable.isNatural(handOfPokerCards);

                GameResult resultForPlayer = getGameResult(dealerTotalFaceValue, dealerBust, dealerIsNatural, playerTotalFaceValue, playerBust, playerIsNatural);


                String printResult="";
                if(resultForPlayer==GameResult.WIN){
                    player.setAmountOfMoney(player.getAmountOfMoney()+bets.get(serialNumber).get(i)*2);
                    printResult=Utility.ANSI_GREEN+"WIN"+Utility.ANSI_RESET;
                }

                if(resultForPlayer==GameResult.DRAW){
                    try {
                        player.setAmountOfMoney(player.getAmountOfMoney() + bets.get(serialNumber).get(i));
                    }catch (Exception e) {

                    }
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




    private boolean processDoubleUp(OrdinaryPlayer player, Set<OrdinaryPlayer> playersThatStood, Dealer dealer, Map<Integer,Map<Integer, Integer>> bets) {
        //double bet
        DoubleUpProcessStrategy doubleUpProcessStrategy=new DoubleUpProcessStrategyImpl();
        return doubleUpProcessStrategy.processDoubleUp(player,playersThatStood,dealer,bets);
    }





    private boolean processSplit(OrdinaryPlayer player, Map<Integer, Map<Integer,Integer>> bets, Dealer dealer){
        SplitProcessStrategy splitProcessStrategy=new SplitProcessStrategyImpl();

        return splitProcessStrategy.processSplit(player,bets,dealer);
    }


}