import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DeckMenu {
    private JFrame frame;
    private ArrayList<Deck> decks;
    private DeckDatabase database = new DeckDatabase();
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

        mainPanel.add(dropdownLabel);
        mainPanel.add(dropdown);


        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(450,600);
        frame.setVisible(true);
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
