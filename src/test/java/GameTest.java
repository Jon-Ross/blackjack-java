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
    public void GivenHouseHasAtLeastThresholdAndBetterThanPlayer_WhenCalculateWinner_ThenHouseWins() {
        // given
        final int playerCard1 = 5;
        final int playerCard2 = 10;

        final int houseCard1 = 9;
        final int houseCard2 = 10;

        // when
        final Winner winner = determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.HOUSE, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndWorseThanPlayer_WhenCalculateWinner_ThenPlayerWins() {
        // given
        final int playerCard1 = 10;
        final int playerCard2 = 10;

        final int houseCard1 = 9;
        final int houseCard2 = 10;

        // when
        final Winner winner = determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.PLAYER, winner);
    }

    @Test
    public void GivenHouseHasAtLeastThresholdAndSameAsPlayer_WhenCalculateWinner_ThenDraw() {
        // given
        final int playerCard1 = 9;
        final int playerCard2 = 10;

        final int houseCard1 = 9;
        final int houseCard2 = 10;

        // when
        final Winner winner = determineWinner(houseCard1, houseCard2, playerCard1, playerCard2);

        // then
        assertEquals(Winner.DRAW, winner);
    }

    private Winner determineWinner(final int houseCard1, final int houseCard2, final int playerCard1, final int playerCard2) {
        final int houseTotal = houseCard1 + houseCard2;
        final int playerTotal = playerCard1 + playerCard2;

        if (houseTotal > playerTotal){
            return Winner.HOUSE;
        } else if (houseTotal < playerTotal){
            return Winner.PLAYER;
        } else {
            return Winner.DRAW;
        }
    }
}

enum Winner {
    HOUSE,
    PLAYER,
    DRAW
}