package blackjack_console;

import blackjack_core.BlackjackGame;

public class BlackjackMain {

    public static void main(String[] args) {
        final BlackjackConsoleServiceLocator serviceLocator = new BlackjackConsoleServiceLocator();
        final BlackjackGame.Controller controller = serviceLocator.getController();

        controller.startGame();
    }
}

/*
 1. Create something to implement View
 2. Connect View and Presenter via bind()
 3. Start game
 */