package Modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionPackage implements Serializable {

    private final List<Question> questions;
    private final int amountOfQuestions;
    private int questionNumber = -1;

    public QuestionPackage(List<Question> questions, int amountOfQuestions) {
        this.questions = questions;
        this.amountOfQuestions = amountOfQuestions;
    }

    public List<String> getQuestionAndAlternatives(){
        List<String> questionAndAlternatives = new ArrayList<>();
        questionAndAlternatives.add(questions.get(questionNumber).getQuestion());
        questionAndAlternatives.addAll(questions.get(questionNumber).getAlternatives());
        return questionAndAlternatives;
    }

    public void addOneToQuestionNumber(){
        questionNumber++;
    }

    public boolean checkUserAnswer(String answer){
        return questions.get(questionNumber).checkAnswer(answer);
    }

    public int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public Question getCurrentQuestion() {
        return questions.get(questionNumber);
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void resetPackage(){
        questionNumber = 0;
    }
}
