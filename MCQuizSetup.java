/* MCQuizSetup object has a question, array of answer options and user answer associated with it 
 * Has all info pertaining to each question
*/


public class MCQuizSetup{
    private FlashCard quizCard; 
    String []answerOptions; //array of strings of answer options  for question 
    String userAnswer; 
    
    public MCQuizSetup(FlashCard quizCard, String []answerOptions, String userAnswer){
        this.quizCard = quizCard; 
        this.answerOptions = answerOptions; 
        this.userAnswer = userAnswer; 
    }
    public MCQuizSetup(){}

    /* setters */
    public void setQuizCard(FlashCard qc){
        this.quizCard = qc; 
    }

    public void setAnswerOptionsArr(String []Ao){
        this.answerOptions = Ao; 
    }


    public void setUserAnswer(String userAnswer){
        this.userAnswer = userAnswer; 
    }



    /* getters */
    public FlashCard getQuizCard(){
        return quizCard; 
    }

    public String []getAnswerOptions(){
        return answerOptions; 
    }

    //get answer option i from answerOptions arr 
    public String getAnswerOptionsAtIndex(int i){
        return answerOptions[i];
    }

    public String getUserAnswer(){
        return userAnswer; 
    }

    public String getCorrectAnswer(){
        return quizCard.getAnswer();
    }
    
}
