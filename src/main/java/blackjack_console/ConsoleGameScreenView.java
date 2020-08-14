package blackjack_console;

import blackjack_core.GameScreenContract;
import blackjack_core.Hand;
import blackjack_core.Winner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleGameScreenView implements GameScreenContract.View {

    private final GameScreenContract.Presenter presenter;

    public ConsoleGameScreenView(GameScreenContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showStartingInstructions(String instructions) {
        println(instructions);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final String answer = reader.readLine();
            if (answer.toLowerCase().equals("n")) {
                presenter.onStartBlackJackGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPlayerHand(Hand hand) {
        println("Your hand is: " + hand);
    }

    @Override
    public void showGameInstructions(String instructions) {
        println(instructions);

    }

    @Override
    public void showHouseHand(Hand hand) {
        println("House hand is: " + hand);
    }

    @Override
    public void showWinner(Winner winner) {
        println("Winner is: " + winner);
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
