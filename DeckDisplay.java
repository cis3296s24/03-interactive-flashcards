import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DeckDisplay {

    private JFrame frame;
    private ArrayList<Deck> decks;
    private Deck curr_deck;
    private DeckDatabase database = new DeckDatabase();
    private JPanel cardGrid = new JPanel();

    public DeckDisplay( String deck ){
        frame = new JFrame("Deck Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        System.out.println(" in display class ");
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        decks = database.read();
//        String[] deckNamesArr = new String[decks.size()+2];
//        deckNamesArr[0] = "Select Deck...";
//        //Iterate through decks
//        for (int i = 1; i <= decks.size(); i++) {
//            deckNamesArr[i] = decks.get(i-1).deck_name;
//        }

        curr_deck = getDeck(deck);
//        deckNamesArr[decks.size()+1] = "+ Create New Deck";
//        ArrayList<String> deckNames = new ArrayList<String>(Arrays.asList(deckNamesArr));
//        if (deck != null) {
//            System.out.println(" in loop ");
//            System.out.println(deck);
//            for (int i = 1; i <= decks.size(); i++) {
//                if (deckNames.get(i).equals(deck)) {
//                    deckNames.add(0, deckNames.remove(i));
//                    displayCards(getDeck(deckNames.get(0)));
//                    deckNames.remove(1);
//                }
//            }
//        }
        displayCards(curr_deck);

        // Add the cardGrid panel to the frame's content pane
        frame.getContentPane().add(cardGrid);

        // Set frame size and visibility
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }


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
                new FlashCardBuilder2(null, curr_deck, deck.get(index));
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
            new FlashCardBuilder2(null, curr_deck, new_flashcard);
            frame.dispose();
        });
        //frame.revalidate();
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



}
