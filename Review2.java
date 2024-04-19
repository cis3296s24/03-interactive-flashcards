import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Review2 extends JDialog{
    private JButton flipButton;
    private JPanel panel1;
    private JButton nextButton;
    private JButton lastButton;
    private JButton displaySettingsButton;
    private JButton backButton;
    private Deck cardList;
    private ArrayList<Deck> deckList;
    private int currentCard;
    private DeckDatabase database = new DeckDatabase();


    public Review2(JFrame parent, Deck d) {

        super(parent);
        cardList = d;
        setTitle("Review");
        setContentPane(panel1);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        displaySettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }
}
