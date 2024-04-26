import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * True and false quiz takes a question from the deck and a randomized answer and tests the user if the pair is either true or false.
 * If the user gets it right they are notified correct, and vice verse.
 */
public class TrueFalse extends JDialog{
    private JButton trueButton;
    private JPanel panel1;
    private JButton falseButton;
    private JButton backButton;
    private JLabel TestQ;
    private JLabel TestA;
    private Deck deck;
    private FlashCard currentCard;
    private boolean answer;

    /**
     * Constructor initializes UI, buttons, and actionlisteners.
     *
     * @param parent JFrame parent
     * @param d Deck
     */
    public TrueFalse(JFrame parent, Deck d) {

        super(parent);
        deck = d;
        setTitle("T/F Quiz");
        setContentPane(panel1);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        updateQuestion();

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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeckDisplay(deck);
                dispose();
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Updates question after user completes previous true false question, and updates UI
     * accordingly.
     */
    private void updateQuestion() {
        // Get a random card from the deck
        Random random = new Random();
        int pickNum = random.nextInt(deck.size());
        currentCard = deck.get(pickNum);
        if (Math.random() > 0.5) {
            answer = true;
            TestQ.setText("Q: " + currentCard.getQuestion());
            TestA.setText("A: " + currentCard.getAnswer());
        } else {
            answer = false;
            int newNum = random.nextInt(deck.size());
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(this, "Add at least two flashcards to your deck to play!");
                return;
            }
            while (newNum == pickNum) {
                newNum = random.nextInt(deck.size());
            }
            FlashCard fc = deck.get(newNum);
            TestQ.setText("Q: " + currentCard.getQuestion());
            TestA.setText("A: " + fc.getAnswer());
        }

    }

    /**
     * Checks whether the answer it true or false and displays message to the user
     * @param userAnswer bool answer
     */
    private void checkAnswer(boolean userAnswer) {
        if (userAnswer == answer) {
            JOptionPane.showMessageDialog(this, "Correct!");
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect!");
        }

        // Load new question
        updateQuestion();
    }


}
