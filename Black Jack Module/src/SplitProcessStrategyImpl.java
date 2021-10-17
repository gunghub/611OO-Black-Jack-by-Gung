import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SplitProcessStrategyImpl implements SplitProcessStrategy {
    public boolean processSplit(OrdinaryPlayer player, Map<Integer, Map<Integer,Integer>> bets, Dealer dealer){
        //Only available if the Player has two cards of the same rank.
        // Each of these hands is treated as their own separate value.


        List<List<PokerCard>> handsOfPokerCards=player.getHandsOfPokerCards();
        if(handsOfPokerCards.size()>1){
            Utility.promptUser(Utility.ANSI_RED+"Sorry, you've already split."+Utility.ANSI_RESET);
            return BlackJackLikeGame.ACTION_FAILED;
        }

        if(handsOfPokerCards.get(0).size()>2){
            Utility.promptUser(Utility.ANSI_RED+"Sorry, Split only available at the beginning"+Utility.ANSI_RESET);
            return BlackJackLikeGame.ACTION_FAILED;
        }

        if(handsOfPokerCards.get(0).get(0).getRankCode()!=handsOfPokerCards.get(0).get(1).getRankCode()){
            Utility.promptUser(Utility.ANSI_RED+"Sorry, Split only available if the Player has two cards of the same rank."+Utility.ANSI_RESET);
            return BlackJackLikeGame.ACTION_FAILED;
        }


        // The Player splits their hand into two separate hands,
        handsOfPokerCards.add(1,new ArrayList<>());
        handsOfPokerCards.get(1).add(handsOfPokerCards.get(0).remove(1));

        // and must place a bet on the other hand equal to their original bet.
        int firstBet=bets.get(player.getSerialNumber()).get(0);
        bets.get(player.getSerialNumber()).put(1,player.bet(firstBet));

        // The Dealer gives a single new card to each of these new hands.
        player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_DOWN),0);
        player.receiveAPokerCard(dealer.dealAPokerCard(PokerCard.FACE_DOWN),1);
        return BlackJackLikeGame.ACTION_SUCCESSFUL;
    }
}
