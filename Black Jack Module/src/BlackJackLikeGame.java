import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Black-Jack-Like Game.
 * has common features of Black Jack Game and Trianta Ena Game.
 */
public abstract class BlackJackLikeGame extends PokerCardGame {
    protected Map<Integer,Map<Integer, Integer>> bets;
    protected List<OrdinaryPlayer> players;
    protected Dealer dealer;
    protected FaceValueCalculable faceValueCalculable=new TriantaEnaFaceValueCalculator();
    protected boolean humanVersusMachine=false;
    public static final boolean ACTION_SUCCESSFUL=true;
    public static final boolean ACTION_FAILED=false;

    protected void printTable(Set<OrdinaryPlayer> playersThatStood, Set<OrdinaryPlayer> playersThatFold, boolean allFaceUp){


        Utility.promptUser(Utility.ANSI_BLUE+"----------------------------------------------------------------");
        for (OrdinaryPlayer player:players
                ) {

            if(playersThatFold!=null&&playersThatFold.contains(player)){continue;}//skip ones who fold
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
            Utility.promptUser(stringBuilder.toString());
        }
        Utility.promptUser("----------------------------------------------------------------"+Utility.ANSI_RESET);

    }

    protected void printTable(Set<OrdinaryPlayer> playersThatStood,  boolean allFaceUp){
        printTable(playersThatStood,null,allFaceUp);
    }



    protected void printTable(Set<OrdinaryPlayer> playersThatStood, Set<OrdinaryPlayer> playersThatFold ){
        printTable(playersThatStood,playersThatFold,false);
    }


    protected void printTable(Set<OrdinaryPlayer> playersThatStood){
        printTable(playersThatStood,null,false);

    }

    protected void playSeveralRounds(){
        while(true) {
            playOneRound();
            if(2== Utility.getIntegerFromUser("Do you want to play next round? 1 for Yes, 2 for No",1,2)){
                break;
            };
        }
    }

    public void playOneRound(){
    }

    protected void summaryAndQuit(){
        Utility.promptUser("quiting...");
    }


    protected boolean processHit(OrdinaryPlayer player, Dealer dealer) {
        HitProcessStrategy hitProcessStrategy=new HitProcessStrategyImpl();
        return hitProcessStrategy.processHit(player,dealer);
    }

    protected boolean processStand(Set<OrdinaryPlayer> playersThatStood, OrdinaryPlayer player) {
        StandProcessStrategy standProcessStrategy=new StandProcessStrategyImpl();
        return standProcessStrategy.processStand(playersThatStood,player);

    }

    protected void initialize(){

    }

    public void start(){
        initialize();
        playSeveralRounds();
        summaryAndQuit();
    }


    protected GameResult getGameResult(int dealerTotalFaceValue, boolean dealerBust, boolean dealerIsNatural, int playerTotalFaceValue, boolean playerBust, boolean playerIsNatural) {
        GameResult resultForPlayer;
        if(playerBust&&dealerBust){
            resultForPlayer= GameResult.DRAW;
        }else if(playerBust){
            resultForPlayer=GameResult.LOSE;
        }else if(dealerBust){
            resultForPlayer=GameResult.WIN;
        }else{// neither bust
            if(playerTotalFaceValue>dealerTotalFaceValue){
                resultForPlayer=GameResult.WIN;
            }else if(playerTotalFaceValue<dealerTotalFaceValue){
                resultForPlayer=GameResult.LOSE;
            }else {
                if(playerIsNatural) resultForPlayer=GameResult.WIN;
                else if(dealerIsNatural) resultForPlayer=GameResult.LOSE;
                else {
                    resultForPlayer=GameResult.DRAW;
                }
            }
        }
        return resultForPlayer;
    }

}
