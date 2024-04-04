import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private Deck cardList = new Deck("cardList");
    private JFrame frame;

    public FlashCardBuilder() {
        frame = new JFrame("Flash Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Font font = new Font("Helvetica", Font.BOLD,20);

        question = new JTextArea(6,20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);
        question.setBorder(BorderFactory.createCompoundBorder(question.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane questionScroll = new JScrollPane(question);
        questionScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        questionScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6,20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(font);
        answer.setBorder(BorderFactory.createCompoundBorder(answer.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane answerScroll = new JScrollPane(answer);
        answerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Save Card");

        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");
        mainPanel.add(questionLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between components
        mainPanel.add(questionScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(answerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(answerScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(nextButton);


        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(450, 600);
        frame.setVisible(true);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlashCard card;
                card = getData(question, answer);
                cardList.add(card);
                System.out.println("Question: " + cardList.get(cardList.size()-1).question + " Answer: " + cardList.get(cardList.size()-1).answer);
            }
        });

        // flashcard event button
        JButton reviewButton = new JButton("Review");
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Review review = new Review(cardList);
                review.start();
            }
        });

        mainPanel.add(questionLabel);
        mainPanel.add(questionScroll);
        mainPanel.add(answerLabel);
        mainPanel.add(answerScroll);
        mainPanel.add(nextButton);
        mainPanel.add(reviewButton);

        frame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        frame.setSize(450,600);
        frame.setVisible(true);
    }

    public FlashCard getData(JTextArea question, JTextArea answer) {
        FlashCard card = new FlashCard();
        card.question = question.getText();
        card.answer = answer.getText();
        question.setText("");
        answer.setText("");
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });
    }
}