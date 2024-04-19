import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

import static java.awt.Color.black;

public class Learn {

    private final Deck deck;
    private JFrame frame;
    private FlashCard current_card;
    private JTextArea answer_box;
    private JLabel reviewText;

    private static HashMap<FlashCard, Integer> weights = new HashMap<>();

    public Learn (Deck deck) {
        this.deck = deck;
        current_card = deck.get(0);
        initialize_map();
        start();
    }

    private void start() {
        frame = new JFrame("Learn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel answerPanel = new JPanel();
        JPanel continuePanel = new JPanel();

        Font font = new Font("Helvetica", Font.BOLD,20);

        // first question shown
        reviewText = new JLabel(current_card.question,SwingConstants.CENTER);
        reviewText.setFont(font);

        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);

        answer_box = new JTextArea(1,20);
        answer_box.setBorder(blackBorder);

        reviewText.setBorder(blackBorder);
        reviewText.setMinimumSize(new Dimension(430,250));
        reviewText.setPreferredSize(new Dimension(430,250));
        reviewText.setMaximumSize(new Dimension(430,250));

        JButton backButton = new JButton("Back");
        JButton submit = new JButton("Submit");
        JButton continue_button = new JButton("Continue");

        mainPanel.add(BorderLayout.NORTH,backButton);
        mainPanel.add(BorderLayout.CENTER,reviewText);
        answerPanel.add(BorderLayout.WEST, answer_box);
        answerPanel.add(BorderLayout.EAST, submit);
        continuePanel.add(BorderLayout.SOUTH, continue_button);

        frame.getContentPane().add(BorderLayout.NORTH, mainPanel);
        frame.getContentPane().add(BorderLayout.CENTER, answerPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, continuePanel);
        frame.setSize(450,600);
        frame.setVisible(true);

        submit.addActionListener(e -> {
            String inputText = answer_box.getText();
            if (inputText.isEmpty()) {
                answer_box.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
            else {
                if (inputText.toLowerCase().contains(current_card.answer.toLowerCase()) || current_card.answer.toLowerCase().contains(inputText.toLowerCase())) {
                    reviewText.setForeground(Color.decode("#91FF8A"));
                    reviewText.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    answer_box.setEnabled(false);
                    reviewText.setText("<html>" + current_card.question + "<br><br>" + current_card.answer + "<html>");
                }
                else {
                    reviewText.setForeground(Color.RED);
                    reviewText.setBorder(BorderFactory.createLineBorder(Color.RED));
                    answer_box.setEnabled(false);
                    reviewText.setText("<html>" + current_card.question + "<br><br>" + current_card.answer + "<html>");
                }
            }
        });


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

    public static void main(String[] args) {
        DeckDatabase db = new DeckDatabase();
        Deck deck = db.read().get(1);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new Learn(deck);}
        });
        /*

        Learn temp = new Learn(deck);
        weights.replace(deck.get(0), 9);
        weights.replace(deck.get(1), 1);

        for (int i = 0; i < 1000; i++) {
            System.out.println(temp.cdf().question);
        }



        //System.out.println(weights);

         */
    }
}
