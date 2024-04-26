import javax.swing.*;
import java.awt.*;

/**
 * DeckDisplay creates the deck display panel and automatically populates it with the flashcard buttons
 * Allows for user to perform any of the quiz functionality on the deck passed to it and enter the flashcard builder from
 * any of the flashcard buttons
 * Option to delete the deck as well.
 */
public class DeckDisplay extends JFrame {
    private JPanel cardPanel;
    private JPanel mainPanel;
    private DeckDatabase database = new DeckDatabase();

    /**
     * Constructor performs the UI and the population of the buttons and images.
     * @param deck Deck
     */
    public DeckDisplay(Deck deck) {
        clearCardPanel();
        setTitle("Deck Display");
        setPreferredSize(new Dimension(750, 500));

        // Set background color
        getContentPane().setBackground(new Color(225, 252, 255));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(225, 252, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        cardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cardPanel.setAlignmentY(JPanel.TOP_ALIGNMENT); // Align at the top
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
            cardButton.setFont(new Font("AppleGothic", Font.PLAIN, 22)); // Set font
            cardPanel.add(cardButton);
            //actionlistener for each card, allows flashcardbuilder to edit card
            cardButton.addActionListener(e -> {
                dispose();
                new FlashCardBuilder(null, deck, card);
            });
        }

        //Create add new card button
        JButton plus_button = new JButton("+ Add Card");
        plus_button.setMargin(new Insets(10, 10, 10, 10)); // Add margins for spacing
        plus_button.setBackground(new Color(225, 252, 255)); // Set background color
        plus_button.setForeground(new Color(75, 90, 152)); // Set text color
        plus_button.setFont(new Font("AppleGothic", Font.PLAIN, 22)); // Set font
        plus_button.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
        cardPanel.add(plus_button);

        //Actionlistener to create and edit new card
        plus_button.addActionListener(e -> {
            FlashCard new_flashcard = new FlashCard();
            deck.add(new_flashcard);
            dispose();
            new FlashCardBuilder(null, deck, new_flashcard);
        });
        revalidate();
        repaint();

        //Create panel and buttons, add to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Align buttons to center
        //JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Align buttons to center
        buttonPanel.setBackground(new Color(225, 252, 255));

        JButton reviewButton = new JButton("Review");
        JButton tfButton = new JButton("Quiz");
        JButton backButton = new JButton("Back");
        JButton learnButton = new JButton("Learn");
        JButton mcButton = new JButton("Multiple Choice");
        JButton matchingButton = new JButton("Matching");

        setbutton(reviewButton);
        setbutton(tfButton);
        setbutton(learnButton);
        setbutton(mcButton);
        setbutton(matchingButton);

        buttonPanel.add(reviewButton);
        buttonPanel.add(tfButton);
        buttonPanel.add(learnButton);
        buttonPanel.add(mcButton);
        buttonPanel.add(matchingButton);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        backButtonPanel.setBackground(new Color(225, 252, 255)); // Set background color
        backButtonPanel.setForeground(new Color(75, 90, 152)); // Set text color

        backButton.setBackground(new Color(225, 252, 255)); // Set background color
        backButton.setForeground(new Color(75, 90, 152)); // Set text color
        backButton.setFont(new Font("AppleGothic", Font.PLAIN, 24)); // Set font

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(225, 252, 255)); // Set background color
        deleteButton.setForeground(new Color(75, 90, 152)); // Set text color
        deleteButton.setFont(new Font("AppleGothic", Font.PLAIN, 24)); // Set font

        backButtonPanel.add(deleteButton);
        backButtonPanel.add(backButton);


        //Add to frame
        getContentPane().add(BorderLayout.SOUTH,backButtonPanel);
        getContentPane().add(BorderLayout.CENTER, cardPanel);
        getContentPane().add(BorderLayout.NORTH,buttonPanel);

        setSize(450,600);

        //MC quiz button action listener
        mcButton.addActionListener(e -> {
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(this, "Add at least four flashcards to your deck to play!");
                return;
            }
            dispose();
            new MCQuiz(deck);
        });

        //back button action listener
        backButton.addActionListener(e -> {
            dispose();
            new DeckMenu(null);
        });

        //review button action listener
        reviewButton.addActionListener(e -> {
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(this, "Add at least one flashcards to your deck to review!");
                return;
            }
            dispose();
            new Review(null, deck);
        });

        //TF quiz button action listener
        tfButton.addActionListener(e -> {
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(this, "Add at least two flashcards to your deck to play!");
                return;
            }
            dispose();
            new TrueFalse(null, deck);
        });

        //learn button action listener
        learnButton.addActionListener(e -> {
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(this, "Add at least one flashcard to your deck to learn!");
                return;
            }
            dispose();
            new Learn(null, deck);
        });

        //matching button action listener
        matchingButton.addActionListener(e -> {
            if (deck.size() < 2) {
                JOptionPane.showMessageDialog(this, "Add at least one flashcard to your deck to play!");
                return;
            }
            dispose();
            new MatchingGame(deck);
        });

        //delete button action listener
        deleteButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(DeckDisplay.this, "Are you sure you want to delete this deck?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // User clicked Yes, perform deletion
                database.delete(deck);
                dispose();
                new DeckMenu(null);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * setbutton sets up the look of a specified button
     * @param button JButton
     */
    public void setbutton(JButton button){
        button.setBackground(new Color(225, 252, 255)); // Set background color
        button.setForeground(new Color(75, 90, 152)); // Set text color
        button.setFont(new Font("AppleGothic", Font.PLAIN, 20)); // Set font
    }

    /**
     * ClearCardPanel clears the grid of flashcard buttons
     */
    private void clearCardPanel() {
        if(this.cardPanel != null) {
            cardPanel.removeAll();
            revalidate(); // Update layout
            repaint(); // Redraw components
        }
    }
}