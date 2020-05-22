public class Game {

    private static final int BUST_THRESHOLD = 21;

    private final Dealer dealer;

    public Game(Dealer dealer) {
        this.dealer = dealer;
    }

    public Winner determineWinner(final Hand houseHand, final Hand playerHand) {
        if (houseHand.sum() >= playerHand.sum()){
            return Winner.HOUSE;
        } else {
            return Winner.PLAYER;
        }
    }

    public Winner determineWinner(final int houseCard1, final int houseCard2, final int playerCard1, final int playerCard2) {
        final int houseTotal = houseCard1 + houseCard2;
        final int playerTotal = playerCard1 + playerCard2;

        if (houseTotal >= playerTotal){
            return Winner.HOUSE;
        } else {
            return Winner.PLAYER;
        }
    }

    public boolean isBust(final Hand hand) {
        return hand.sum() > BUST_THRESHOLD;
    }

    public int dealCard() {
        return dealer.dealCard();
    }
}
