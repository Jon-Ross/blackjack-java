import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hand {

    private List<Integer> cardValues = new ArrayList<>();

    public void addValue(int card) {
        cardValues.add(card);
    }

    public int sum() {
        if (cardValues.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (int value : cardValues) {
            total += value;
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return Objects.equals(cardValues, hand.cardValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardValues);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cardValues=" + cardValues +
                '}';
    }
}
