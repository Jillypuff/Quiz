package Modules;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Question implements Serializable {

    private final String question;
    private final String answer;
    private final List<String> alternatives;

    public Question(String question, String answer, List<String> alternatives) {
        this.question = question;
        this.answer = answer;
        this.alternatives = alternatives;
    }

    public boolean checkAnswer(String answer){
        return this.answer.equalsIgnoreCase(answer);
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAlternatives() {
        List<String> temp = alternatives;
        Collections.shuffle(temp);
        return temp;
    }
}
