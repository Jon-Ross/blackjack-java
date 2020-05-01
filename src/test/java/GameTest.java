import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    private DealerMock dealer;

    private Game game;

    @Before
    public void setUp() throws Exception {
        dealer = new DealerMock();
        game = new Game(dealer);
    }

    @Test
    public void GivenPlayerIsDealtTwoCards_WhenIsBust_ThenIsNotBust() {
        // given
        dealer.addValue(10);
        dealer.addValue(11);

        final int playerCard1 = game.dealCard();
        final int playerCard2 = game.dealCard();

        // when
        // then
        assertFalse(game.isBust(playerCard1, playerCard2));
    }

    @Test
    public void GivenPlayerIsDealtTwoCardsGreaterThan21_WhenIsBust_ThenIsBust() {
        // given
        dealer.addValue(11);
        dealer.addValue(11);

        final int playerCard1 = game.dealCard();
        final int playerCard2 = game.dealCard();

        // when
        // then
        assertTrue(game.isBust(playerCard1, playerCard2));
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndBetterThanPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        dealer.addValue(5);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);
        final int playerCard1 = game.dealCard();
        final int playerCard2 = game.dealCard();

        final int houseCard1 = game.dealCard();
        final int houseCard2 = game.dealCard();

        // when
        final Winner winner = game.determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndWorseThanPlayer_WhenDetermineWinner_ThenPlayerWins() {
        // given
        dealer.addValue(10);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);

        final int playerCard1 = dealer.dealCard();
        final int playerCard2 = dealer.dealCard();

        final int houseCard1 = dealer.dealCard();
        final int houseCard2 = dealer.dealCard();

        // when
        final Winner winner = game.determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.PLAYER, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndSameAsPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        dealer.addValue(9);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);

        final int playerCard1 = dealer.dealCard();
        final int playerCard2 = dealer.dealCard();

        final int houseCard1 = dealer.dealCard();
        final int houseCard2 = dealer.dealCard();

        // when
        final Winner winner = game.determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

}