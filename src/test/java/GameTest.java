import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
         game = new Game();
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndBetterThanPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        final int playerCard1 = 5;
        final int playerCard2 = 10;

        final int houseCard1 = 9;
        final int houseCard2 = 10;

        // when
        final Winner winner = game.determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndWorseThanPlayer_WhenDetermineWinner_ThenPlayerWins() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 10;

        final int houseCard1 = 9;
        final int houseCard2 = 10;

        // when
        final Winner winner = game.determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.PLAYER, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndSameAsPlayer_WhenDetermineWinner_ThenHouseWins() {
        // given
        final int playerCard1 = 9;
        final int playerCard2 = 10;

        final int houseCard1 = 9;
        final int houseCard2 = 10;

        // when
        final Winner winner = game.determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

}