import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MCQuiz {
    private Deck deck; 
    private int deckLength; 


    private MCQuizSetup []QuizQuestions;
    private static ArrayList <Integer> UsedQuizQIndex = new ArrayList<Integer>();   //keeps track of index nums that have been used for quizQuestions
    private String selectedUserAnswer; //sets selected answer for each quiz question
    private int currentQuestion;    //index of current question 
    private int score; 

    //JFrame
    private JFrame MCQuizFrame; 
    JLabel QuizQLabel; 
    JButton option1Button;
    JButton option2Button;
    JButton option3Button;
    JButton option4Button;

    public MCQuiz(Deck deck){
        this.deck = deck; 
        this.deckLength = deck.size();
        this.score = 0;   
    }

    public void runMCQUiz(){

        makeQuizQArray();   //makes array of all quiz qs, answer options etc 

        // create frame
        MCQuizFrame = new JFrame("Multiple Choice Quiz Frame");
        MCQuizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel answersPanel = new JPanel(); 

        currentQuestion = 0; //initialises current question number 
        /* initialize, then change to question # -- for each q  */
        QuizQLabel = new JLabel("question " + currentQuestion + ": " + QuizQuestions[currentQuestion].getQuizCard().getQuestion()); 

        //initialise answer options buttons
        option1Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(0));
        option2Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(1));
        option3Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(2));
        option4Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(3)); 


        //option button action listeners 
        option1Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserAnswer = QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(0);
                
            }
        });   

        option2Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserAnswer = QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(1);
            }
        });
        
        option3Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserAnswer = QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(2);
            }
        }); 

        option4Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserAnswer = QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(3);
            }
        }); 



        JButton submitAnswerButton = new JButton("submit"); 
        submitAnswerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizQuestions[currentQuestion].setUserAnswer(selectedUserAnswer); 

                //END OF QUIZ
                if ((currentQuestion + 1 == deckLength) || (currentQuestion + 1 == 10)) {    
                    //add submit test button, popup score page, make into its own method/class
                    JFrame resultsPageFrame = new JFrame("Results");
                    MCQuizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    JPanel resultsPanel = new JPanel(); 

                    JLabel scoreLabel = new JLabel("score: " + score); 
                    
                    JButton exitResultsPage = new JButton("exit");
                    exitResultsPage.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            resultsPageFrame.dispose(); 
                        }
                    }); 

                    resultsPanel.add(scoreLabel); 
                    resultsPanel.add(exitResultsPage); 

                    resultsPageFrame.add(resultsPanel); 

                    resultsPageFrame.setSize(500,500);
                    resultsPageFrame.setVisible(true);

                    return;
                }            

                //if answer is correct, add to score 
                if(checkAnswer(QuizQuestions[currentQuestion].getUserAnswer(), QuizQuestions[currentQuestion].getCorrectAnswer())){
                    score++; //add to score
                    //something like 
                        //QuizQuestions[currentQuestion].isCorrect(); //sets if question is correct or not 
                }

                currentQuestion++;
                setNextQuizQuestion();  //setup next question
            }
        });

        JButton exitQuiz = new JButton("exit"); 
        exitQuiz.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                MCQuizFrame.dispose(); 
            }
        }); 

        

        //add question and option buttons to answersPanel 
        answersPanel.add(QuizQLabel); 
        answersPanel.add(option1Button);
        answersPanel.add(option2Button);
        answersPanel.add(option3Button);
        answersPanel.add(option4Button);
        answersPanel.add(submitAnswerButton);
        answersPanel.add(exitQuiz);
        
        MCQuizFrame.add(answersPanel);

        // format and set visible
        MCQuizFrame.getContentPane().add(answersPanel);
        MCQuizFrame.setSize(1000,1000);
        MCQuizFrame.setVisible(true);

    }


    /*creates QuizQuestions array. sets quiz questions and answer options for each quiz question. */ 
    public void makeQuizQArray(){
        //Scanner getInput = new Scanner(System.in); //TESTING
        //int kb; 

        QuizQuestions = new MCQuizSetup[deckLength]; 
        Random random = new Random(); 
        
        //initial random number
        int ranNum = random.nextInt(deckLength);    //set first ranNum 
        UsedQuizQIndex.add(ranNum);  //add to usedQuizQIndex ArrayList
        int usedCount = 1; 

        /* loop to create Question and answer options for every question in quiz */
        int i = 0; 
        while(i<10 && i<deckLength){
            QuizQuestions[i] = new MCQuizSetup();
            QuizQuestions[i].setQuizCard(deck.get(ranNum));   //picks quiz questions at random from any card in deck 
            chooseAnswerOptions(i, ranNum);     //creates answer options for quizQuestions[i] 

            //TESTING
            System.out.println("Quiz Question " + i + " is: "+ QuizQuestions[i].getQuizCard().getQuestion());  
            for(int j= 0; j<4; j++){
                System.out.println("answer option " + j + ": " + QuizQuestions[i].getAnswerOptionsAtIndex(j)); 
            }
            System.out.println("\n"); 

            //check if index has been used already, changes ranNum if so 
            ranNum = random.nextInt(deckLength);    //get new random number 
            while(UsedQuizQIndex.contains(ranNum) && usedCount < deckLength && usedCount < 10){   //stop when that array is all full
                ranNum = random.nextInt(deckLength);    //get new random number 
                
            /*    System.out.println("press 6 to stop"); //TESTING
                kb = getInput.nextInt(); 
                if(kb == 6){
                    return; 
                }
            */
            }


            UsedQuizQIndex.add(ranNum); //add ranNum to usedQuizQIndex ArrayList
            usedCount++;  
            i++; 
        }
    }


    public void chooseAnswerOptions(int questionNum, int correctAnswerIndex){
        Random random = new Random(); 
        int ranNum = 0; 

        String []answerOptions = new String[4]; 
        ArrayList <Integer> usedAnswerOptionsIndex = new ArrayList<Integer>();   //keeps track of index nums that have been used for answerOption
        
        //int answersCreatedCount = 0;    //use if needed


        Scanner ns = new Scanner(System.in); 
        int in = 0; 


        boolean redo = true; 

        answerOptions[0] = deck.get(correctAnswerIndex).getAnswer();    //sets correct answer as first in deck
        usedAnswerOptionsIndex.add(correctAnswerIndex);
        

        for(int i = 0; i<3; i++){
            ranNum = random.nextInt(deckLength); 

            do{
                /*System.out.println("press 4 to stop \n"); 
                in = ns.nextInt();*/    

                if(usedAnswerOptionsIndex.contains(ranNum)){
                        redo = true; 
                        ranNum = random.nextInt(deckLength); 
                }
                else{
                    redo = false; 
                }
            }while(redo && usedAnswerOptionsIndex.size() < 4);


            answerOptions[i+1] = deck.get(ranNum).getAnswer();
            usedAnswerOptionsIndex.add(ranNum); 
        
        }

        QuizQuestions[questionNum].setAnswerOptionsArr(answerOptions); 
    }


    public static boolean checkAnswer(String userAnswerTemp, String correctAnswerTemp){
        //if userAnswer == correctanswer
        if(userAnswerTemp.equals(correctAnswerTemp)){
            return true; 
        }
        return false; 
    }


    public void setNextQuizQuestion(){
        QuizQLabel.setText("question " + currentQuestion + ": " + QuizQuestions[currentQuestion].getQuizCard().getQuestion()); 
        
        option1Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(0)); 
        option2Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(1)); 
        option3Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(2)); 
        option4Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(3)); 
    }




    public static void main(String []args){
        //TESTING 
        Deck deck1 = new Deck("test1");

        FlashCard t1 = new FlashCard();
        t1.question = "bonjour";
        t1.answer = "hello";
        
        FlashCard t2 = new FlashCard();
        t2.question = "au revoir";
        t2.answer = "goodbye";

        FlashCard t3 = new FlashCard();
        t3.question = "pourquoi";
        t3.answer = "why";

        FlashCard t4 = new FlashCard();
        t4.question = "poubelle";
        t4.answer = "trash";

        FlashCard t5 = new FlashCard();
        t5.question = "la hyene";
        t5.answer = "hyena";

        FlashCard t6 = new FlashCard();
        t6.question = "manger";
        t6.answer = "to eat";

        FlashCard t7 = new FlashCard();
        t7.question = "dormir";
        t7.answer = "to sleep";

        FlashCard t8 = new FlashCard();
        t8.question = "voir";
        t8.answer = "to see";

        FlashCard t9 = new FlashCard();
        t9.question = "comment dit-on";
        t9.answer = "how do you say";
        
        FlashCard t10 = new FlashCard();
        t10.question = "chocolat";
        t10.answer = "chocolate";
        
        FlashCard t11 = new FlashCard();
        t11.question = "the";
        t11.answer = "tea";


        deck1.add(t1);
        deck1.add(t2);
        deck1.add(t3); 
        deck1.add(t4); 
        deck1.add(t5); 
        deck1.add(t6); 
        deck1.add(t7); 
        deck1.add(t8); 
        deck1.add(t9); 
        deck1.add(t10); 
        deck1.add(t11); 

        MCQuiz MCQuiz1 = new MCQuiz(deck1); 
        //MCQuiz1.makeQuizQArray(); 
        MCQuiz1.runMCQUiz(); 
        


    }
}






    /*********NOTES***************/
    /* test -10 qs(for now) give(defintion) match with answer 
        - def: random, but cant be repeated!!
        - options: random from 4 cards 
        - keep score 
        - show score and questions you got wrong

    if user clicks answer -> next question and set is revealed
    */
    //create frame
    //quiz questions
        //max 10 questions(for now)
            //if under 10-> quiz = length of list
            //definitions can be random but NOT REPEATED
        //get definition
        //get correct question, and 3 random answers
        //display options on screen as button
        //either have submit button or submit automatically
    //keep track of score and which questions you got right
    //at the end reveal score and what questions you got right
