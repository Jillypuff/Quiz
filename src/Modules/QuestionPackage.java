package Modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionPackage implements Serializable {

    List<Question> questions;
    int amountOfQuestions;
    // -1 because checkUser first increments, then it's 0 indexed
    int currentQuestion = -1;

    public QuestionPackage(List<Question> questions, int amountOfQuestions) {
        this.questions = questions;
        this.amountOfQuestions = amountOfQuestions;
    }

    public List<String> getQuestionAndAlternatives(){
        List<String> questionAndAlternatives = new ArrayList<>();
        questionAndAlternatives.add(questions.get(currentQuestion).getQuestion());
        questionAndAlternatives.addAll(questions.get(currentQuestion).getAlternatives());
        return questionAndAlternatives;
    }

    public boolean checkUserAnswer(int answer){
        currentQuestion++;
        return questions.get(currentQuestion).checkAnswer(answer);
    }

    public int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }
}
