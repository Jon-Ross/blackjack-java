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

        handleStartGame();
    }

    @Override
    public void showPlayerHand(Hand hand) {
        showHandMessage("Your", hand);
    }

    @Override
    public void showGameInstructions(String instructions) {
        println(instructions);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final String answer = reader.readLine();
            if (answer.toLowerCase().equals("s")) {
                presenter.onStick();
            } else if (answer.toLowerCase().equals("t")) {
                presenter.onTwist();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showHouseHand(Hand hand) {
        showHandMessage("House", hand);
    }

    @Override
    public void showWinner(Winner winner) {
        String winnerSection = "     ********************************";
        String winnerMessage = "     * Winner is: " + winner;
        final int spaceCount = winnerSection.length() - winnerMessage.length() - 1;

        printWinnerMessage(winnerSection, winnerMessage, spaceCount);
    }

    @Override
    public void showPlayAgainInstructions(String instructions) {
        println(instructions);

        handleStartGame();
    }

    @Override
    public void alertBust(String alert) {
        println(alert);
    }

    @Override
    public void alertHouseAction(String alert) {
        println(alert);
    }

    private void handleStartGame() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final String answer = reader.readLine();
            if (answer.toLowerCase().equals("n")) {
                presenter.onStartBlackJackGame();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printWinnerMessage(String winnerSection, String winnerMessage, int spaceCount) {
        println(winnerSection);
        print(winnerMessage);
        addSpaces(spaceCount);
        println("*");
        println(winnerSection);
    }

    private void println(String message) {
        System.out.println(message);
    }

    private void print(String winnerMessage) {
        System.out.print(winnerMessage);
    }

    private void addSpaces(int spaceCount) {
        for (int i = 0; i < spaceCount; i++) {
            print(" ");
        }
    }

    private void showHandMessage(String player, Hand hand) {
        println(player + " hand is: " + hand.getCardValues() + ", Total is: " + hand.sum());
    }
}
