package blackjack_console;

import blackjack_core.Dealer;
import blackjack_core.Game;
import blackjack_core.GamePresenter;
import blackjack_core.GameScreenContract;

public class BlackjackMain {

    public static void main(String[] args) {
        final GameScreenContract.View view = new ConsoleGameScreenView();
        final Dealer dealer = new Dealer();
        final Game game = new Game(dealer);
        final GameScreenContract.Presenter presenter = new GamePresenter(game);
        presenter.bind(view);

        presenter.onStartScreen();
    }
}

/*
 1. Create something to implement View
 2. Connect View and Presenter via bind()
 3. Start game
 */