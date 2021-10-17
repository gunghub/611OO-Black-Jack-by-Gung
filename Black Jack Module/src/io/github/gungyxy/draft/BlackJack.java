package io.github.gungyxy.draft;//todo access modifier
//todo bet


import java.util.*;

public class BlackJack {
    List<BlackJackPlayer> blackJackPlayerList;
    Set<BlackJackPlayer> playersThatStood;
    Dealer dealer;

    void start(){
        initialize();
        rounds();
        finish();
    }

    void initialize(){
        dealer=new Dealer();
        blackJackPlayerList =new ArrayList<>();
        blackJackPlayerList.addAll(Arrays.asList(new BlackJackPlayer("io.github.gungyxy.draft.BlackJackLikePlayer One"), new BlackJackPlayer("io.github.gungyxy.draft.BlackJackLikePlayer Two")));//todo
    }

    void rounds(){
        aRound();//todo
    }

    void finish(){

    }

    public void hit(BlackJackPlayer blackJackPlayer){
        blackJackPlayer.receiveACard(dealer.dealACard());
    }

    public void hit(Dealer dealer){
        dealer.receiveACard(dealer.dealACard());
    }

    public void stand(BlackJackPlayer blackJackPlayer){
        playersThatStood.add(blackJackPlayer);
    }

    public void stand(Dealer dealer){

    }

    public void aRound(){


        playersThatStood=new HashSet<>();

        dealer.newCardStack();//准备一副牌

        //请dealer发给每个player两张牌
        for (BlackJackPlayer blackJackPlayer : blackJackPlayerList
             ) {

            Card card=dealer.dealACard();
            card.setFaceUp(true);
            blackJackPlayer.receiveACard(card);

            card=dealer.dealACard();
            card.setFaceUp(true);
            blackJackPlayer.receiveACard(card);
        }

        //请dealer发给自己两张牌
        Card card=dealer.dealACard();
        card.setFaceUp(true);
        dealer.receiveACard(card);

        card=dealer.dealACard();
        dealer.receiveACard(card);

        boolean allStood;//直到所有 player 都 stand 为止
        while(true){
            for (int i = 0; i< blackJackPlayerList.size(); i++) {
                if(playersThatStood.contains(blackJackPlayerList.get(i))){
                    //skip
                }else{
                    blackJackPlayerList.get(i).act(this);
                }

            }

            allStood=true;
            for (int i = 0; i< blackJackPlayerList.size(); i++) {
                if(!playersThatStood.contains(blackJackPlayerList.get(i))){
                    allStood=false;
                }
            }

            if(allStood) break;
        }


        dealer.act(this);


        //目前为止所有人都stood 了。可以开牌了。


        //计算所有player的 face value 之和。
        Map<BlackJackPlayer,Integer> totalValues=new HashMap<>();
        for (BlackJackPlayer player: blackJackPlayerList
             ) {
            List<Card> cardList=player.getCardList();
            Integer totalValue=0;
            int numberOfAce=0;
            for(int i=0;i<cardList.size();i++){
                if(cardList.get(i).getRank()==1) {
                    totalValue=totalValue+1;
                    numberOfAce++;
                } else {
                    totalValue=totalValue+cardList.get(i).getFaceValue();
                }
            }

            for(int i=1;i<=numberOfAce;i++){
                if(totalValue+10<=21) totalValue=totalValue+10;
            }

            totalValues.put(player,totalValue);
            System.out.println(player.name+" total face value: "+totalValue);
        }

        //计算 dealer 的 face value 之和
        List<Card> cardList=dealer.getCardList();
        Integer dealerTotalValue=0;
        int numberOfAce=0;
        for(int i=0;i<cardList.size();i++){
            if(cardList.get(i).getRank()==1) {
                dealerTotalValue=dealerTotalValue+1;
                numberOfAce++;
            } else {
                dealerTotalValue=dealerTotalValue+cardList.get(i).getFaceValue();
            }
        }
        for(int i=1;i<=numberOfAce;i++){
            if(dealerTotalValue+10<=21) dealerTotalValue=dealerTotalValue+10;
        }
        System.out.println(dealer.name+" total face value: "+dealerTotalValue);

        //判断谁赢？
        //先找player 中最大的。
        int max=-1;
        BlackJackPlayer maxPlayer=null;
        for (BlackJackPlayer player: blackJackPlayerList
             ) {
            if(totalValues.get(player)>max&&totalValues.get(player)<=21) {
                max=totalValues.get(player);
                maxPlayer=player;
            }
        }

        if(dealerTotalValue>21){
            dealerTotalValue=-1;
        }
        if(max>dealerTotalValue){
            System.out.println("winner: "+maxPlayer.name);
        }else if(dealerTotalValue>max){
            System.out.println("winner: "+dealer.name);
        }else{
            System.out.println("winner: none");
        }
    }
    public void printCurrentCircumstance(){

            for(int i=0;i<blackJackPlayerList.size();i++){
                System.out.print(blackJackPlayerList.get(i).name+" has ");
                List<Card> cardList=blackJackPlayerList.get(i).getCardList();
                for (Card card:cardList
                     ) {
                    System.out.print(card.peek()+" ");
                }
                System.out.println();
            }

        System.out.print("Dealer "+" has ");
        for (Card card:dealer.getCardList()
                ) {
            System.out.print(card.peek()+" ");
        }
        System.out.println();

    }
}
