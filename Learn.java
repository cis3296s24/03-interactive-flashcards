import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Learn allows the user be tested on their knowledge by inputting an answer into a text
 * field and being notified that they were correct or incorrect. If the user is correct,
 * the weight of the card decreases, making it less likely to be seen again, and if they get it
 * incorrect, it is more likely they will see that card again.
 */
public class Learn extends JDialog{
    private JPanel panel1;
    private JButton backButton;
    private JButton submitButton;
    private JButton continueButton;
    private JTextField inputBox;
    private JLabel reviewLabel;
    private Deck deck;
    private FlashCard curr_card;
    private static HashMap<FlashCard, Integer> weights = new HashMap<>();

    /**
     * The constructor creates the UI, buttons, and action listeners for the learn functionalisty
     * @param parent JFrame Parent
     * @param deck Deck
     */
    public Learn(JFrame parent, Deck deck) {
        super(parent);
        this.deck = deck;
        initialize_map();
        curr_card = this.deck.get(0);
        setTitle("Review");
        setContentPane(panel1);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        reviewLabel.setText(curr_card.question);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        reviewLabel.setBorder(blackBorder);
        reviewLabel.setMinimumSize(new Dimension(430,250));
        reviewLabel.setPreferredSize(new Dimension(430,250));
        reviewLabel.setMaximumSize(new Dimension(430,250));
        reviewLabel.setVerticalTextPosition(SwingConstants.CENTER);

        submitButton.addActionListener(e -> {
            String inputText = inputBox.getText();
            if (inputText.isEmpty()) {
                inputBox.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            else {
                if (inputText.toLowerCase().contains(curr_card.answer.toLowerCase()) || curr_card.answer.toLowerCase().contains(inputText.toLowerCase())) {
                    reviewLabel.setForeground(Color.decode("#91FF8A"));
                    reviewLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    update_weight(curr_card, true);

                }
                else {
                    reviewLabel.setForeground(Color.RED);
                    reviewLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
                    update_weight(curr_card, false);
                }
                inputBox.setEnabled(false);
                reviewLabel.setText("<html>" + curr_card.question + "<br><br>" + curr_card.answer + "<html>");
            }
        });

        continueButton.addActionListener(e -> {
            curr_card = cdf();
            reviewLabel.setText(curr_card.question);
            reviewLabel.setForeground(Color.BLACK);
            reviewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            inputBox.setEnabled(true);
            inputBox.setText("Please Enter Answer");
        });

        backButton.addActionListener(e -> {
            new DeckDisplay(deck);
            dispose();
        });

        inputBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                inputBox.setText("");
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Initializes the Hashmap for weights to a weight ot 5 each
     */
    private void initialize_map() {
        for (int i = 0; i < deck.size(); i++) {
            weights.put(deck.get(i), 5);
        }
    }

    /**
     * Updates weight of the card depending on whether the user gets the question right or wrong
     * @param card Flashcard
     * @param isCorrect boolean
     */
    private void update_weight(FlashCard card, boolean isCorrect) {
        if (isCorrect && weights.get(card) != 1) { weights.replace(card, weights.get(card) - 1); }
        else if (!isCorrect && weights.get(card) != 10) { weights.replace(card, weights.get(card) + 1); }
    }

    /**
     * the cumulative density function returns a flashcard randomly based on the weights of the cards.
     * A card with a larger weight is more likely to be chosen and a card with a lower weight is less likely to be chosen.
     * @return Flashcard
     */
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
}
