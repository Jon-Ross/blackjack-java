public class Game {

    public Winner determineWinner(final int houseCard1, final int houseCard2, final int playerCard1, final int playerCard2) {
        final int houseTotal = houseCard1 + houseCard2;
        final int playerTotal = playerCard1 + playerCard2;

        if (houseTotal >= playerTotal){
            return Winner.HOUSE;
        } else {
            return Winner.PLAYER;
        }
    }
}
