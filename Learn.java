import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Learn {

    private final Deck deck;

    private static HashMap<FlashCard, Integer> weights = new HashMap<>();

    public Learn (Deck deck) {
        this.deck = deck;
        initialize_map();
    }

    private void initialize_map() {
        for (int i = 0; i < deck.size(); i++) {
            weights.put(deck.get(i), 5);
        }
    }

    private void update_weight(FlashCard card, boolean isCorrect) {
        if (isCorrect && weights.get(card) != 1) { weights.replace(card, weights.get(card) - 1); }
        else if (!isCorrect && weights.get(card) != 10) { weights.replace(card, weights.get(card) + 1); }
    }

    //Cumulative Density Function
    private FlashCard cdf() {

        int cumulative_sum = weights.values().stream().mapToInt(Integer::intValue).sum();
        Random random  = new Random();
        int rand = random.nextInt(cumulative_sum) + 1;

        int sum = 0;
        for (Map.Entry<FlashCard, Integer> entry: weights.entrySet()) {
            sum += entry.getValue();
            if (rand <= sum) { return entry.getKey(); }
        }
        //Should never return null
        return null;
    }

    public static void main(String[] args) {
        DeckDatabase db = new DeckDatabase();
        Deck deck = db.read().get(2);
        Learn temp = new Learn(deck);
        weights.replace(deck.get(0), 9);
        weights.replace(deck.get(1), 1);

        for (int i = 0; i < 1000; i++) {
            System.out.println(temp.cdf().question);
        }



        //System.out.println(weights);
    }
}
