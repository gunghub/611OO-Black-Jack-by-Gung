public class Main {
    private static final int BLACK_JACK_GAME_CODE=1;
    private static final int TRIANTA_ENA_GAME_CODE=2;
    private static final String WELCOME_PROMPT="Welcome to the game. Please input 1 for Black Jack or 2 for Trianta Ena:";
    private static final String GOOD_BYE_PROMPT="Good Bye!";

    public static void main(String[] args) {
        int gameCode= Utility.getIntegerFromUser(WELCOME_PROMPT, BLACK_JACK_GAME_CODE,TRIANTA_ENA_GAME_CODE);
        switch (gameCode){
            case BLACK_JACK_GAME_CODE: BlackJackGame.getInstance().start();break;
            case TRIANTA_ENA_GAME_CODE: TriantaEnaGame.getInstance().start();break;
        }
        Utility.promptUser(GOOD_BYE_PROMPT);
    }

}
