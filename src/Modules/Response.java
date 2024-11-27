package Modules;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    ResponseType response;
    int amountOfRounds;
    boolean isActivePlayer;
    QuestionPackage questionPackage;
    int yourScoreLastRound;
    int yourTotalScore;
    int opponentScoreLastRound;
    int opponentTotalScore;
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

    public Response(ResponseType response, int yourScoreLastRound, int yourTotalScore, int opponentScoreLastRound, int opponentTotalScore){
        this.response = response;
        this.yourScoreLastRound = yourScoreLastRound;
        this.yourTotalScore = yourTotalScore;
        this.opponentScoreLastRound = opponentScoreLastRound;
        this.opponentTotalScore = opponentTotalScore;
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

    public int getYourScoreLastRound() {return yourScoreLastRound;}
    public int getYourTotalScore() {return yourTotalScore;}

    public int getOpponentScoreLastRound() {return opponentScoreLastRound;}
    public int getOpponentTotalScore() {return opponentTotalScore;}
}
