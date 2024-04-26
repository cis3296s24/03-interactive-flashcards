import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Matching game takes 4 flashcard and places them in a grad to be compared.
 * The user must choose the correct question and answer correctly and they will be blanked out.
 * When the user correctly gets all the matches correct, a new selection is shown.
 */
public class MatchingGame {
    private JFrame frame;
    private JButton[] answerButtons;
    private Deck deck;
    private List<Map.Entry<Boolean, FlashCard>> cards;
    private List<FlashCard> matchedCards;
    private Map.Entry<Boolean, FlashCard> previousCard;
    private JButton previousBtn;
    private int matchedPairs;
    private final int totalPairs;

    /**
     * Constructor takes the current deck and initializes two new arraylists, previous card, pairs, and total pairs.
     * It calls the prepareCards and the initializeUI function.
     * @param deck Deck
     */
    public MatchingGame(Deck deck) {
        this.deck = deck;
        this.cards = new ArrayList<>();
        this.matchedCards = new ArrayList<>();
        this.previousCard = null;
        this.matchedPairs = 0;
        this.totalPairs = Math.min(deck.size(), 4);
        prepareCards();
        initializeUI();
    }

    /**
     * InitializeUI initializes the UI and creates the buttons, the action listeners, and the formatting.
     */
    private void initializeUI() {
        frame = new JFrame("Matching Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setTitle("Matching");
        //frame.setPreferredSize(new Dimension(500, 500));

        // Set background color
        frame.getContentPane().setBackground(new Color(225, 252, 255));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(225, 252, 255)); // Set background color
        backButton.setForeground(new Color(75, 90, 152)); // Set text color
        backButton.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 24)); // Set font


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new DeckDisplay(deck);
            }
        });

        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(225, 252, 255));
        backPanel.add(backButton);

        // Setting up answer buttons
        answerButtons = new JButton[totalPairs * 2];
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));
        buttonPanel.setBackground(new Color(225, 252, 255));

        for (int i = 0; i < totalPairs * 2; i++) {
            String questionText = cards.get(i).getKey() ? cards.get(i).getValue().getQuestion() : cards.get(i).getValue().getAnswer();
            JButton button = new JButton(questionText);
            button.setBackground(new Color(225, 252, 255)); // Set background color
            button.setForeground(new Color(75, 90, 152)); // Set text color
            button.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectCard(finalI);
                }
            });
            answerButtons[i] = button;
            buttonPanel.add(button);
        }

        frame.add(backPanel, BorderLayout.NORTH);

        // Add components to frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Frame settings
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * prepareCards creates the deck of cards used for the current matching game
     */
    private void prepareCards() {
        // Copy the deck and shuffle the cards
        List<FlashCard> tempCards = deck.getDeck();
        Collections.shuffle(tempCards);

        // Add each card twice (matching pairs)
        for (int i = 0; i < totalPairs; i++) {
            cards.add(new AbstractMap.SimpleEntry<>(true, tempCards.get(i)));
            cards.add(new AbstractMap.SimpleEntry<>(false, tempCards.get(i)));
        }

        // Shuffle the cards again
        Collections.shuffle(cards);
    }

    /**
     * selectCard provides the functionality for clicking and matching the cards and recognizing the matching question and answer.
     * It then updates the buttons accordingly.
     * @param index index
     */
    private void selectCard(int index) {
        JButton selectedButton = answerButtons[index];
        Map.Entry<Boolean, FlashCard> selectedCard = cards.get(index);

        if (matchedCards.contains(selectedCard.getValue())) {
            return;
        }

        if (previousCard == null) {
            previousCard = selectedCard;
            previousBtn = selectedButton;

        }

        if ((selectedCard.getValue() == previousCard.getValue() && selectedCard.getKey() == previousCard.getKey())) {
            return; // Ignore if the same card is clicked or if the card is already matched
        }

        // Check if the selected card matches the previous card
        if (selectedCard.getValue().getAnswer().equals(previousCard.getValue().getAnswer())) {
            // Cards match
            matchedCards.add(selectedCard.getValue());
            matchedPairs++;
            if (matchedPairs == totalPairs) {
                JOptionPane.showMessageDialog(frame, "Congratulations! You've matched all pairs!");
                new DeckDisplay(deck);
                frame.dispose();
            }
        } else {
            // Cards don't match
            JOptionPane.showMessageDialog(frame, "Sorry, the answer is incorrect!");
        }
        // Reset previous card
        previousCard = null;
        previousBtn = null;
        // Update the display
        updateButtons();
    }

    /**
     * updateButtons updates the card buttons after a match has been successfully made or not successfully made.
     */
    private void updateButtons() {
        for (int i = 0; i < totalPairs * 2; i++) {
            JButton button = answerButtons[i];
            FlashCard card = cards.get(i).getValue();
            if (matchedCards.contains(card)) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }
    }
}