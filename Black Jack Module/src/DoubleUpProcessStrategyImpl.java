import java.util.Map;
import java.util.Set;


public class DoubleUpProcessStrategyImpl implements DoubleUpProcessStrategy {
    public boolean processDoubleUp(OrdinaryPlayer player, Set<OrdinaryPlayer> playersThatStood, Dealer dealer, Map<Integer,Map<Integer, Integer>> bets) {
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
            handNumber=Utility.getIntegerFromUser("Please input which hand of poker cards you want to add the new card to",0,player.getHandsOfPokerCards().size()-1);
        }
        PokerCard pokerCard=dealer.dealAPokerCard(PokerCard.FACE_DOWN);
        Utility.promptUser("Hey, Player "+player.getSerialNumber()+", you got " +pokerCard.peekedByOwner());
        player.receiveAPokerCard(pokerCard,handNumber);


        //stand
        playersThatStood.add(player);


        return BlackJackLikeGame.ACTION_SUCCESSFUL;
    }
}
