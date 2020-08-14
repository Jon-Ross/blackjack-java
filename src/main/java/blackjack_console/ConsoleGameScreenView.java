package blackjack_console;

import blackjack_core.GameScreenContract;
import blackjack_core.Hand;
import blackjack_core.Winner;

public class ConsoleGameScreenView implements GameScreenContract.View {

    @Override
    public void showStartingInstructions(String instructions) {
        println(instructions);
    }

    @Override
    public void showPlayerHand(Hand hand) {

    }

    @Override
    public void showGameInstructions(String instructions) {
        println(instructions);
    }

    @Override
    public void showHouseHand(Hand hand) {

    }

    @Override
    public void showWinner(Winner winner) {

    }

    @Override
    public void showPlayAgainInstructions(String instructions) {
        println(instructions);
    }

    @Override
    public void alertBust(String alert) {
        println(alert);
    }

    @Override
    public void alertHouseAction(String alert) {
        println(alert);
    }

    private void println(String message) {
        System.out.println(message);
    }
}
