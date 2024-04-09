import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeckMenu {
    private JFrame frame;
    private ArrayList<Deck> decks;
    private DeckDatabase database = new DeckDatabase();
    private JPanel cardGrid = new JPanel();

    public DeckMenu() {
        frame = new JFrame("Flash Card");
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

        cardGrid.setLayout(new GridLayout((int)Math.ceil(decks.size()/4),4));

        mainPanel.add(dropdownLabel);
        mainPanel.add(dropdown);

        frame.getContentPane().add(BorderLayout.NORTH,mainPanel);
        frame.getContentPane().add(BorderLayout.CENTER, cardGrid);
        frame.setSize(450,600);
        frame.setVisible(true);

        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> combo = (JComboBox<String>) e.getSource();
                String selected = (String) combo.getSelectedItem();
                Deck deck;
                if (selected == "Select Deck...") {return;}
                else if (selected == "+ Create New Deck") {return;}
                else {
                    displayCards(getDeck(selected));
                }
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
