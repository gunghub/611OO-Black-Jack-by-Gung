import java.util.List;

/**
 * Created by gung on 10/16/21.
 */
public class ChooseDealerStrategyImplRandom implements ChooseDealerStrategy{
    public Dealer chooseDealer(List<OrdinaryPlayer> players, boolean humanVersusMachine) {
        Dealer dealer;
        if(!humanVersusMachine){
            int randomSerialNumber= Utility.randomInteger(0,players.size()-1);
            dealer=(Dealer) players.get(randomSerialNumber);
        }else{
            dealer=(Dealer)players.get(1);
        }
        Utility.promptUser("Round Begin! Please note that Player "+dealer.getSerialNumber() +" is the dealer this round.");
        dealer.setPokerCardDeck(PokerCardGameUtility.getAPokerCardDeck());
        return dealer;
    }

    public Dealer chooseDealer(List<OrdinaryPlayer> players){
        return null;
    }
}
