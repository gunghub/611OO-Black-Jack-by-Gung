package io.github.gungyxy.draft;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by gung on 10/15/21.
*/
class Dealer{//todo live player only
    boolean automatic=false;
    String name="dealer";

    private List<Card> cardList=new ArrayList<>();
    private Stack<Card> cardStack=new Stack<>();

    void receiveACard(Card card){
        cardList.add(card);
    }

    void newCardStack(){
        cardStack= Card.get52CardsRandom(new BlackJackFaceValueConverter());
//            for (io.github.gungyxy.draft.PokerCard card:cardStack
//                 ) {
//                System.out.println(card.getRankCode());
//            }
    }

    public Card dealACard(){
        return cardStack.pop();
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void act(BlackJack blackJack) {
        while (true) {
            System.out.println(name+"  Please input 1 for hit, 2 for stand, 3 for peek the table, 4 for read my cards");
            Scanner scanner = new Scanner(System.in);
            int inputInteger;
            while (true) {
                if (scanner.hasNextInt()) {
                    inputInteger = scanner.nextInt();
                    break;
                } else {
                    scanner.next();
                }
            }

            if (inputInteger == 1) {
                blackJack.hit(this);
                printMyCardList();
            }

            if (inputInteger == 2) {
                blackJack.stand(this);
                printMyCardList();
                break;
            }

            if (inputInteger == 3) {
                blackJack.printCurrentCircumstance();
            }

            if (inputInteger == 4) {
                printMyCardList();
            }
        }

    }

    public void printMyCardList(){

        System.out.print(name+" has ");
        for (Card card :
                cardList) {
            System.out.print(Card.RANKS[card.getRank()]+" ");
        }
        System.out.println();
    }
}


