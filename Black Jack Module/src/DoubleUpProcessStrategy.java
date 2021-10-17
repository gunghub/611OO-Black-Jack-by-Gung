import java.util.Map;
import java.util.Set;

/**
 * Created by gung on 10/16/21.
 */
public interface DoubleUpProcessStrategy {
    boolean processDoubleUp(OrdinaryPlayer player, Set<OrdinaryPlayer> playersThatStood, Dealer dealer, Map<Integer,Map<Integer, Integer>> bets);
}
