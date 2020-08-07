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
    public void WhenDealCard_ThenCardDealt() {
        // given
        final int expectedCard = 5;
        dealer.addValue(expectedCard);

        // when
        final int actualCard = game.dealCard();

        // then
        assertEquals(expectedCard, actualCard);
    }

    @Test
    public void WhenDealHand_ThenHandDealt() {
        // given
        final int card1 = 10;
        final int card2 = 11;
        dealer.addValue(card1);
        dealer.addValue(card2);

        // when
        final Hand actualHand = game.dealHand();

        // then
        final Hand expectedHand = new Hand();
        expectedHand.addValue(card1);
        expectedHand.addValue(card2);
        assertEquals(expectedHand, actualHand);
    }

    @Test
    public void GivenPlayerIsDealtHand_WhenIsBust_ThenIsNotBust() {
        // given
        dealer.addValue(10);
        dealer.addValue(11);
        final Hand playerHand = game.dealHand();

        // when
        // then
        assertFalse(game.isBust(playerHand));
    }

    @Test
    public void GivenPlayerIsDealtHandGreaterThan21_WhenIsBust_ThenIsBust() {
        // given
        dealer.addValue(11);
        dealer.addValue(11);

        final Hand playerHand = game.dealHand();

        // when
        // then
        assertTrue(game.isBust(playerHand));
    }

    @Test
    public void GivenPlayerIsDealtHand_WhenTwist_ThenBust() {
        // given
        dealer.addValue(7);
        dealer.addValue(3);
        dealer.addValue(11);

        final Hand playerHand = game.dealHand();
        final int playerCard = game.dealCard();
        playerHand.addValue(playerCard);

        // when
        // then
        assertFalse(game.isBust(playerHand));
    }

    @Test
    public void GivenPlayerNotBustAndHouseUnderMinThreshold_WhenDetermineWinner_ThenReturnNull() {
        // given
        dealer.addValue(7);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        playerHand.addValue(dealer.dealCard());

        dealer.addValue(9);
        dealer.addValue(7);

        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertNull(winner);
    }

    @Test
    public void GivenPlayerIsBustAndHouseUnderMinThreshold_WhenDetermineWinner_ThenReturnNull() {
        // given
        dealer.addValue(5);
        dealer.addValue(7);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        playerHand.addValue(dealer.dealCard());

        dealer.addValue(9);
        dealer.addValue(7);

        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertNull(winner);
    }

    @Test
    public void GivenHouseHandAndBetterThanPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        dealer.addValue(5);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHandAndWorseThanPlayer_WhenDetermineWinner_ThenPlayerWins() {
        // given
        dealer.addValue(10);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.PLAYER, winner);
    }

    @Test
    public void GivenHouseHandAndSameAsPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        dealer.addValue(9);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHandAndPlayerTwists_WhenDetermineWinner_ThenPlayerWins() {
        // given
        dealer.addValue(5);
        dealer.addValue(6);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        playerHand.addValue(dealer.dealCard());

        dealer.addValue(9);
        dealer.addValue(8);

        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.PLAYER, winner);
    }

    @Test
    public void GivenPlayerBustAndHouseValid_WhenDetermineWinner_ThenHouseWins() {
        // given
        dealer.addValue(5);
        dealer.addValue(7);
        dealer.addValue(10);

        final Hand playerHand = game.dealHand();
        playerHand.addValue(dealer.dealCard());

        dealer.addValue(9);
        dealer.addValue(8);

        final Hand houseHand = game.dealHand();

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseBustAndPlayerValid_WhenDetermineWinner_ThenPlayerWins() {
        // given
        final Hand playerHand = new Hand();
        playerHand.addValue(9);
        playerHand.addValue(8);

        final Hand houseHand = new Hand();
        houseHand.addValue(5);
        houseHand.addValue(7);
        houseHand.addValue(10);

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.PLAYER, winner);
    }

    @Test
    public void GivenHouseAndPlayerBust_WhenDetermineWinner_ThenHouseWins() {
        // given
        final Hand playerHand = new Hand();
        playerHand.addValue(5);
        playerHand.addValue(7);
        playerHand.addValue(10);

        final Hand houseHand = new Hand();
        houseHand.addValue(5);
        houseHand.addValue(7);
        houseHand.addValue(10);

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHandIsUnderMinThreshold_WhenIsUnderMinThreshold_ThenReturnTrue() {
        // given
        // when
        final Hand houseHand = new Hand();
        houseHand.addValue(10);
        houseHand.addValue(6);

        // then
        assertTrue(game.isUnderMinThreshold(houseHand));
    }

    @Test
    public void GivenHouseHandIsAtMinThreshold_WhenIsUnderMinThreshold_ThenReturnFalse() {
        // given
        // when
        final Hand houseHand = new Hand();
        houseHand.addValue(10);
        houseHand.addValue(7);

        // then
        assertFalse(game.isUnderMinThreshold(houseHand));
    }
}