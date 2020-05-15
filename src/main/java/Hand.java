import java.util.ArrayList;
import java.util.List;

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
}
