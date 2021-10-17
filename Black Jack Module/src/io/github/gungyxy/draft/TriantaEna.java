package io.github.gungyxy.draft;

import java.util.*;

public class TriantaEna {
    List<TriantaEnaPlayer> playerList;
    TriantaEnaPlayer banker;
    Set<TriantaEnaPlayer> playersThatStood;

    void start(){
        initialize();
        rounds();
        finish();
    }

    public void stand(TriantaEnaPlayer player){
        playersThatStood.add(player);
    }

    public void hit(TriantaEnaPlayer player){
        player.receiveACard(banker.dealACard());
    }

    void initialize(){

        //加载一批玩家
        playerList =new ArrayList<>();
        for(int i=0;i<=5;i++){
            playerList.add(new TriantaEnaPlayer("BlackJackLikePlayer"+String.valueOf(i)));
        }

        //选一个为banker。就下标为0的吧。
        playerList.get(0).setRole(TriantaEnaPlayerRole.BANKER);
        banker=playerList.get(0);

    }

    void aRound(){
        //todo 更新banker

        playersThatStood=new HashSet<>();
        banker.newCardStack();


        //每個人一張face down
        for(TriantaEnaPlayer player:playerList) {
            if (player.getRole() == TriantaEnaPlayerRole.BANKER) continue;
            player.receiveACard(banker.dealACard());
        }

        //banker自己發
        Card cardDealt=banker.dealACard();
        cardDealt.setFaceUp(true);
        banker.receiveACard(cardDealt);


        //每個人兩張張face up
        for(TriantaEnaPlayer player:playerList) {
            //if (player.getRole() == TriantaEnaPlayerRole.BANKER) continue;

            cardDealt=banker.dealACard();
            cardDealt.setFaceUp(true);
            player.receiveACard(cardDealt);

            cardDealt=banker.dealACard();
            cardDealt.setFaceUp(true);
            player.receiveACard(cardDealt);
        }

        boolean allStood;//直到所有 player 包括 dealer 都 stand 为止
        while(true){
            for (TriantaEnaPlayer aPlayerList1 : playerList) {
                if (!playersThatStood.contains(aPlayerList1)) {

//                    if (aPlayerList1 == banker) {
//                        continue;
//                    }

                    aPlayerList1.act(this);
                } else {
                    //skip
                }
            }

            allStood=true;
            for (TriantaEnaPlayer player : playerList) {
//                if (player == banker) {
//                    continue;
//                }

                if (!playersThatStood.contains(player)) {
                    allStood = false;
                }
            }
            if(allStood) break;
        }


        //计算所有player的 face value 之和。
        Map<TriantaEnaPlayer,Integer> totalValues=new HashMap<>();
        for (TriantaEnaPlayer player: playerList
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
                if(totalValue+10<=31) totalValue=totalValue+10;
            }


            System.out.println(player.name+" total face value: "+totalValue);

            if(totalValue>31) totalValue=-1;
            totalValues.put(player,totalValue);
        }


        for (TriantaEnaPlayer player: playerList
                ) {
            if(player==banker)continue;
            if(totalValues.get(player)>totalValues.get(banker)){
                System.out.println(player.name+" won.");
            }else{
                System.out.println(player.name+" lost.");
            }
        }

    }

    void rounds(){
        aRound();
    }


    void finish(){

    }


    public void printCurrentCircumstance() {

        for (int i = 0; i < playerList.size(); i++) {
            System.out.print(playerList.get(i).name + " has ");
            List<Card> cardList = playerList.get(i).getCardList();
            for (Card card : cardList
                    ) {
                System.out.print(card.peek() + " ");
            }
            System.out.println();
        }

        System.out.print("io.github.gungyxy.draft.Dealer " + " has ");
        for (Card card : banker.getCardList()
                ) {
            System.out.print(card.peek() + " ");
        }
        System.out.println();
    }
}
