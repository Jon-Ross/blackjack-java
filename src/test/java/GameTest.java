import org.junit.Before;
import org.junit.Ignore;
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

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        // when
        // then
        assertFalse(game.isBust(playerHand));
    }

    @Test
    public void GivenPlayerIsDealtTwoCardsGreaterThan21_WhenIsBust_ThenIsBust() {
        // given
        dealer.addValue(11);
        dealer.addValue(11);

        final int playerCard1 = game.dealCard();
        final int playerCard2 = game.dealCard();

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);

        // when
        // then
        assertTrue(game.isBust(playerHand));
    }

    @Test
    public void GivenPlayerNotBust_WhenTwist_ThenBust() {
        // given
        dealer.addValue(7);
        dealer.addValue(3);
        dealer.addValue(11);

        final int playerCard1 = game.dealCard();
        final int playerCard2 = game.dealCard();
        final int playerCard3 = game.dealCard();

        final Hand playerHand = new Hand();
        playerHand.addValue(playerCard1);
        playerHand.addValue(playerCard2);
        playerHand.addValue(playerCard3);

        // when
        // then
        assertFalse(game.isBust(playerHand));
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndBetterThanPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        dealer.addValue(5);
        dealer.addValue(10);
        dealer.addValue(9);
        dealer.addValue(10);

        final Hand playerHand = new Hand();
        playerHand.addValue(dealer.dealCard());
        playerHand.addValue(dealer.dealCard());

        final Hand houseHand = new Hand();
        houseHand.addValue(dealer.dealCard());
        houseHand.addValue(dealer.dealCard());

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

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

        final Hand playerHand = new Hand();
        playerHand.addValue(dealer.dealCard());
        playerHand.addValue(dealer.dealCard());

        final Hand houseHand = new Hand();
        houseHand.addValue(dealer.dealCard());
        houseHand.addValue(dealer.dealCard());

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

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

        final Hand playerHand = new Hand();
        playerHand.addValue(dealer.dealCard());
        playerHand.addValue(dealer.dealCard());

        final Hand houseHand = new Hand();
        houseHand.addValue(dealer.dealCard());
        houseHand.addValue(dealer.dealCard());

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndPlayerTwists_WhenDetermineWinner_ThenPlayerWins() {
        // given
        dealer.addValue(5);
        dealer.addValue(6);
        dealer.addValue(10);

        final Hand playerHand = new Hand();
        playerHand.addValue(dealer.dealCard());
        playerHand.addValue(dealer.dealCard());
        playerHand.addValue(dealer.dealCard());

        dealer.addValue(9);
        dealer.addValue(8);

        final Hand houseHand = new Hand();
        houseHand.addValue(dealer.dealCard());
        houseHand.addValue(dealer.dealCard());

        // when
        final Winner winner = game.determineWinner(houseHand, playerHand);

        // then
        assertEquals(Winner.PLAYER, winner);
    }
}