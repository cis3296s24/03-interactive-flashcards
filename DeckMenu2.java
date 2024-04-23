import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

    // Method to populate JComboBox with deck names
    private void addDecks() {
        dropdown.addItem("Select Deck...");
        for (Deck deck : decks) {
            dropdown.addItem(deck.deck_name);
        }
    }

    // Method to get Deck object given deck name
    private Deck getDeck(String name) {
        for (Deck deck : decks) {
            if (deck.deck_name.equals(name)) {
                return deck;
            }
        }
        return null;
    }

    // Method to create a new deck
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

    // Method to display cards in a grid layout
    private void displayCards(Deck deck) {
        new DeckDisplay(deck);
        dispose();
    }


    public static void main(String[] args){

        DeckMenu2 menu = new DeckMenu2(null);


    }

}
