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
        playerHand.addValue(game.dealCard());
        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";
        view.showPlayerHand(playerHand);
        view.showGameInstructions(gameInstructions);
    }

    @Override
    public void onStick() {
        Hand houseHand = game.dealHand();
        view.showHouseHand(houseHand);
        while (game.isUnderMinThreshold(houseHand)) {
            final String alert1 = "House value is less than 17.\nHouse Twists.";
            view.alertHouseAction(alert1);

            // created new object for test purposes
            final Hand newHouseHand = new Hand(houseHand);
            newHouseHand.addValue(game.dealCard());
            houseHand = newHouseHand;
            view.showHouseHand(houseHand);
        }
        final String alert2 = "House value is at least 17.\nHouse Sticks.";
        view.alertHouseAction(alert2);
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
