import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Color.black;

/**
 * The review class allows for users to iterate through and flip cards much like how physical flashcards would work.
 */
public class Review extends JDialog{
    private JButton flipButton;
    private JPanel panel1;
    private JButton nextButton;
    private JButton lastButton;
    private JButton displaySettingsButton;
    private JButton backButton;
    private JLabel reviewLabel;
    private Deck cardList;
    private ArrayList<Deck> deckList;
    private int currentCard;
    private DeckDatabase database = new DeckDatabase();

    /**
     * Constructor takes the parent frame and the deck, and creates the UI, the buttons and the actionlisteners.
     * Allows user to move through deck either forwards ot backwards, and the ability to flip each card to see the back/front question/answer.
     * @param parent JFrame parent
     * @param d Deck
     */
    public Review(JFrame parent, Deck d) {

        super(parent);
        setTitle("Review");
        setContentPane(panel1);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);

        reviewLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cardList = d;
        database.write(cardList);
        this.deckList = database.read();
        currentCard = 0;

        reviewLabel.setText(cardList.get(currentCard).question);
        Border blackBorder = BorderFactory.createLineBorder(black);
        reviewLabel.setBorder(blackBorder);
        reviewLabel.setMinimumSize(new Dimension(430,250));
        reviewLabel.setPreferredSize(new Dimension(430,250));
        reviewLabel.setMaximumSize(new Dimension(430,250));
        reviewLabel.setVerticalTextPosition(SwingConstants.CENTER);

        // button listeners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeckDisplay(cardList);
                dispose();
            }
        });
        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reviewLabel.getText() == cardList.get(currentCard).question) {
                    reviewLabel.setText(cardList.get(currentCard).answer);
                }
                else if (reviewLabel.getText() == cardList.get(currentCard).answer) {
                    reviewLabel.setText(cardList.get(currentCard).question);
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCard + 1 == cardList.size()) {
                    currentCard = -1;
                }
                currentCard++;
                reviewLabel.setText(cardList.get(currentCard).question);
            }
        });
        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCard - 1 < 0) {
                    currentCard = cardList.size();
                }
                currentCard--;
                reviewLabel.setText(cardList.get(currentCard).question);
            }
        });
//        displaySettingsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                DisplaySettingsMenu DSMenu = new DisplaySettingsMenu(fontType, fontSize);
////                DSMenu.start();
////                fontType = DSMenu.getNewFont();
////                fontSize = DSMenu.getNewFontSize();
////
////                Font getNewFont = new Font(fontType, Font.BOLD,fontSize);    //sets users font choice
////                reviewText.setFont(getNewFont); //changes font to getNewFont
//            }
//        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }
}
