import java.util.List;

/**
 * Created by gung on 10/16/21.
 */
public interface ChooseDealerStrategy {
    Dealer chooseDealer(List<OrdinaryPlayer> players);
    Dealer chooseDealer( List<OrdinaryPlayer> players, boolean humanVersusMachine);
}
