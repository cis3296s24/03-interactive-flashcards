import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.awt.Color.black;

public class Review {
    private JFrame frame;
    private Deck cardList;
    private ArrayList<Deck> deckList;
    private int currentCard;
    private DeckDatabase database = new DeckDatabase();

    public Review(Deck cardList) {
        this.cardList = cardList;
        database.write(cardList);
        this.deckList = database.read();
        currentCard = 0;
        start();
    }
    public void start() {
        // create frame
        frame = new JFrame("Review");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel reviewPanel = new JPanel();

        // set font
        Font font = new Font("Helvetica", Font.BOLD,20);

        // first question shown
        JLabel reviewText = new JLabel(cardList.get(currentCard).question,SwingConstants.CENTER);
        reviewText.setFont(font);

        // format text
        Border blackBorder = BorderFactory.createLineBorder(black);
        reviewText.setBorder(blackBorder);
        reviewText.setMinimumSize(new Dimension(430,250));
        reviewText.setPreferredSize(new Dimension(430,250));
        reviewText.setMaximumSize(new Dimension(430,250));

        // initialize buttons
        JButton flipButton = new JButton("Flip");
        JButton nextButton = new JButton("Next");
        JButton lastButton = new JButton("Last");
        JButton backButton = new JButton("Back");
        JButton changeFontButton = new JButton("Change Font");  //creates changeFontButton

        // button listeners
        flipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reviewText.getText() == cardList.get(currentCard).question) {
                    reviewText.setText(cardList.get(currentCard).answer);
                }
                else if (reviewText.getText() == cardList.get(currentCard).answer) {
                    reviewText.setText(cardList.get(currentCard).question);
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCard + 1 == cardList.size()) {
                    return;
                }
                currentCard++;
                reviewText.setText(cardList.get(currentCard).question);
            }
        });

        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentCard - 1 < 0) {
                    return;
                }
                currentCard--;
                reviewText.setText(cardList.get(currentCard).question);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FlashCardBuilder();
            }
        });

        /*want user to be able to see all font options, pick one and change font of the flashcards */
        changeFontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //add method with creating buttons so user can see and pick which font they want 
                    //maybe display those fonts in the fonts theyre in
                
                //getChosenFont(){
                //list("...")
                    //if(getSelection = thisFont)
                        //return thisFont;     
                //}

                Font []fontList; 
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                
                fontList = ge.getAllFonts();    //returns array of all fonts available in graphical interface
                
                //should replace w/ method to display all options to screen and allow user to pick what they want 
                for(int i = 0; i<fontList.length; i++){ //prints all fonts to output
                    System.out.print(fontList[i].getName() + "\n"); 
                }

                
                Font getNewFont = new Font("Roboto Black Italic", Font.BOLD,20);    //sets users font choice
                reviewText.setFont(getNewFont); //changes font to getNewFont
            }
        }); 

        // add components to panel
        reviewPanel.add(backButton);
        reviewPanel.add(reviewText);
        reviewPanel.add(flipButton);
        reviewPanel.add(nextButton);
        reviewPanel.add(lastButton);
        reviewPanel.add(changeFontButton);  //adds changeFontButton to panel

        // format and set visible
        frame.getContentPane().add(BorderLayout.CENTER,reviewPanel);
        frame.setSize(450,600);
        frame.setVisible(true);
    }
}