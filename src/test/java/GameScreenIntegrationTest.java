import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
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

        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        List<Hand> values = argument.getAllValues();
        assertEquals(1, values.size());
        assertEquals(playerHandFirst, values.get(0));

        presenter.onTwist();
        presenter.onStick();

        // then
        // onStartScreen
        verify(view).showStartingInstructions(startingInstructions);
        // onStartBlackJackGame
        // onTwist
        argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(playerHandSecond, values.get(1));
        verify(view, times(2)).showGameInstructions(gameInstructions);
        // onStick
        verify(view).showHouseHand(houseHand);
        verify(view).alertHouseAction("House value is at least 17.\nHouse Sticks.");
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }

    @Test
    public void GivenPlayerSticksWithLosingHandAndHouseTwists_WhenGamePlayed_ThenHouseDeclaredWinner() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        final int playerCard1 = 10;
        final int playerCard2 = 6;
        dealer.addValue(playerCard1);
        dealer.addValue(playerCard2);

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        final int houseCard1 = 6;
        final int houseCard2 = 10;
        final int houseCard3 = 2;
        dealer.addValue(houseCard1);
        dealer.addValue(houseCard2);
        dealer.addValue(houseCard3);

        final Hand houseHand1 = new Hand();
        houseHand1.addValue(houseCard1);
        houseHand1.addValue(houseCard2);

        final Hand houseHand2 = new Hand();
        houseHand2.addValue(houseCard1);
        houseHand2.addValue(houseCard2);
        houseHand2.addValue(houseCard3);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showStartingInstructions(startingInstructions);
        verify(view).showPlayerHand(playerHand);
        verify(view).showGameInstructions(gameInstructions);

        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showHouseHand(argument.capture());
        List<Hand> values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(houseHand1, values.get(0));
        assertEquals(houseHand2, values.get(1));

        verify(view).showHouseHand(houseHand1);
        verify(view).alertHouseAction("House value is less than 17.\nHouse Twists.");
        verify(view).showHouseHand(houseHand2);
        verify(view).alertHouseAction("House value is at least 17.\nHouse Sticks.");
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }

    @Test
    public void GivenPlayerTwists_WhenGamePlayed_ThenPlayerIsBust() {
        // given
        final String startingInstructions = "Press \"n\" to start a new blackjack game";

        final int playerCard1 = 5;
        final int playerCard2 = 9;
        final int playerCard3 = 8;
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

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();

        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        List<Hand> values = argument.getAllValues();
        assertEquals(1, values.size());
        assertEquals(playerHandFirst, values.get(0));

        presenter.onTwist();

        // then
        // onStartScreen
        verify(view).showStartingInstructions(startingInstructions);
        // onStartBlackJackGame
        // onTwist
        argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(playerHandSecond, values.get(1));
        verify(view).alertBust("You've gone bust!");
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }

    @Test
    public void GivenHouseTwists_WhenGamePlayed_ThenHouseIsBust() {
        // given
        final int playerCard1 = 8;
        final int playerCard2 = 10;
        dealer.addValue(playerCard1);
        dealer.addValue(playerCard2);

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        final String gameInstructions = "Press \"s\" to stick and \"t\" to twist";

        final int houseCard1 = 6;
        final int houseCard2 = 10;
        final int houseCard3 = 6;
        dealer.addValue(houseCard1);
        dealer.addValue(houseCard2);
        dealer.addValue(houseCard3);

        final Hand houseHand1 = new Hand();
        houseHand1.addValue(houseCard1);
        houseHand1.addValue(houseCard2);

        final Hand houseHand2 = new Hand();
        houseHand2.addValue(houseCard1);
        houseHand2.addValue(houseCard2);
        houseHand2.addValue(houseCard3);

        final String playAgainInstructions = "Press \"n\" to start a new blackjack game";

        // when
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showGameInstructions(gameInstructions);
        verify(view).showPlayerHand(playerHand);

//        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
//        verify(view, atLeastOnce()).showHouseHand(argument.capture());
//        List<Hand> values = argument.getAllValues();
//        assertEquals(2, values.size());
//        assertEquals(houseHand1, values.get(0));
//        assertEquals(houseHand2, values.get(1));

        verify(view).showHouseHand(houseHand1);
        verify(view).alertHouseAction("House value is less than 17.\nHouse Twists.");
        verify(view).showHouseHand(houseHand2);
        verify(view).alertBust("House has gone bust!");
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showPlayAgainInstructions(playAgainInstructions);
    }


    // TODO:
    // 1. implement house goes bust
    // 2. think about refactoring - like single view alert(String message) method?
}
