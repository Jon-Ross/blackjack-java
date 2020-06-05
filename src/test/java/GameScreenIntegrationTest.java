import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameScreenIntegrationTest {

    @Mock
    private GameScreenContract.View view;

    private DealerMock dealer;

    private Game game;

    private GameScreenContract.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        dealer = new DealerMock();
        game = new Game(dealer);
        presenter = new GamePresenter();
    }

    @Test
    public void GivenPlayerSticksWithAWinningHand_WhenGamePlayed_ThenPlayerDeclaredWinner() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        dealer.addValue(10);
        dealer.addValue(11);
        final int playerCard1 = game.dealCard();
        final int playerCard2 = game.dealCard();
        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        dealer.addValue(9);
        dealer.addValue(10);
        final Hand houseHand = new Hand();
        houseHand.addValue(dealer.dealCard());
        houseHand.addValue(dealer.dealCard());

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showStartingInstructions(startingInstructions);
        verify(view).showHandPlayer(playerHand);
        verify(view).showGameInstructions(gameInstructions);
        verify(view).showHouseHand(houseHand);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showPlayAgainInstructions(playAgainInstructions);

    }
}
