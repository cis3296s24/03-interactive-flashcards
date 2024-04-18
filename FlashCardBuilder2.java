import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlashCardBuilder2 extends JDialog{
    private JPanel CardBuilder;
    private JTextArea QuestiontextArea;
    private JTextArea AnswertextArea;
    private JButton saveCardButton;
    private Deck deck;
    private FlashCard card;
    private DeckDatabase database = new DeckDatabase();

    public FlashCardBuilder2(JFrame parent, Deck d, FlashCard c){

        super(parent);
        setTitle("Edit Card");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(CardBuilder);
        setMinimumSize(new Dimension(600, 600));
        setModal(true);
        setLocationRelativeTo(parent);

        deck = d;
        card = c;
        d.delete(c);

        saveCardButton.addActionListener(new ActionListener() {
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
        deck.add(card);
        database.write(deck);
        dispose();
    }

}
