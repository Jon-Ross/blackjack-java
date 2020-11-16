package blackjack_console;

import blackjack_core.BlackjackGame;
import blackjack_core.Dealer;
import blackjack_core.Game;
import blackjack_core.GamePresenter;
import blackjack_core.GameScreenContract;

public class BlackjackConsoleServiceLocator {

    BlackjackGame.Controller getController() {
        final Dealer dealer = new Dealer();
        final Game game = new Game(dealer);
        final GameScreenContract.StringProvider stringProvider = new ConsoleStringProvider();
        final GameScreenContract.Presenter presenter = new GamePresenter(game, stringProvider);
        final GameScreenContract.View view = new ConsoleGameScreenView(presenter);
        presenter.bind(view);

        return new BlackjackController(presenter);
    }
}
