import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GamePresenterTest {

    private static final String GAME_INSTRUCTIONS = "Press \"s\" to stick and \"t\" to twist";

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

        // when
        presenter.onStartBlackJackGame();

        // then
        InOrder orderVerifier = Mockito.inOrder(view);
        orderVerifier.verify(view).showPlayerHand(playerHand);
        orderVerifier.verify(view).showGameInstructions(GAME_INSTRUCTIONS);
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
        verify(view).alertHouseAction("House value is at least 17.\nHouse Sticks.");
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
        verify(view).alertHouseAction("House value is at least 17.\nHouse Sticks.");
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }

    @Test
    public void WhenOnTwist_ThenShowPlayerHandAndShowGameInstructions() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 3;
        final Hand playerHand1 = new Hand();
        playerHand1.addValue(playerCard1);
        playerHand1.addValue(playerCard2);
        when(game.dealHand()).thenReturn(playerHand1);

        presenter.onStartBlackJackGame();

        final int playerCard3 = 5;
        when(game.dealCard()).thenReturn(playerCard3);
        final Hand playerHand2 = new Hand();
        playerHand2.addValue(playerCard1);
        playerHand2.addValue(playerCard2);
        playerHand2.addValue(playerCard3);

        // when
        presenter.onTwist();

        // then
        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        List<Hand> values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(playerHand1, values.get(0));
        assertEquals(playerHand2, values.get(1));
        verify(view, times(2)).showGameInstructions(GAME_INSTRUCTIONS);
    }

    @Test
    public void GivenPlayerSticksWithLosingHandAndHouseTwists_WhenGamePlayed_ThenHouseDeclaredWinner() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 5;
        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);
        when(game.dealHand()).thenReturn(playerHand);

        presenter.onStartBlackJackGame();

        final int houseCard1 = 10;
        final int houseCard2 = 6;
        final int houseCard3 = 2;
        final Hand houseHand1 = new Hand();
        houseHand1.addValue(houseCard1);
        houseHand1.addValue(houseCard2);
        when(game.dealHand()).thenReturn(houseHand1);
        when(game.isUnderMinThreshold(houseHand1)).thenReturn(true);
        when(game.dealCard()).thenReturn(houseCard3);
        final Hand houseHand2 = new Hand();
        houseHand2.addValue(houseCard1);
        houseHand2.addValue(houseCard2);
        houseHand2.addValue(houseCard3);
        when(game.determineWinner(houseHand2, playerHand)).thenReturn(Winner.HOUSE);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStick();

        // then
        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showHouseHand(argument.capture());
        List<Hand> values = argument.getAllValues();
        assertEquals(2, values.size());

        assertEquals(houseHand1, values.get(0));
        verify(view).alertHouseAction("House value is less than 17.\nHouse Twists.");
        assertEquals(houseHand2, values.get(1));
        verify(view).alertHouseAction("House value is at least 17.\nHouse Sticks.");
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }
}
