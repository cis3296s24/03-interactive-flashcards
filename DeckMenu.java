import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeckMenu {
    private JFrame frame;
    private ArrayList<Deck> decks;
    private Deck curr_deck;
    private DeckDatabase database = new DeckDatabase();
    private JPanel cardGrid = new JPanel();

    public DeckMenu() {
        frame = new JFrame("Deck Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //Create deck dropdown
        decks = database.read();
        String[] deckNames = new String[decks.size()+2];
        deckNames[0] = "Select Deck...";
        for (int i = 1; i <= decks.size(); i++) {
            deckNames[i] = decks.get(i-1).deck_name;
        }
        deckNames[decks.size()+1] = "+ Create New Deck";
        JComboBox<String> dropdown = new JComboBox<>(deckNames);
        JLabel dropdownLabel = new JLabel("Decks");

        cardGrid.setLayout(new GridLayout((int)Math.ceil((decks.size())/4),4));

        JPanel buttonPanel = new JPanel();
        JButton reviewButton = new JButton("Review");
        JButton tfButton = new JButton("Quiz");
        buttonPanel.add(reviewButton, BorderLayout.EAST);
        buttonPanel.add(tfButton, BorderLayout.WEST);

        mainPanel.add(dropdownLabel);
        mainPanel.add(dropdown);

        frame.getContentPane().add(BorderLayout.NORTH,mainPanel);
        frame.getContentPane().add(BorderLayout.CENTER, cardGrid);
        frame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        frame.setSize(450,600);
        frame.setVisible(true);

        reviewButton.addActionListener(e -> {
            new Review(curr_deck);
        });

        tfButton.addActionListener(e -> {
            new TrueFalseQuiz(curr_deck);
        });

        dropdown.addActionListener(e -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selected = (String) combo.getSelectedItem();
            if (selected == "Select Deck...") {clearCardGrid();}
            else if (selected == "+ Create New Deck") {}
            else {
                Deck temp = getDeck(selected);
                displayCards(temp);
                curr_deck = temp;
            }
        });
    }

    private Deck getDeck(String name) {
        for (Deck deck : decks) {
            if (deck.deck_name == name) {
                return deck;
            }
        }
        return null;
    }

    private void displayCards(Deck deck){
        clearCardGrid();
        for (int i = 0;i < deck.size();i++) {
            FlashCard card = deck.get(i);
            JButton cardButton = new JButton(card.question);
            cardButton.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
            cardGrid.add(cardButton);

        }
        JButton plus_button = new JButton("+ Add Card");
        plus_button.setMaximumSize(new Dimension(Integer.MAX_VALUE,20));
        cardGrid.add(plus_button);
        frame.revalidate();
        frame.repaint();
    }


    private void clearCardGrid() {
        cardGrid.removeAll();
        frame.revalidate(); // Update layout
        frame.repaint(); // Redraw components
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DeckMenu();
            }
        });
    }
}
