import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class cardGrid extends JDialog {
    private JPanel cardsPanel;
    private JButton reviewButton;
    private JButton quizButton;
    private JPanel gridPanel;
    private JButton backButton;
    private JSpinner.ListEditor deckCards;
    private Deck curr_deck;
    private DeckDatabase database = new DeckDatabase();

    public cardGrid(JFrame parent, Deck deck) {
        super(parent);
        curr_deck = deck;
        setTitle("Study Set");
        setContentPane(cardsPanel);
        setSize(800, 800);
        setModal(true);
        setLocationRelativeTo(parent);
        //curr_deck = deck;
        //displayCards(curr_deck);
        //setVisible(true);

        //gridPanel = new JPanel(new GridLayout(0, 1));

//        reviewButton.addActionListener(e -> new Review(curr_deck));
//        quizButton.addActionListener(e -> new TrueFalseQuiz(curr_deck));
//        backButton.addActionListener(e -> {
//            dispose(); // Close the current panel
//            new DeckMenu2(null); // Open a new instance of DeckMenu2
//        });

        //createUIComponents();
        //setVisible(true);


        setVisible(true);
    }


    /**
     * Clear grid
     */
    private void clearCardGrid() {
        removeAll();
        revalidate(); // Update layout
        repaint(); // Redraw components
    }

    private void createUIComponents() {
        //gridPanel.setLayout(new GridLayout(0, 4));
        clearCardGrid();
        //Iterate through deck creating buttons for each card
        for (int i = 0; i < curr_deck.size(); i++) {
            FlashCard card = curr_deck.get(i);
            JButton cardButton = new JButton(card.question);
            cardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
            add(cardButton);
            System.out.println("Testing");
            int index = i;
            //actionlistener for each card, allows flashcardbuilder to edit card
            cardButton.addActionListener(actionEvent -> {
                new FlashCardBuilder(curr_deck.get(index), curr_deck);
            });
        }
        // TODO: place custom component creation code here
    }

//    private void createUIComponents() {
//        // TODO: place custom component creation code here
//        //Clear grid
//        //clearCardGrid();
//        //Iterate through deck creating buttons for each card
//        if(curr_deck == null ){
//            return;
//        }else{
//            for (int i = 0;i < curr_deck.size();i++) {
//                FlashCard card = curr_deck.get(i);
//                JButton cardButton = new JButton(card.question);
//                cardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
//                gridPanel.add(cardButton);
//                int index = i;
//                //actionlistener for each card, allows flashcardbuilder to edit card
//                cardButton.addActionListener(e -> {
//                    new FlashCardBuilder(curr_deck.get(index),curr_deck);
//                });
//            }
//            //Create add new card button
//            JButton plus_button = new JButton("+ Add Card");
//            plus_button.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
//            gridPanel.add(plus_button);
//            //Actionlistener to create and edit new card
//            plus_button.addActionListener(e -> {
//                FlashCard new_flashcard = new FlashCard();
//                curr_deck.add(new_flashcard);
//                new FlashCardBuilder(new_flashcard,curr_deck);
//                dispose();
//            });
//            revalidate();
//            repaint();
//
//            setVisible(true);
//
//        }
//
//    }
//
//    public void initializeUI(){
//        createUIComponents();
//    }
//
//    /**
//     * Clear grid
//     */
//    private void clearCardGrid() {
//        gridPanel.removeAll();
//        revalidate(); // Update layout
//        repaint(); // Redraw components
//    }
//
//    public void setDeck(Deck deck){
//        curr_deck = deck;
//    }
//
//    public static void main(String[] args){
//
//        cardGrid test = new cardGrid(null);
//
//
//    }

}
