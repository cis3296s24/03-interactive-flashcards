import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Review {
    private JFrame frame;
    private ArrayList<FlashCard> cardList;

    public Review(ArrayList<FlashCard> cardList) {
        this.cardList = cardList;
        start();
        System.out.println("check");
    }
    public void start() {
        // create frame
        frame = new JFrame("Review");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel reviewPanel = new JPanel();

        // set font
        Font font = new Font("Helvetica", Font.BOLD,20);

        // first question shown
        JLabel reviewText = new JLabel(cardList.get(0).question);
        reviewText.setFont(font);

        // flip button
        JButton flipButton = new JButton("Flip");

        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(reviewText);
                reviewText.setText(cardList.get(0).answer);
            }
        });

        // add components to panel
        reviewPanel.add(reviewText);
        reviewPanel.add(flipButton);

        // format and set visible
        frame.getContentPane().add(BorderLayout.CENTER,reviewPanel);
        frame.setSize(450,600);
        frame.setVisible(true);
    }
}
