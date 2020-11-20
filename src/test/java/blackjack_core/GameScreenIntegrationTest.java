package blackjack_core;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameScreenIntegrationTest {

    private static final String GAME_INSTRUCTIONS = "Stick and twist";
    private static final String STARTING_INSTRUCTIONS = "Start a new blackjack game";
    private static final String PLAY_AGAIN_INSTRUCTIONS = "Play a new blackjack game";
    private static final String HOUSE_VALUE_IS_AT_LEAST_THRESHOLD = "House value is at least threshold";
    private static final String HOUSE_UNDER_MIN_THRESHOLD_ALERT = "House value is less than 17. House Twists.";
    private static final String PLAYER_BUST_ALERT = "You're bust!";
    private static final String HOUSE_BUST_ALERT = "House bust!";

    @Mock
    private GameScreenContract.View view;

    @Mock
    private GameScreenContract.StringProvider stringProvider;

    private DealerMock dealer;

    private GameScreenContract.Presenter presenter;

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        dealer = new DealerMock();
        Game game = new Game(dealer);
        presenter = new GamePresenter(game, stringProvider);
        presenter.bind(view);

        when(stringProvider.getStartingInstructions()).thenReturn(STARTING_INSTRUCTIONS);
        when(stringProvider.getGameInstructions()).thenReturn(GAME_INSTRUCTIONS);
        when(stringProvider.getPlayAgainInstructions()).thenReturn(PLAY_AGAIN_INSTRUCTIONS);
        when(stringProvider.getHouseAtLeastThresholdAlert()).thenReturn(HOUSE_VALUE_IS_AT_LEAST_THRESHOLD);
        when(stringProvider.getUnderMinThresholdAlert()).thenReturn(HOUSE_UNDER_MIN_THRESHOLD_ALERT);
        when(stringProvider.getPlayerBustAlert()).thenReturn(PLAYER_BUST_ALERT);
        when(stringProvider.getHouseBustAlert()).thenReturn(HOUSE_BUST_ALERT);
    }

    @Test
    public void GivenPlayerSticksWithAWinningHand_WhenGamePlayed_ThenPlayerDeclaredWinner() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 11;
        dealer.addValue(playerCard1);
        dealer.addValue(playerCard2);

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        final int houseCard1 = 9;
        final int houseCard2 = 10;
        dealer.addValue(houseCard1);
        dealer.addValue(houseCard2);

        final Hand houseHand = new Hand();
        houseHand.addValue(houseCard1);
        houseHand.addValue(houseCard2);

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showStartingInstructions(STARTING_INSTRUCTIONS);
        verify(view).showPlayerHand(playerHand);
        verify(view).showGameInstructions(GAME_INSTRUCTIONS);
        verify(view).showHouseHand(houseHand);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showStartingInstructions(PLAY_AGAIN_INSTRUCTIONS);
    }

    @Test
    public void GivenPlayerTwistsThenSticksWithAWinningHand_WhenGamePlayed_ThenPlayerDeclaredWinner() {
        // given

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

        final int houseCard1 = 9;
        final int houseCard2 = 10;
        dealer.addValue(houseCard1);
        dealer.addValue(houseCard2);

        final Hand houseHand = new Hand();
        houseHand.addValue(houseCard1);
        houseHand.addValue(houseCard2);

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
        verify(view).showStartingInstructions(STARTING_INSTRUCTIONS);
        // onStartBlackJackGame
        // onTwist
        argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(playerHandSecond, values.get(1));
        verify(view, times(2)).showGameInstructions(GAME_INSTRUCTIONS);
        // onStick
        verify(view).showHouseHand(houseHand);
        verify(view).showAlert(HOUSE_VALUE_IS_AT_LEAST_THRESHOLD);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showStartingInstructions(PLAY_AGAIN_INSTRUCTIONS);
    }

    @Test
    public void GivenPlayerSticksWithLosingHandAndHouseTwists_WhenGamePlayed_ThenHouseDeclaredWinner() {
        // given

        final int playerCard1 = 10;
        final int playerCard2 = 6;
        dealer.addValue(playerCard1);
        dealer.addValue(playerCard2);

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

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

        // when
        presenter.onStartScreen();
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showStartingInstructions(STARTING_INSTRUCTIONS);
        verify(view).showPlayerHand(playerHand);
        verify(view).showGameInstructions(GAME_INSTRUCTIONS);

        ArgumentCaptor<Hand> argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showHouseHand(argument.capture());
        List<Hand> values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(houseHand1, values.get(0));
        assertEquals(houseHand2, values.get(1));

        verify(view).showHouseHand(houseHand1);
        verify(view).showAlert(HOUSE_UNDER_MIN_THRESHOLD_ALERT);
        verify(view).showHouseHand(houseHand2);
        verify(view).showAlert(HOUSE_VALUE_IS_AT_LEAST_THRESHOLD);
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showStartingInstructions(PLAY_AGAIN_INSTRUCTIONS);
    }

    @Test
    public void GivenPlayerTwists_WhenGamePlayed_ThenPlayerIsBust() {
        // given
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
        verify(view).showStartingInstructions(STARTING_INSTRUCTIONS);
        // onStartBlackJackGame
        // onTwist
        argument = ArgumentCaptor.forClass(Hand.class);
        verify(view, atLeastOnce()).showPlayerHand(argument.capture());
        values = argument.getAllValues();
        assertEquals(2, values.size());
        assertEquals(playerHandSecond, values.get(1));
        verify(view).showAlert(PLAYER_BUST_ALERT);
        verify(view).showWinner(Winner.HOUSE);
        verify(view).showStartingInstructions(PLAY_AGAIN_INSTRUCTIONS);
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

        // when
        presenter.onStartBlackJackGame();
        presenter.onStick();

        // then
        verify(view).showGameInstructions(GAME_INSTRUCTIONS);
        verify(view).showPlayerHand(playerHand);

        verify(view).showHouseHand(houseHand1);
        verify(view).showAlert(HOUSE_UNDER_MIN_THRESHOLD_ALERT);
        verify(view).showHouseHand(houseHand2);
        verify(view).showAlert(HOUSE_BUST_ALERT);
        verify(view).showWinner(Winner.PLAYER);
        verify(view).showStartingInstructions(PLAY_AGAIN_INSTRUCTIONS);
    }
}
