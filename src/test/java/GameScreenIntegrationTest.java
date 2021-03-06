import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameScreenIntegrationTest {

    @Mock
    private GameScreenContract.View view;

    private DealerMock dealer;

    private GameScreenContract.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        dealer = new DealerMock();
        Game game = new Game(dealer);
        presenter = new GamePresenter(game);
        presenter.bind(view);
    }

    @Test
    public void GivenPlayerSticksWithAWinningHand_WhenGamePlayed_ThenPlayerDeclaredWinner() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        final int playerCard1 = 10;
        final int playerCard2 = 11;
        dealer.addValue(playerCard1);
        dealer.addValue(playerCard2);

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        final int houseCard1 = 9;
        final int houseCard2 = 10;
        dealer.addValue(houseCard1);
        dealer.addValue(houseCard2);

        final Hand houseHand = new Hand();
        houseHand.addValue(houseCard1);
        houseHand.addValue(houseCard2);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showStartingInstructions(startingInstructions);
        verify(view).showPlayerHand(playerHand);
        verify(view).showGameInstructions(gameInstructions);
        verify(view).showHouseHand(houseHand);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showPlayAgainInstructions(playAgainInstructions);

    }

    @Test
    public void GivenPlayerTwistsThenSticksWithAWinningHand_WhenGamePlayed_ThenPlayerDeclaredWinner() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        final int playerCard1 = 5;
        final int playerCard2 = 9;
        final int playerCard3 = 7;
        dealer.addValue(playerCard1);
        dealer.addValue(playerCard2);
        dealer.addValue(playerCard3);

        final Hand playerHandFirst = new Hand();
        playerHandFirst.addValue(playerCard1);
        playerHandFirst.addValue(playerCard2);

        final Hand playerHandSecond = new Hand();
        playerHandSecond.addValue(playerCard1);
        playerHandSecond.addValue(playerCard2);
        playerHandSecond.addValue(playerCard3);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        final int houseCard1 = 9;
        final int houseCard2 = 10;
        dealer.addValue(houseCard1);
        dealer.addValue(houseCard2);

        final Hand houseHand = new Hand();
        houseHand.addValue(houseCard1);
        houseHand.addValue(houseCard2);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();
        presenter.onTwist();
        presenter.onStick();

        // then
        verify(view).showStartingInstructions(startingInstructions);
        verify(view).showPlayerHand(playerHandFirst);
        verify(view).showGameInstructions(gameInstructions);
        verify(view).showPlayerHand(playerHandSecond);
        verify(view).showGameInstructions(gameInstructions);
        verify(view).showHouseHand(houseHand);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showPlayAgainInstructions(playAgainInstructions);

    }
}
