

public class HitProcessStrategyImpl implements HitProcessStrategy {
    public boolean processHit(OrdinaryPlayer player, Dealer dealer) {

        int handNumber;
        if(player.getHandsOfPokerCards().size()==1){
            handNumber=0;
        }else{
            handNumber=Utility.getIntegerFromUser("Please input which hand of poker cards you want to add the new card to",0,player.getHandsOfPokerCards().size()-1);
        }
        PokerCard pokerCard=dealer.dealAPokerCard(PokerCard.FACE_DOWN);
        Utility.promptUser("Hey, Player "+player.getSerialNumber()+", you got " +pokerCard.peekedByOwner());
        player.receiveAPokerCard(pokerCard,handNumber);
        return BlackJackLikeGame.ACTION_SUCCESSFUL;
    }

}
