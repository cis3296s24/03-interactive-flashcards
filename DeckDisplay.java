import javax.swing.*;
import java.awt.*;

public class DeckDisplay extends JFrame {
    private JPanel cardPanel;

    public DeckDisplay(Deck deck) {
        System.out.println("in display class");
        clearCardPanel();
        setTitle("Deck Display");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cardPanel = new JPanel(new GridLayout(0, 4));
        for (int i = 0;i < deck.size();i++) {
            FlashCard card = deck.get(i);
            JButton cardButton = new JButton(card.getQuestion());
            cardPanel.add(cardButton);
            int index = i;
            //actionlistener for each card, allows flashcardbuilder to edit card
            cardButton.addActionListener(e -> {
                new FlashCardBuilder2(null, deck, deck.get(index));
            });
        }

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        getContentPane().add(scrollPane);

        //Create add new card button
        JButton plus_button = new JButton("+ Add Card");
        plus_button.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
        cardPanel.add(plus_button);
        //Actionlistener to create and edit new card
        plus_button.addActionListener(e -> {
            FlashCard new_flashcard = new FlashCard();
            deck.add(new_flashcard);
            new FlashCardBuilder2(null, deck, new_flashcard);
            dispose();
        });
        revalidate();
        repaint();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearCardPanel() {
        if(this.cardPanel != null) {
            cardPanel.removeAll();
            revalidate(); // Update layout
            repaint(); // Redraw components
        }
    }
}