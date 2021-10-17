//import java.util.Collections;
//import java.util.Stack;
//
///**
// * Created by gung on 10/15/21.
// */
//public class PokerCardGameUtility {
//
//    /**
//     * get a standard deck of 52 poker cards in random order;
//     * @return a standard deck of 52 poker cards in random order;
//     */
//    public static Stack<PokerCard> getAPokerCardDeck(){
//        Stack<PokerCard> pokerCardDeck=new Stack<>();
//        for(int rankCode = 1; rankCode<= PokerCard.NUMBER_OF_RANKS; rankCode++){
//            for(PokerCardSuit pokerCardSuit:PokerCardSuit.values()){
//                pokerCardDeck.push(new PokerCard(rankCode, PokerCard.FACE_DOWN,pokerCardSuit));
//            }
//        }
//        Collections.shuffle(pokerCardDeck);
//
////
////        for(int i=0;i<5;i++){
////            pokerCardDeck.push(new PokerCard(4,FACE_DOWN,PokerCardSuit.CLUBS));
////        }
//
//        return pokerCardDeck;
//    }
//}
