import java.util.Set;

/**
 * Created by gung on 10/16/21.
 */
public class StandProcessStrategyImpl implements StandProcessStrategy{
    public boolean processStand(Set<OrdinaryPlayer> playersThatStood, OrdinaryPlayer player) {
        playersThatStood.add(player);
        return BlackJackLikeGame.ACTION_SUCCESSFUL;
    }
}
