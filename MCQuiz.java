import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

/**
 * Multiple Choice quiz gets a random set of answers from the questions in the deck, one of which being the correct
 * answer for the current Flashcard, and creates a multiple choice quiz for the user.
 */
public class MCQuiz extends JFrame{
    private Deck deck; 
    private int deckLength;
    private MCQuizSetup []QuizQuestions;
    private static ArrayList <Integer> UsedQuizQIndex = new ArrayList<Integer>();   //keeps track of index nums that have been used for quizQuestions
    private String selectedUserAnswer; //sets selected answer for each quiz question
    private int currentQuestion;    //index of current question 
    private int score;
    JLabel QuizQLabel; 
    JButton option1Button;
    JButton option2Button;
    JButton option3Button;
    JButton option4Button;

    /**
     * Constructor is passed a deck and initializes deckLength and score
     * It calls runMCQuiz function
     * @param deck Deck
     */
    public MCQuiz(Deck deck){
        this.deck = deck; 
        this.deckLength = deck.size();
        this.score = 0;
        runMCQuiz();
    }

    /**
     * runMCQuiz creates the UI, buttons, and actionlisteners for the mcquiz
     * It creates a panel for the card question and displays four buttons for the answers.
     * The user selects an answer and tells the user it is correct or incorrect, then moves to the next
     */
    public void runMCQuiz(){

        makeQuizQArray();   //makes array of all quiz qs, answer options etc 
        setTitle("Multiple Choice Quiz");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));

        // Set background color
        getContentPane().setBackground(new Color(225, 252, 255));

        JPanel questionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        questionPanel.setBackground(new Color(225, 252, 255));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

        optionsPanel.setBackground(new Color(225, 252, 255));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        buttonPanel.setBackground(new Color(225, 252, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        currentQuestion = 1; //initialises current question number
        /* initialize, then change to question # -- for each q  */
        QuizQLabel = new JLabel("Question " + currentQuestion + ": " + QuizQuestions[currentQuestion].getQuizCard().getQuestion());
        QuizQLabel.setHorizontalAlignment(SwingConstants.CENTER);
        QuizQLabel.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 24));
        QuizQLabel.setForeground(new Color(75, 90, 152));
        questionPanel.add(QuizQLabel, BorderLayout.CENTER);

        //initialise answer options buttons
        option1Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(0));
        option2Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(1));
        option3Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(2));
        option4Button = new JButton(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(3));

        option1Button.setBackground(new Color(225, 252, 255)); // Set background color
        option1Button.setForeground(new Color(75, 90, 152)); // Set text color
        option1Button.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font

        option2Button.setBackground(new Color(225, 252, 255)); // Set background color
        option2Button.setForeground(new Color(75, 90, 152)); // Set text color
        option2Button.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font

        option3Button.setBackground(new Color(225, 252, 255)); // Set background color
        option3Button.setForeground(new Color(75, 90, 152)); // Set text color
        option3Button.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font

        option4Button.setBackground(new Color(225, 252, 255)); // Set background color
        option4Button.setForeground(new Color(75, 90, 152)); // Set text color
        option4Button.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font

        QuizQLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        option1Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        option2Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        option3Button.setAlignmentX(Component.CENTER_ALIGNMENT);
        option4Button.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        JButton submitAnswerButton = new JButton("Submit");

        submitAnswerButton.setBackground(new Color(225, 252, 255)); // Set background color
        submitAnswerButton.setForeground(new Color(75, 90, 152)); // Set text color
        submitAnswerButton.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font

        submitAnswerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitAnswerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                QuizQuestions[currentQuestion].setUserAnswer(selectedUserAnswer);
                //if answer is correct, add to score
                if(checkAnswer(QuizQuestions[currentQuestion].getUserAnswer(), QuizQuestions[currentQuestion].getCorrectAnswer())){
                    score++; //add to score
                }
                //END OF QUIZ
                if ((currentQuestion + 1 == deckLength) || (currentQuestion + 1 == 10)) {    
                    dispose();
                    JFrame resultsPageFrame = new JFrame("Results");
                    resultsPageFrame.getContentPane().setBackground(new Color(225, 252, 255));
                    resultsPageFrame.setSize(100, 100);
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel resultsPanel = new JPanel();
                    resultsPanel.setBackground(new Color(225, 252, 255));

                    JLabel scoreLabel = new JLabel("score: " + score);
                    scoreLabel.setBackground(new Color(225, 252, 255)); // Set background color
                    scoreLabel.setForeground(new Color(75, 90, 152)); // Set text color
                    scoreLabel.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font
                    
                    JButton exitResultsPage = new JButton("Exit");

                    exitResultsPage.setBackground(new Color(225, 252, 255)); // Set background color
                    exitResultsPage.setForeground(new Color(75, 90, 152)); // Set text color
                    exitResultsPage.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font
                    exitResultsPage.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            resultsPageFrame.dispose();
                            new DeckDisplay(deck);

                        }
                    }); 

                    resultsPanel.add(scoreLabel); 
                    resultsPanel.add(exitResultsPage);

                    resultsPageFrame.add(resultsPanel);
                    resultsPageFrame.setSize(200,200);
                    resultsPageFrame.setBackground(new Color(225, 252, 255));
                    resultsPageFrame.setLocationRelativeTo(null);
                    resultsPageFrame.setVisible(true);
                    return;
                }

                currentQuestion++;
                setNextQuizQuestion();  //setup next question
            }
        });

        JButton exitQuiz = new JButton("Exit");

        exitQuiz.setBackground(new Color(225, 252, 255)); // Set background color
        exitQuiz.setForeground(new Color(75, 90, 152)); // Set text color
        exitQuiz.setFont(new Font("Avenir Next Condensed", Font.PLAIN, 28)); // Set font
        exitQuiz.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitQuiz.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DeckDisplay(deck);
            }
        });

        //add question and option buttons to answersPanel 
        questionPanel.add(QuizQLabel);
        optionsPanel.add(option1Button);
        optionsPanel.add(option2Button);
        optionsPanel.add(option3Button);
        optionsPanel.add(option4Button);
        buttonPanel.add(submitAnswerButton);
        buttonPanel.add(exitQuiz);

        //Add to frame
        getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        getContentPane().add(BorderLayout.CENTER, optionsPanel);
        getContentPane().add(BorderLayout.NORTH,questionPanel);

        // format and set visible
        setSize(450,450);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * creates QuizQuestions array. sets quiz questions and answer options for each quiz question.
     */
    public void makeQuizQArray(){
        QuizQuestions = new MCQuizSetup[deckLength]; 
        Random random = new Random();
        UsedQuizQIndex.clear();
        
        int ranNum = random.nextInt(deckLength);    //set first ranNum 
        UsedQuizQIndex.add(ranNum);  //add to usedQuizQIndex ArrayList
        int usedCount = 1; 

        /* loop to create Question and answer options for every question in quiz */
        int i = 0; 
        while(i<10 && i<deckLength){
            QuizQuestions[i] = new MCQuizSetup();
            QuizQuestions[i].setQuizCard(deck.get(ranNum));   //picks quiz questions at random from any card in deck 
            chooseAnswerOptions(i);     //creates answer options for quizQuestions[i] 

            //check if index has been used already, changes ranNum if so 
            while(!canUseQuizQIndex(ranNum) && usedCount < deckLength){   //stop when that array is all full
                ranNum = random.nextInt(deckLength);    //get new random number 
            }

            //TESTING
            System.out.println("Quiz Question " + i + " is: "+ QuizQuestions[i].getQuizCard().getQuestion());  
            System.out.println("correct answer " + i + " is: " + QuizQuestions[i].getQuizCard().getAnswer());
            for(int j= 0; j<3; j++){
                System.out.println("answer option " + j + ": " + QuizQuestions[i].getAnswerOptionsAtIndex(j)); 
            }
            System.out.println("\n"); 

            UsedQuizQIndex.add(ranNum); //add ranNum to usedQuizQIndex ArrayList
            usedCount++;  
            i++; 
        }
    }

    /**
     * choose answer options for each quiz questions
     * @param questionNum question number
     */
    public void chooseAnswerOptions(int questionNum){ 
        Random random = new Random();
        int ranNum; 
        String []answerOptions2 = new String[4]; 

        //gets 3 random answers 
        for(int i = 0; i<3; i++){ 
            ranNum = random.nextInt(deckLength);    //need a way to check for repetitions/...
            if (Arrays.asList(answerOptions2).contains(deck.get(ranNum).getAnswer())) {
                i--;
                continue;
            }
            if (deck.get(ranNum).getAnswer() == QuizQuestions[questionNum].getCorrectAnswer()) {
                i--;
                continue;
            }
            answerOptions2[i] = deck.get(ranNum).getAnswer();
        }

        answerOptions2[3] = QuizQuestions[questionNum].getCorrectAnswer(); //adds correct answer to list
        QuizQuestions[questionNum].setAnswerOptionsArr(answerOptions2);
    }


    /**
     * check if num is index in UsedQuizQIndex
     * @param num number
     * @return boolean
     */
    public static boolean canUseQuizQIndex(int num){
        int stop = UsedQuizQIndex.size();   

        //checks if num is in UsedQuizQIndex array, returns false 
        for(int i = 0; i<stop; i++){
            if(num == UsedQuizQIndex.get(i)){
                return false;   //index has already been used 
                //get new random num 
            }
        }
        return true; 
    }

    /**
     * checks if answer is correct or incorrect
     * @param userAnswerTemp user answer
     * @param correctAnswerTemp correct answer
     * @return boolean
     */
    public static boolean checkAnswer(String userAnswerTemp, String correctAnswerTemp){
        //if userAnswer == correctanswer
        if(userAnswerTemp.equals(correctAnswerTemp)){
            return true; 
        }
        return false; 
    }

    /**
     * sets the next quiz question
     */
    public void setNextQuizQuestion(){
        QuizQLabel.setText("question " + currentQuestion + ": " + QuizQuestions[currentQuestion].getQuizCard().getQuestion());

        QuizQuestions[currentQuestion].shuffle();

        option1Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(0)); 
        option2Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(1)); 
        option3Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(2)); 
        option4Button.setText(QuizQuestions[currentQuestion].getAnswerOptionsAtIndex(3)); 
    }

}