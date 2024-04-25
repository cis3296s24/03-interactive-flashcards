import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Creates the main menu for the flashcard application
 * Has a dropdown for existing decks loaded in from the user_data and
 * a create deck button that will prompt the user to create a new deck
 */
public class DeckMenu2 extends JDialog {

    private JButton createNewDeckButton;
    private JPanel MainPanel;
    private JLabel IconImage;
    private JComboBox<String> dropdown;
    private ArrayList<Deck> decks;
    private Deck curr_deck;
    private DeckDatabase database = new DeckDatabase();
    private JFrame frame;
    private JPanel cardGrid = new JPanel();

    /**
     * Constructor decks the JFrame parent creates the UI, buttons, dropdown, and actionlisteners.
     * @param parent JFrame parent
     */
    public DeckMenu2(JFrame parent){
        super(parent);
        setTitle("Interactive Flashcards");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(MainPanel);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        decks = database.read();
        addDecks();

        cardGrid.setLayout(new GridLayout(0,4));
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDeck = (String) dropdown.getSelectedItem();
                    Deck temp = getDeck(selectedDeck);
                    displayCards(temp);
                    curr_deck = temp;
            }
        });

        createNewDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPopup();
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Adds decks to the dropdown menu
     */
    private void addDecks() {
        dropdown.addItem("Select Deck...");
        for (Deck deck : decks) {
            dropdown.addItem(deck.deck_name);
        }
    }

    /**
     * Gets deck object given deck name
     * @param name deck name
     * @return deck
     */
    private Deck getDeck(String name) {
        for (Deck deck : decks) {
            if (deck.deck_name.equals(name)) {
                return deck;
            }
        }
        return null;
    }

    /**
     * Create new deck popup that prompts the user to input name of the new deck
     */
    private void createPopup() {
        String inputText = JOptionPane.showInputDialog(this, "Enter the name of the new deck:");
        if (inputText != null && !inputText.isEmpty()) {
            if (getDeck(inputText) != null) {
                JOptionPane.showMessageDialog(this, "A deck with this name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Deck d = new Deck(inputText);
                decks.add(d);
                curr_deck = getDeck(inputText);
                displayCards(d);
                curr_deck = d;
            }
        }

    }

    /**
     * Displays card in a grid layout
     * @param deck Deck
     */
    private void displayCards(Deck deck) {
        new DeckDisplay(deck);
        dispose();
    }

    /**
     * Main
     * @param args args
     */
    public static void main(String[] args){

        DeckMenu2 menu = new DeckMenu2(null);


    }

}
