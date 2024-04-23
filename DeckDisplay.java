import javax.swing.*;
import java.awt.*;

public class DeckDisplay extends JFrame {
    private JPanel cardPanel;
    private JPanel mainPanel;
    private DeckDatabase database = new DeckDatabase();

    public DeckDisplay(Deck deck) {
        clearCardPanel();
        setTitle("Deck Display");
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(750, 500));

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
                dispose();
                new FlashCardBuilder2(null, deck, card);
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
            dispose();
            new FlashCardBuilder2(null, deck, new_flashcard);
        });
        revalidate();
        repaint();

        //Create panel and buttons, add to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Align buttons to center
        buttonPanel.setBackground(new Color(225, 252, 255));

        JButton reviewButton = new JButton("Review");
        JButton tfButton = new JButton("Quiz");
        JButton backButton = new JButton("Back");
        JButton learnButton = new JButton("Learn");
        JButton mcButton = new JButton("Multiple Choice");

        reviewButton.setBackground(new Color(225, 252, 255)); // Set background color
        reviewButton.setForeground(new Color(75, 90, 152)); // Set text color
        reviewButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font
        tfButton.setBackground(new Color(225, 252, 255)); // Set background color
        tfButton.setForeground(new Color(75, 90, 152)); // Set text color
        tfButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font
        learnButton.setBackground(new Color(225, 252, 255)); // Set background color
        learnButton.setForeground(new Color(75, 90, 152)); // Set text color
        learnButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font

        mcButton.setBackground(new Color(225, 252, 255)); // Set background color
        mcButton.setForeground(new Color(75, 90, 152)); // Set text color
        mcButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font

        //buttonPanel.add(backButton);
        buttonPanel.add(reviewButton);
        buttonPanel.add(tfButton);
        buttonPanel.add(learnButton);
        buttonPanel.add(mcButton);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        backButtonPanel.setBackground(new Color(225, 252, 255)); // Set background color
        backButtonPanel.setForeground(new Color(75, 90, 152)); // Set text color

        backButton.setBackground(new Color(225, 252, 255)); // Set background color
        backButton.setForeground(new Color(75, 90, 152)); // Set text color
        backButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(225, 252, 255)); // Set background color
        deleteButton.setForeground(new Color(75, 90, 152)); // Set text color
        deleteButton.setFont(new Font("AppleGothic", Font.PLAIN, 28)); // Set font

        backButtonPanel.add(deleteButton);
        backButtonPanel.add(backButton);


        //Add to frame
        getContentPane().add(BorderLayout.SOUTH,backButtonPanel);
        getContentPane().add(BorderLayout.CENTER, cardPanel);
        getContentPane().add(BorderLayout.NORTH,buttonPanel);

        setSize(450,600);

        //MC quiz button action listener
        mcButton.addActionListener(e -> {
            dispose();
            new MCQuiz(deck);
        });

        //back button action listener
        backButton.addActionListener(e -> {
            dispose();
            new DeckMenu2(null);
        });

        //review button action listener
        reviewButton.addActionListener(e -> {
            dispose();
            new Review2(null, deck);
        });

        //quiz button action listener
        tfButton.addActionListener(e -> {
            dispose();
            new TrueFalse(null, deck);
        });

        learnButton.addActionListener(e -> {
            dispose();
            new Learn2(null, deck);
        });

        deleteButton.addActionListener(e -> {
            database.delete(deck);
            dispose();
            new DeckMenu2(null);
        });


        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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