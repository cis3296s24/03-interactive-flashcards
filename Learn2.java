import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Learn2 extends JDialog{
    private JPanel panel1;
    private JButton backButton;
    private JButton submitButton;
    private JButton continueButton;
    private JTextField inputBox;
    private JLabel reviewLabel;
    private Deck deck;
    private FlashCard curr_card;
    private static HashMap<FlashCard, Integer> weights = new HashMap<>();

    public Learn2 (JFrame parent, Deck deck) {
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



        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);


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
}
