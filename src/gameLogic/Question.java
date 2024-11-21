package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    String question;
    String answer;
    List<String> alternatives = new ArrayList<>();

    public Question(String question, String answer, List<String> alternatives) {
        this.question = question;
        this.answer = answer;
        this.alternatives = alternatives;
    }

    public boolean checkAnswer(String answer){
        return this.answer.equalsIgnoreCase(answer);
    }

    public boolean checkAnswer(int answer){
        return checkAnswer(alternatives.get(answer));
    }
}
