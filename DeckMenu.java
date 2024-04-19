import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DeckMenu {
    private JFrame frame;
    private ArrayList<Deck> decks;
    private Deck curr_deck;
    private DeckDatabase database = new DeckDatabase();
    private JPanel cardGrid = new JPanel();
    private JComboBox<String> dropdown;


    /**
     * Creates a menu where user can select deck then view, edit, and add new cards
     * User can quiz or review decks from this menu as well.
     */
    public DeckMenu() {start(null);}

    public DeckMenu(String selectedDeck) { start(selectedDeck); }

    public void start(String selectedDeck) {
        frame = new JFrame("Deck Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Create deck dropdown
        decks = database.read();
        String[] deckNamesArr = new String[decks.size()+2];
        deckNamesArr[0] = "Select Deck...";
        //Iterate through decks
        for (int i = 1; i <= decks.size(); i++) {
            deckNamesArr[i] = decks.get(i-1).deck_name;
        }
        curr_deck = getDeck(selectedDeck);
        deckNamesArr[decks.size()+1] = "+ Create New Deck";
        ArrayList<String> deckNames = new ArrayList<String>(Arrays.asList(deckNamesArr));
        if (selectedDeck == null) { dropdown = new JComboBox<>(deckNames.toArray(deckNamesArr));}
        else {
            for (int i = 1; i <= decks.size(); i++) {
                if (deckNames.get(i).equals(selectedDeck)) {
                    deckNames.add(0,deckNames.remove(i));
                    displayCards(getDeck(deckNames.get(0)));
                    deckNames.remove(1);
                }
            }
            dropdown = new JComboBox<>(deckNames.toArray(new String[deckNames.size()]));
        }
        JLabel dropdownLabel = new JLabel("Decks");


        //Create grid
        cardGrid.setLayout(new GridLayout(0,4));

        //Create panel and buttons, add to panel
        JPanel buttonPanel = new JPanel();
        JButton reviewButton = new JButton("Review");
        JButton tfButton = new JButton("Quiz");
        JButton matchingButton = new JButton("Match");
        buttonPanel.add(reviewButton, BorderLayout.EAST);
        buttonPanel.add(matchingButton, BorderLayout.CENTER);
        buttonPanel.add(tfButton, BorderLayout.WEST);

        //Add to mainPanel
        mainPanel.add(dropdownLabel);
        mainPanel.add(dropdown);

        //Add to frame
        frame.getContentPane().add(BorderLayout.NORTH,mainPanel);
        frame.getContentPane().add(BorderLayout.CENTER, cardGrid);
        frame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        frame.setSize(450,600);
        frame.setVisible(true);

        //review button action listener
        reviewButton.addActionListener(e -> {
            new Review(curr_deck);
            frame.dispose();
        });

        //quiz button action listener
        tfButton.addActionListener(e -> {
            TrueFalseQuiz tfQuiz = new TrueFalseQuiz(curr_deck);
            if (tfQuiz.sufficientFlashcards()) { frame.dispose(); }
        });

        //match button action listener
        matchingButton.addActionListener(e -> {
            new MatchingGame(curr_deck);
            frame.dispose();
        });

        //dropdown action listener
        dropdown.addActionListener(e -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selected = (String) combo.getSelectedItem();
            if (selected == "Select Deck...") {clearCardGrid();}
            else if (selected == "+ Create New Deck") {createPopup();}
            else {
                Deck temp = getDeck(selected);
                displayCards(temp);
                curr_deck = temp;
            }
        });
    }

    /**
     * returns Deck obj given deck name
     * @param name
     * @return Deck or null
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
     * Displays all card in deck in grid layout
     * Assigns card buttons actionlisteners that open flashcardbuilder
     * Either edit existing cards or create new cards
     * @param deck
     */
    private void displayCards(Deck deck){
        //Clear grid
        clearCardGrid();
        //Iterate through deck creating buttons for each card
        for (int i = 0;i < deck.size();i++) {
            FlashCard card = deck.get(i);
            JButton cardButton = new JButton(card.question);
            cardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
            cardGrid.add(cardButton);
            int index = i;
            //actionlistener for each card, allows flashcardbuilder to edit card
            cardButton.addActionListener(e -> {
                new FlashCardBuilder(deck.get(index),curr_deck);
            });

        }
        //Create add new card button
        JButton plus_button = new JButton("+ Add Card");
        plus_button.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
        cardGrid.add(plus_button);
        //Actionlistener to create and edit new card
        plus_button.addActionListener(e -> {
            FlashCard new_flashcard = new FlashCard();
            curr_deck.add(new_flashcard);
            new FlashCardBuilder(new_flashcard,curr_deck);
            frame.dispose();
        });
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Clear grid
     */
    private void clearCardGrid() {
        cardGrid.removeAll();
        frame.revalidate(); // Update layout
        frame.repaint(); // Redraw components
    }

    /**
     * Creates Popup to get deck name
     * Updates dropdown, creates new deck
     * Navigates to new deck
     */
    private void createPopup() {
        //Create popup frame
        JFrame popupFrame = new JFrame("Create Deck");
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField(20);
        panel.add(textField, BorderLayout.CENTER);

        //Create button and addActionListener
        JButton create = new JButton("Create");
        create.addActionListener(e -> {
            String inputText = textField.getText();
            //If deck already exists
            if (getDeck(inputText) != null) {textField.setForeground(Color.RED);}
            else {
                //Dispose popup, update dropdown, create deck, navigate to new deck
                popupFrame.dispose();
                System.out.println(dropdown.size() + " / " + decks.size());

                dropdown.removeItemAt(decks.size());
                dropdown.addItem(inputText);
                dropdown.addItem("+ Create New Deck");
                dropdown.repaint();
                dropdown.revalidate();
                decks.add(new Deck(inputText));
                dropdown.setSelectedIndex(decks.size()-1);
            }
        });
        //Configurations
        panel.add(create, BorderLayout.SOUTH);
        popupFrame.getContentPane().add(panel);
        popupFrame.setSize(300, 100);
        popupFrame.setLocationRelativeTo(null); // Center the frame on the screen
        popupFrame.setVisible(true);
    }


    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeckMenu();
            }
        });
    }
}
