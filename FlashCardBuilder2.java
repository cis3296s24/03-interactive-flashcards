import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlashCardBuilder2 extends JDialog{
    private JPanel CardBuilder;
    private JTextArea QuestiontextArea;
    private JTextArea AnswertextArea;
    private JButton saveCardButton;
    private JButton BACKButton;
    private Deck deck;
    private FlashCard card;
    private DeckDatabase database = new DeckDatabase();

    public FlashCardBuilder2(JFrame parent, Deck d, FlashCard c){

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

        setVisible(true);
    }

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

}
