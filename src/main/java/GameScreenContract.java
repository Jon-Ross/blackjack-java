public interface GameScreenContract {

    interface View {
        void showStartingInstructions(final String instructions);
        void showHandPlayer(final Hand hand);
        void showGameInstructions(final String instructions);
        void showHouseHand(final Hand hand);
        void showWinner(final Winner winner);
        void showPlayAgainInstructions(final String instructions);
        void showBust(final String instructions);
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
