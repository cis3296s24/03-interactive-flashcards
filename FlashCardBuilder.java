import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Flashcard Builder allows user to create, edit, and delete flashcard questions and answers
 */
public class FlashCardBuilder extends JDialog{
    private JPanel CardBuilder;
    private JTextArea QuestiontextArea;
    private JTextArea AnswertextArea;
    private JButton saveCardButton;
    private JButton BACKButton;
    private JButton DELETEButton;
    private Deck deck;
    private FlashCard card;
    private DeckDatabase database = new DeckDatabase();

    /**
     * Constructor creates the UI, the buttons, and populates the flashcard text boxes given that the user
     * is editing the flashcards.
     * @param parent JFrame parent
     * @param d Deck
     * @param c Flashcard
     */
    public FlashCardBuilder(JFrame parent, Deck d, FlashCard c){

        super(parent);
        setTitle("Edit Card");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(CardBuilder);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        deck = d;
        card = c;

        QuestiontextArea.setText(card.question);
        QuestiontextArea.setLineWrap(true);
        QuestiontextArea.setWrapStyleWord(true);
        AnswertextArea.setText(card.answer);
        AnswertextArea.setLineWrap(true);
        AnswertextArea.setWrapStyleWord(true);

        d.delete(c);

        saveCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCard();
            }
        });
        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCard();
            }
        });

        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteCard();
            }
        });

        setVisible(true);
    }

    /**
     * Updates card updates the current cards question and answer with the question and answer
     * entered by the user, and returns to the DeckDisplay
     */
    private void updateCard() {
        card.question = QuestiontextArea.getText();
        card.answer = AnswertextArea.getText();
        if( card.question.equals("") || card.answer.equals("")){}
        else {
            deck.add(card);
            database.write(deck);
        }
        new DeckDisplay(deck);
        dispose();
    }

    /**
     * Deletes card and returns the user to the DeckDisplay
     */
    private void deleteCard() {
        deck.delete(card);
        database.write(deck);
        new DeckDisplay(deck);
        dispose();
    }

}
