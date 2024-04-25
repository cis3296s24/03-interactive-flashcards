import java.util.ArrayList;

/**
 * Deck class is used to create a deck of flashcards
 */
public class Deck {
    private ArrayList<FlashCard> deck = new ArrayList<FlashCard>();
    public String deck_name;

    /**
     * Constructor takes name for deck
     * @param deck_name deck name
     */
    public Deck(String deck_name) {
        this.deck_name = deck_name;
    }

    /**
     * add function adds card to the deck
     * @param card Flashcard
     */
    public void add(FlashCard card) {
        deck.add(card);
    }

    /**
     * get function gets card at specified indeck and returns card
     * @param index index
     * @return Flashcard
     */
    public FlashCard get(int index) {
        return deck.get(index);
    }

    /**
     * delete function deletes the flashcard provided
     * @param card Flashcard
     */
    public void delete(FlashCard card) {
        deck.remove(card);
    }

    /**
     * size function returns the size of the deck
     * @return size of deck
     */
    public int size() {
        return deck.size();
    }

    /**
     * get deck function returns the entire deck arraylist
     * @return ArrayList>(deck)
     */
    public ArrayList<FlashCard> getDeck() {
        return new ArrayList<>(deck);
    }

}