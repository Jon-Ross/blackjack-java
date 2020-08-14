package blackjack_console;

import blackjack_core.BlackjackGame;
import blackjack_core.GameScreenContract;

public class BlackjackController implements BlackjackGame.Controller {

    private GameScreenContract.Presenter presenter;

    public BlackjackController(GameScreenContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startGame() {
        presenter.onStartScreen();
    }
}
