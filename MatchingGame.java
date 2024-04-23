import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

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

    private void initializeUI() {
        frame = new JFrame("Matching Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.setTitle("Multiple Choice Quiz Frame");
        //frame.setPreferredSize(new Dimension(500, 500));

        // Set background color
        frame.getContentPane().setBackground(new Color(225, 252, 255));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(225, 252, 255)); // Set background color
        backButton.setForeground(new Color(75, 90, 152)); // Set text color
        backButton.setFont(new Font("AppleGothic", Font.PLAIN, 24)); // Set font


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
            button.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font
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
//        frame.add(questionLabel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Frame settings
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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