import java.awt.*;
import javax.swing.*;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private DeckDatabase database = new DeckDatabase();
    private JFrame frame;
    private FlashCard card;
    private Deck deck;

    public FlashCardBuilder(FlashCard card, Deck deck){
        this.deck = deck;
        this.card = card;
        //Delete existing card (overwrite)
        //If not changed it will be added back in
        deck.delete(card);
        build_card();
    }

    private void build_card() {
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Font font = new Font("Helvetica", Font.BOLD,20);

        //creates question box
        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);
        question.setBorder(BorderFactory.createCompoundBorder(question.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        question.setText(card.question);

        //creates scroll for question box
        JScrollPane questionScroll = new JScrollPane(question);
        questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //creates answer box
        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(font);
        answer.setBorder(BorderFactory.createCompoundBorder(answer.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        answer.setText(card.answer);

        //creates scroll for answer box
        JScrollPane answerScroll = new JScrollPane(answer);
        answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Create button and labels
        JButton nextButton = new JButton("Save Card");
        JLabel questionLabel = new JLabel("Question");  //creates question box label
        JLabel answerLabel = new JLabel("Answer");  //creates answer box label

        // Center Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton, BorderLayout.CENTER);

        //next button
        nextButton.addActionListener(e -> {
            updateCard();
        });

        //adds everything to main panel
        mainPanel.add(questionLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between components
        mainPanel.add(questionScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(answerLabel, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(answerScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(buttonPanel); //adds buttonPanel to mainPanel

        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(450,600);
        frame.setVisible(true);
    }

    //Update card, add card to deck, write deck to database, exit builder
    public void updateCard() {
        card.question = question.getText();
        card.answer = answer.getText();
        deck.add(card);
        database.write(deck);
        new DeckMenu();
    }

    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });
    }
    */
}