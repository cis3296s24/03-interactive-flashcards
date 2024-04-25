/* MCQuizSetup object has a question, array of answer options and user answer associated with it 
 * Has all info pertaining to each question
*/

/**
 * Setup class for the multiple choice quiz
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MCQuizSetup{
    private FlashCard quizCard; 
    String []answerOptions; //array of strings of answer options  for question 
    String userAnswer;

    /**
     * Constructor takes quizCard, array of answerOptions, and userAnswer
     * @param quizCard
     * @param answerOptions
     * @param userAnswer
     */
    public MCQuizSetup(FlashCard quizCard, String []answerOptions, String userAnswer){
        this.quizCard = quizCard; 
        this.answerOptions = answerOptions; 
        this.userAnswer = userAnswer; 
    }
    public MCQuizSetup(){}

    /**
     * Set quiz card to qc provided
     * @param qc
     */
    public void setQuizCard(FlashCard qc){
        this.quizCard = qc; 
    }

    /**
     * setAnswer to Ao provided
     * @param Ao
     */
    public void setAnswerOptionsArr(String []Ao){
        this.answerOptions = Ao; 
    }

    /**
     * setUserAnswer to userAnswer provided
     * @param userAnswer
     */
    public void setUserAnswer(String userAnswer){
        this.userAnswer = userAnswer; 
    }


    /**
     * get quiz card
     * @return
     */
    public FlashCard getQuizCard(){
        return quizCard; 
    }

    /**
     * get list of answer options
     * @return
     */
    public String []getAnswerOptions(){
        return answerOptions; 
    }

    /**
     * getAnswerOptions at specified index
     * @param i
     * @return answerOption
     */
    public String getAnswerOptionsAtIndex(int i){
        return answerOptions[i];
    }

    /**
     * gets user answer
     * @returnu userAnswer
     */
    public String getUserAnswer(){
        return userAnswer; 
    }

    /**
     * gets correct answer
     * @return correctAnswer
     */
    public String getCorrectAnswer(){
        return quizCard.getAnswer();
    }

    public void shuffle() {
        List<String> temp = Arrays.asList(answerOptions);
        Collections.shuffle(temp);
        temp.toArray(answerOptions);
    }
    
}
