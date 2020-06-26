import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GamePresenterTest {

    @Mock
    private GameScreenContract.View view;

    @Mock
    private Game game;

    private GameScreenContract.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        presenter = new GamePresenter(game);
        presenter.bind(view);
    }

    @Test
    public void GivenNewGame_WhenOnStartScreen_ThenShowStartingInstructions() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();

        // then
        verify(view).showStartingInstructions(startingInstructions);
    }

    @Test
    public void WhenOnStartBlackJackGame_ThenShowPlayerHandAndShowGameInstructions() {
        // given
        final int playerCard1 = 8;
        final int playerCard2 = 9;
        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);
        when(game.dealHand()).thenReturn(playerHand);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        // when
        presenter.onStartBlackJackGame();

        // then
        InOrder orderVerifier = Mockito.inOrder(view);
        orderVerifier.verify(view).showPlayerHand(playerHand);
        orderVerifier.verify(view).showGameInstructions(gameInstructions);
    }

    @Test
    public void GivenPlayerHandBeatsHouseHand_WhenOnStick_ThenShowHouseHandAndShowPlayerWinsAndShowPlayAgainInstructions() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 9;
        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);
        when(game.dealHand()).thenReturn(playerHand);

        presenter.onStartBlackJackGame();

        final int houseCard1 = 8;
        final int houseCard2 = 9;
        final Hand houseHand = new Hand();
        houseHand.addValue(houseCard1);
        houseHand.addValue(houseCard2);
        when(game.dealHand()).thenReturn(houseHand);
        when(game.determineWinner(houseHand, playerHand)).thenReturn(Winner.PLAYER);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStick();

        // then
        verify(view).showHouseHand(houseHand);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }

    @Test
    public void GivenHouseHandBeatsPlayerHand_WhenOnStick_ThenShowHouseHandAndShowHouseWinsAndShowPlayAgainInstructions() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 9;
        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);
        when(game.dealHand()).thenReturn(playerHand);

        presenter.onStartBlackJackGame();

        final int houseCard1 = 10;
        final int houseCard2 = 10;
        final Hand houseHand = new Hand();
        houseHand.addValue(houseCard1);
        houseHand.addValue(houseCard2);
        when(game.dealHand()).thenReturn(houseHand);
        when(game.determineWinner(houseHand, playerHand)).thenReturn(Winner.HOUSE);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStick();

        // then
        verify(view).showHouseHand(houseHand);
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }
}
