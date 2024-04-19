import javax.swing.*;
import java.awt.*;

public class DeckDisplay extends JFrame {
    private JPanel cardPanel;
    private JPanel mainPanel;

    public DeckDisplay(Deck deck) {
        clearCardPanel();
        setTitle("Deck Display");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));

        // Set background color
        getContentPane().setBackground(new Color(225, 252, 255));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(225, 252, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        cardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cardPanel.setAlignmentY(JPanel.TOP_ALIGNMENT); // Align at the top
        //cardPanel = new JPanel(new GridLayout(0, 4));
        cardPanel.setBackground(new Color(225, 252, 255));
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        for (int i = 0;i < deck.size();i++) {
            FlashCard card = deck.get(i);
            JButton cardButton = new JButton(card.getQuestion());
            cardButton.setMargin(new Insets(10, 10, 10, 10)); // Add margins for spacing
            cardButton.setBackground(new Color(225, 252, 255)); // Set background color
            cardButton.setForeground(new Color(75, 90, 152)); // Set text color
            cardButton.setFont(new Font("AppleGothic", Font.PLAIN, 14)); // Set font
            cardPanel.add(cardButton);
            //actionlistener for each card, allows flashcardbuilder to edit card
            cardButton.addActionListener(e -> {
                new FlashCardBuilder2(null, deck, card);
                dispose();
            });
        }

        //Create add new card button
        JButton plus_button = new JButton("+ Add Card");
        plus_button.setMargin(new Insets(10, 10, 10, 10)); // Add margins for spacing
        plus_button.setBackground(new Color(225, 252, 255)); // Set background color
        plus_button.setForeground(new Color(75, 90, 152)); // Set text color
        plus_button.setFont(new Font("AppleGothic", Font.PLAIN, 14)); // Set font
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

        //Create panel and buttons, add to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Align buttons to center
        buttonPanel.setBackground(new Color(225, 252, 255));
        JButton reviewButton = new JButton("Review");
        JButton tfButton = new JButton("Quiz");
        reviewButton.setBackground(new Color(225, 252, 255)); // Set background color
        reviewButton.setForeground(new Color(75, 90, 152)); // Set text color
        reviewButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font
        tfButton.setBackground(new Color(225, 252, 255)); // Set background color
        tfButton.setForeground(new Color(75, 90, 152)); // Set text color
        tfButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font

        buttonPanel.add(reviewButton);
        buttonPanel.add(tfButton);


        //Add to frame
        getContentPane().add(BorderLayout.NORTH,mainPanel);
        getContentPane().add(BorderLayout.CENTER, cardPanel);
        getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        setSize(450,600);

        //review button action listener
        reviewButton.addActionListener(e -> {
            new Review(deck);
            dispose();
        });

        //quiz button action listener
        tfButton.addActionListener(e -> {
            new TrueFalse(null, deck);
            dispose();
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

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