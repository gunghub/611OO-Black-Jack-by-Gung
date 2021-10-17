package io.github.gungyxy.draft;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class TriantaEnaPlayer {
    public String name;
    private TriantaEnaPlayerRole role= TriantaEnaPlayerRole.NON_BANKER_PLAYER;
    TriantaEnaPlayer(TriantaEnaPlayerRole role){

    }

    TriantaEnaPlayer(String name){
        this.name=name;
    }

    private List<Card> cardList=new ArrayList<>();
    private Stack<Card> cardStack=new Stack<>();

    TriantaEnaPlayer(){

    }

    public void setRole(TriantaEnaPlayerRole role) {
        this.role = role;
    }

    public TriantaEnaPlayerRole getRole() {
        return role;
    }


    public List<Card> getCardList() {
        return cardList;
    }

    void newCardStack(){
        cardStack= Card.get52CardsRandom(new BlackJackFaceValueConverter());
//            for (PokerCard card:cardStack
//                 ) {
//                System.out.println(card.getRankCode());
//            }
    }

    public Card dealACard(){
        return cardStack.pop();
    }
    public void act(TriantaEna triantaEna) {
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
                triantaEna.hit(this);
                printMyCardList();
                break;
            }

            if (inputInteger == 2) {
                triantaEna.stand(this);
                printMyCardList();
                break;
            }

            if (inputInteger == 3) {
                triantaEna.printCurrentCircumstance();
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

    void receiveACard(Card card){
        cardList.add(card);
    }
}
