package blackjack_core;

public interface GameScreenContract {

    interface View {
        void showStartingInstructions(final String instructions);
        void showPlayerHand(final Hand hand);
        void showGameInstructions(final String instructions);
        void showHouseHand(final Hand hand);
        void showWinner(final Winner winner);
        void showPlayAgainInstructions(final String instructions);
        void alertBust(final String alert);
        void alertHouseAction(final String alert);
    }

    interface Presenter {
        void onStartScreen();
        void onStartBlackJackGame();
        void onTwist();
        void onStick();

        void bind(final View view);
        void unbind();
    }
}
