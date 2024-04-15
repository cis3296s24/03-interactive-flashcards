import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TrueFalseQuiz {
    private JFrame frame;
    private JLabel questionLabel;
    private Deck deck;
    private FlashCard currentCard;
    private boolean answer;

    public TrueFalseQuiz(Deck deck) {
        this.deck = deck;
        initializeUI();
    }

    private void initializeUI() {

        frame = new JFrame("True/False Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Setting up the question label
        questionLabel = new JLabel("", SwingConstants.CENTER);
        updateQuestion();

        // Setting up True and False buttons
        JButton trueButton = new JButton("True");
        JButton falseButton = new JButton("False");
        JButton backButton = new JButton("Back");

        trueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer(true);
            }
        });

        falseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer(false);
            }
        });

        backButton.addActionListener(e -> {
                new DeckMenu(deck.deck_name);
                frame.dispose();
            }
        );

        JPanel backPanel = new JPanel();
        backPanel.add(backButton);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(trueButton);
        buttonPanel.add(falseButton);

        // Add components to frame
        frame.add(backPanel, BorderLayout.NORTH);
        frame.add(questionLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Frame settings
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void updateQuestion() {
        // Get a random card from the deck
        Random random = new Random();
        int pickNum = random.nextInt(deck.size());
        currentCard = deck.get(pickNum);
        if (Math.random() > 0.5) {
            answer = true;
            questionLabel.setText("Q: " + currentCard.getQuestion() + "\n" + "A: " + currentCard.getAnswer());
        } else {
            answer = false;
            int newNum = random.nextInt(deck.size());
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(frame, "Add at least two flashcards to your deck to play!");
                return;
            }
            while (newNum == pickNum) {
                newNum = random.nextInt(deck.size());
            }
            FlashCard fc = deck.get(newNum);
            questionLabel.setText("Q: " + currentCard.getQuestion() + "\n" + "A: " + fc.getAnswer());
        }

    }

    private void checkAnswer(boolean userAnswer) {
        if (userAnswer == answer) {
            JOptionPane.showMessageDialog(frame, "Correct!");
        } else {
            JOptionPane.showMessageDialog(frame, "Incorrect!");
        }

        // Load new question
        updateQuestion();
    }
}
