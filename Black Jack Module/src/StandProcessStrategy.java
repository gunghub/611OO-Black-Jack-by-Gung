import java.util.Set;

/**
 * Created by gung on 10/16/21.
 */
public interface StandProcessStrategy {
    boolean processStand(Set<OrdinaryPlayer> playersThatStood, OrdinaryPlayer player);
}
