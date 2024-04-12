/*object to create, display and get user changes from a display settings menu */
import javax.swing.*;
import javax.swing.border.Border;

import static java.awt.Color.black;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DisplaySettingsMenu{
    private JFrame DSFrame; 
    private JPopupMenu DSMenu; 
    private String newText; //should change to new font

    private static String []fontStrings; //!!!!!!!!

    DisplaySettingsMenu(String fontType){
        this.newText = fontType; //initializes newText to whatever font is initially in Review
    }

    public void start(){
        // create frame
        DSFrame = new JFrame("Display Settings");
        DSFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createFontStringArr(); 

        /*create comboBox fontList */
        JComboBox <String> fontList = new JComboBox<>(fontStrings); //creates fontList combobox

        /*ActionListener for fontList */
        fontList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                newText = (String) fontList.getSelectedItem();  //sets newText to whatever is selected
                System.out.print(newText);
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        });

        JLabel selectFontLabel = new JLabel("font:"); 

        JPanel displaySettingsPanel = new JPanel(); //create panel
        displaySettingsPanel.add(selectFontLabel);  //add select font label
        displaySettingsPanel.add(fontList); 
        


        /* change font size */

        DSFrame.add(displaySettingsPanel);  //add comboBox fontLIst to frame
        DSFrame.setSize(600,600);   // format frame
        DSFrame.setVisible(true);   //set visible    
    }
    
    
    public String getNewFont(){
        return newText; 
    }


    /* gets all fonts in system and puts them into fontStrings list */
    public static void createFontStringArr(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                
        Font []getFonts = ge.getAllFonts();    //returns Font array of all fonts available in graphical interface
        fontStrings = new String[getFonts.length]; //creates String array for font names to go into 

        //gets names of all fonts and puts into fontStrings array
        for(int i = 0; i<getFonts.length; i++){ //prints all fonts to output
            //System.out.print(getFonts[i].getName() + "\n"); //prints all fonts to screen DELETE (eventually)
            fontStrings[i] = getFonts[i].getName(); 
        }
    }
}


    

/*     public static void main(String []args){
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DisplaySettingsMenu();
            }
        }); */

    //    DisplaySettingsMenu try1 = new DisplaySettingsMenu("Arial"); 
    //    try1.start(); 
    //}









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


