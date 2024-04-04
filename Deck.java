import java.util.ArrayList;

public class Deck {
    private ArrayList<FlashCard> deck = new ArrayList<FlashCard>();
    public String deck_name;

    public Deck(String deck_name) {
        this.deck_name = deck_name;
    }

    public void add(FlashCard card) {
        deck.add(card);
    }

    public FlashCard get(int index) {
        return deck.get(index);
    }

    public void delete(FlashCard card) {
        deck.remove(card);
    }

    public int size() {
        return deck.size();
    }
}