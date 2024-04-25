/**
 * Flashcard class defines the parameters and functions of a flashcard
 * Flashcards consist of a question and answer.
 */
public class FlashCard {
    public String question = "";
    public String answer = "";

    /**
     * Constructor defining attributes
     */
    public FlashCard() {
        question = this.question;
        answer = this.answer;
    }

    /**
     * getQuestion returns the question of the flashcard
     * @return question
     */
    public String getQuestion() { return question;}

    /**
     * getAnswer returns the answer of the flashcard
     * @return answer
     */
    public String getAnswer() { return answer;}
}