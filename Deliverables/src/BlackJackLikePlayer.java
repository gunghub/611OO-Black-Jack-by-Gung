/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static Utility.*;


public abstract class BlackJackLikePlayer implements Dealer, OrdinaryPlayer{
    protected int serialNumber;
    protected int amountOfMoney;
    protected Stack<PokerCard> pokerCardDeck;
    protected List<List<PokerCard>> handsOfPokerCards=new ArrayList<>(Arrays.asList(new ArrayList<PokerCard>()));
    protected boolean automatic=false;
    protected static final int DEFAULT_AMOUNT_OF_MONEY=10000000;

    private BlackJackLikePlayer(){
    }

    public BlackJackLikePlayer(int serialNumber, boolean automatic){
        this.serialNumber=serialNumber;
        this.automatic=automatic;
        if(automatic){
            amountOfMoney=DEFAULT_AMOUNT_OF_MONEY;
        }else{
            setAmountOfMoneyByUser();
        }
    }

    public BlackJackLikePlayer(int serialNumber){
        this(serialNumber,false);
    }

    @Override
    public PokerCard dealAPokerCard() {
        if(pokerCardDeck==null){
            pokerCardDeck= PokerCardGameUtility.getAPokerCardDeck();
        }
        return pokerCardDeck.pop();
    }

    @Override
    public PokerCard dealAPokerCard(boolean faceOrientation){
        if(pokerCardDeck==null){
            pokerCardDeck= PokerCardGameUtility.getAPokerCardDeck();
        }
        PokerCard pokerCard=pokerCardDeck.pop();
        pokerCard.setFaceOrientation(faceOrientation);
        return pokerCard;
    }

    @Override
    public void receiveAPokerCard(PokerCard pokerCard){
        receiveAPokerCard(pokerCard,0);
    }

    @Override
    public void receiveAPokerCard(PokerCard pokerCard, int handNumber){
        while(handNumber>=handsOfPokerCards.size()){
            handsOfPokerCards.add(new ArrayList<>());
        }
        handsOfPokerCards.get(handNumber).add(pokerCard);
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    @Override
    public int bet(int lowerBound, int upperBound){
        int specificMoney= getIntegerFromUser("Please Player "+serialNumber+" input the bet between ",lowerBound,upperBound);
        return bet(specificMoney);
    }

    @Override
    public int bet(int specificMoney){
        promptUser("Player " +serialNumber+" bet "+specificMoney);
        amountOfMoney-=specificMoney;
        return specificMoney;
    }

    @Override
    public void setAmountOfMoneyByUser(){
        amountOfMoney= getIntegerFromUser("Hello, Player "+serialNumber+", please input your amount of your money, an integer between:",100000,10000000);
    }


    public Stack<PokerCard> getPokerCardDeck() {
        return pokerCardDeck;
    }

    public void setPokerCardDeck(Stack<PokerCard> pokerCardDeck) {
        this.pokerCardDeck = pokerCardDeck;
    }

    public List<List<PokerCard>> getHandsOfPokerCards() {
        return handsOfPokerCards;
    }

    @Override
    public void printHandsOfPokerCards(){

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(ANSI_PURPLE);
        stringBuilder.append("Player "+this.getSerialNumber()+" in actuality has ");
        for (List<PokerCard> handOfPokerCards:handsOfPokerCards
                ) {
            stringBuilder.append("[");
            for (PokerCard pokerCard :
                    handOfPokerCards) {
                stringBuilder.append(pokerCard.peekedByOwner()+" ");
            }
            stringBuilder.append("]");
        }
        stringBuilder.append(ANSI_RESET);
        promptUser(stringBuilder.toString());
    }

    public Action act(){ return null;}


    public boolean isAutomatic() {
        return automatic;
    }

    @Override
    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }



}
*/
