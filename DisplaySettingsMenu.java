/*object to create, display and get user changes from a display settings menu */
import javax.swing.*;
import javax.swing.border.Border;

import static java.awt.Color.black;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Opens popup frame to display an options menu where the user can edit font size and the font of the text
 * for the current working pane/panel
 */
public class DisplaySettingsMenu{
    private JFrame DSFrame; 
    private JPopupMenu DSMenu; 
    
    private String newFont; //should change to new font
    private int newFontSize; 
    private Review2 thisReview;

    private static String []fontStrings;

    /**
     * Constructor takes fontType to switch the font to, the new font size,
     * and the panel/frame.
     * @param fontType String fonttype
     * @param newFontSize integer size
     * @param thisReview Review2 obj
     */
    DisplaySettingsMenu(String fontType, int newFontSize, Review2 thisReview){
        this.newFont = fontType; //initializes newText to whatever font is initially in Review
        this.newFontSize = newFontSize; 
        this.thisReview = thisReview; 
    }

    /**
     * start begins the UI popup and the action listeners
     * Is called from the constructor
     */
    public void start(){
        // create frame
        DSFrame = new JFrame("Display Settings");
        DSFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* change font */
        createFontStringArr();  // gets all fonts in system and puts them into fontStrings list

        JLabel selectFontLabel = new JLabel("font:");
        /*create comboBox fontList */
        JComboBox <String> fontList = new JComboBox<>(fontStrings); //creates fontList combobox

        /*ActionListener for fontList */
        fontList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String newFontTemp = (String) fontList.getSelectedItem();  //sets newText to whatever is selected
                //thisReview.setNewFont(newFontTemp);    //setter for newFont
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        });

         
        
        /* change font size */
        JLabel selectFontSizeLabel= new JLabel("font size:");

        /* create combobox of font size list */
        Integer []fontSizeList = new Integer[50]; 
        for(int i = 0; i<50; i++){
            fontSizeList[i] = i+1; 
        }

        JComboBox <Integer> fontSize = new JComboBox<>(fontSizeList);

        /*Action listener for font size list */
        fontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newFontSizeTemp = (Integer)fontSize.getSelectedItem(); 
                //thisReview.setNewFontSize(newFontSizeTemp); //set newFontSize for review object
            }
        });

        /*add set changes button - disposes frame when clicked*/
        JButton setChanges = new JButton("ok"); 
        setChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DSFrame.dispose(); 
            }
        });
        
        //add items to jpanel
        JPanel displaySettingsPanel = new JPanel(); //create panel
        displaySettingsPanel.add(selectFontLabel);  //add select font label
        displaySettingsPanel.add(fontList); 
        displaySettingsPanel.add(selectFontSizeLabel);  //add select font size label
        displaySettingsPanel.add(fontSize); //add select font size jcombobox
        displaySettingsPanel.add(setChanges);   //add selectChanges button 


        DSFrame.add(displaySettingsPanel);  //add comboBox fontLIst to frame
        DSFrame.setSize(400, 400);   // format frame
        DSFrame.setVisible(true);   //set visible    
    }


    /**
     * gets all fonts in system and puts them into fontStrings list
     */
    public static void createFontStringArr(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                
        Font []getFonts = ge.getAllFonts();    //returns Font array of all fonts available in graphical interface
        fontStrings = new String[getFonts.length]; //creates String array for font names to go into 

        //gets names of all fonts and puts into fontStrings array
        for(int i = 0; i<getFonts.length; i++){ //prints all fonts to output
            fontStrings[i] = getFonts[i].getName(); 
        }
    }



    

 //    public static void main(String []args){
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplaySettingsMenu();
            }
        }); */

 //       DisplaySettingsMenu try1 = new DisplaySettingsMenu("Arial", 12); 
 //       try1.start(); 
 //   }
}









/*********NOTES*************/
/*  look: 
 *  font: whateverFont  //if click, open a scroll menu, pickFont: if click, font = thisFont
 *                          //maybe display those fonts in the fonts theyre in
 *  size: whateverSize  //textbox: can change size, reject non numbers? prog might do that automically... reset if nonnumbers detected
 *  ...color maybe
 */

/*CURRENTLY IN ACTION LISTENER BUTTON IN REVIEW */
    /* 
        Font getNewFont = new Font("Roboto Black Italic", Font.BOLD,20);    //sets users font choice
        reviewText.setFont(getNewFont); //changes font to getNewFont
    */


