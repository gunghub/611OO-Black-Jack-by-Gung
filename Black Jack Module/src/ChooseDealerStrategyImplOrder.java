import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by gung on 10/16/21.
 */
public class ChooseDealerStrategyImplOrder implements ChooseDealerStrategy{
    public Dealer chooseDealer(List<OrdinaryPlayer> players) {
        Dealer dealer;
        int randomSerialNumber;
        Stack<Integer> numbers=new Stack<>();
        while(true){
            if(numbers.size()==0){
                for (int i=0;i<players.size();i++){
                    numbers.push(i);
                }
                Collections.shuffle(numbers);
            }

            randomSerialNumber= numbers.pop();
            int inputInteger=Utility.getIntegerFromUser("Would Player "+randomSerialNumber+" like to be the dealer this round? 1 for yes, 2 for no",1,2);
            if(inputInteger==1){
                break;
            }
        }

        dealer=(Dealer) players.get(randomSerialNumber);

        Utility.promptUser("Round Begin! Please note that Player "+dealer.getSerialNumber() +" is the dealer this round.");
        return dealer;
    }

    public Dealer chooseDealer(List<OrdinaryPlayer> players, boolean humanVersusMachine){

        return null;
    }
}
