public class GamePresenter implements GameScreenContract.Presenter {

    private final Game game;

    private GameScreenContract.View view;

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
        final Hand playerHand = game.dealHand();
        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";
        view.showHandPlayer(playerHand);
        view.showGameInstructions(gameInstructions);
    }

    @Override
    public void onTwist() {

    }

    @Override
    public void onStick() {

    }

    @Override
    public void bind(GameScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {

    }
}
