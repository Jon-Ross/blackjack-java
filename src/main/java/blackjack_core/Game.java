package blackjack_core;

public class Game {

    private static final int BUST_THRESHOLD = 21;
    private static final int MIN_THRESHOLD = 17;

    private final Dealer dealer;

    public Game(Dealer dealer) {
        this.dealer = dealer;
    }

    public Winner determineWinner(final Hand houseHand, final Hand playerHand) {
        if (houseHand.sum() < MIN_THRESHOLD) {
            return null;
        }

        if (isBust(playerHand)) {
            return Winner.HOUSE;
        } else if (isBust(houseHand)) {
            return Winner.PLAYER;
        }

        if (houseHand.sum() >= playerHand.sum()){
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

    public Hand dealHand() {
        final int card1 = dealer.dealCard();
        final int card2 = dealer.dealCard();
        final Hand hand = new Hand();
        hand.addValue(card1);
        hand.addValue(card2);
        return hand;
    }

    public boolean isUnderMinThreshold(final Hand houseHand) {
        return houseHand.sum() < MIN_THRESHOLD;
    }
}
