public class GamePresenter implements GameScreenContract.Presenter {

    private final Game game;

    private GameScreenContract.View view;

    private Hand playerHand;

    public GamePresenter(Game game) {
        this.game = game;
    }

    @Override
    public void onStartScreen() {
        final String startingInstructions = "Press \"n\" to start a new blackjack game";
        view.showStartingInstructions(startingInstructions);
    }

    @Override
    public void onStartBlackJackGame() {
        playerHand = game.dealHand();
        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";
        view.showPlayerHand(playerHand);
        view.showGameInstructions(gameInstructions);
    }

    @Override
    public void onTwist() {

    }

    @Override
    public void onStick() {
        final Hand houseHand = game.dealHand();
        view.showHouseHand(houseHand);
        final Winner winner = game.determineWinner(houseHand, playerHand);
        view.showWinner(winner);
        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";
        view.showPlayAgainInstructions(playAgainInstructions);
    }

    @Override
    public void bind(GameScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {

    }
}
