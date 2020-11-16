package blackjack_core;

public class GamePresenter implements GameScreenContract.Presenter {

    private final Game game;
    private final GameScreenContract.StringProvider stringProvider;

    private GameScreenContract.View view;

    private Hand playerHand;

    public GamePresenter(final Game game, final GameScreenContract.StringProvider stringProvider) {
        this.game = game;
        this.stringProvider = stringProvider;
    }

    @Override
    public void onStartScreen() {
        final String startingInstructions = stringProvider.getStartingInstructions();
//        final String startingInstructions = "Press \"n\" to start a new blackjack game";
        view.showStartingInstructions(startingInstructions);
    }

    @Override
    public void onStartBlackJackGame() {
        playerHand = game.dealHand();
        final String gameInstructions = stringProvider.getGameInstructions();
//        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";
        view.showPlayerHand(playerHand);
        view.showGameInstructions(gameInstructions);
    }

    @Override
    public void onTwist() {
        playerHand.addValue(game.dealCard());
        if (game.isBust(playerHand)) {
            view.showPlayerHand(playerHand);
            final String playerBustAlert = stringProvider.getPlayerBustAlert();
//            String playerBustAlert = "You've gone bust!";
            view.showAlert(playerBustAlert);
            view.showWinner(Winner.HOUSE);
            final String playAgainInstructions = stringProvider.getPlayAgainInstructions();
//            final String playAgainInstructions = "Press \"n\" to start a new blackjack game";
            view.showStartingInstructions(playAgainInstructions);
        } else {
            final String gameInstructions = stringProvider.getGameInstructions();
//            final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";
            view.showPlayerHand(playerHand);
            view.showGameInstructions(gameInstructions);
        }
    }

    @Override
    public void onStick() {
        Hand houseHand = game.dealHand();
        view.showHouseHand(houseHand);
        while (game.isUnderMinThreshold(houseHand)) {
            final String underMinThresholdAlert = stringProvider.getUnderMinThresholdAlert();
//            final String alert = "House value is less than 17.\nHouse Twists.";
            view.showAlert(underMinThresholdAlert);

            // created new object for test purposes
            final Hand newHouseHand = new Hand(houseHand);
            newHouseHand.addValue(game.dealCard());
            houseHand = newHouseHand;
            view.showHouseHand(houseHand);
        }
        if (game.isBust(houseHand)) {
            final String houseBustAlert = stringProvider.getHouseBustAlert();
//            final String houseBustAlert = "House has gone bust!";
            view.showAlert(houseBustAlert);
        } else {
            final String houseAtLeastThresholdAlert = stringProvider.getHouseAtLeastThresholdAlert();
//            String houseAtLeastThresholdAlert = "House value is at least 17.\nHouse Sticks.";
            view.showAlert(houseAtLeastThresholdAlert);
        }
        final Winner winner = game.determineWinner(houseHand, playerHand);
        view.showWinner(winner);
        final String playAgainInstructions = stringProvider.getPlayAgainInstructions();
//        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";
        view.showStartingInstructions(playAgainInstructions);
    }

    @Override
    public void bind(GameScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void unbind() {
        view = null;
    }
}
