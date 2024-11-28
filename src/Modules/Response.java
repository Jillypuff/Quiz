package Modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response implements Serializable {

    ResponseType response;
    int amountOfRounds;
    boolean isActivePlayer;
    QuestionPackage questionPackage;
    int yourScore;
    int opponentScore;
    private List<Integer> yourScores = new ArrayList<>();
    private List<Integer> opponentScores = new ArrayList<>();
    List<Category> categories;

    public Response(ResponseType response) {
        this.response = response;
    }

    public Response(ResponseType response, int amountOfRounds, boolean isActivePlayer){
        this.response = response;
        this.amountOfRounds = amountOfRounds;
        this.isActivePlayer = isActivePlayer;
    }

    public Response (ResponseType response, List<Category> categories){
        this.response = response;
        this.categories = categories;
    }

    public Response(ResponseType response, QuestionPackage questionPackage){
        this.response = response;
        this.questionPackage = questionPackage;
    }

    public Response(ResponseType response, int yourScore, int opponentScore){
        this.response = response;
        this.yourScore = yourScore;
        this.opponentScore = opponentScore;
    }

    public Response(ResponseType response, List<Integer> yourScores, List<Integer> opponentScores){
        this.response = response;
        this.yourScores = yourScores;
        this.opponentScores = opponentScores;
    }

    public ResponseType getResponse() {
        return this.response;
    }

    public int getAmountOfRounds(){
        return this.amountOfRounds;
    }

    public boolean isActivePlayer() {
        return isActivePlayer;
    }

    public QuestionPackage getQuestionPackage() {
        return questionPackage;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getYourScore() {return yourScore;}

    public int getOpponentScore() {return opponentScore;}

    public List<Integer> getYourScores() {
        return yourScores;
    }

    public List<Integer> getOpponentScores() {
        return opponentScores;
    }
}
