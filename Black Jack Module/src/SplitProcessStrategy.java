import java.util.Map;

/**
 * Created by gung on 10/16/21.
 */
public interface SplitProcessStrategy {
    boolean processSplit(OrdinaryPlayer player, Map<Integer, Map<Integer,Integer>> bets, Dealer dealer);
}
