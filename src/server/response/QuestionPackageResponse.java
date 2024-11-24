package server.response;

import gamelogic.Question;

import java.util.List;

public class QuestionPackageResponse extends GamePackageResponse {

    List<Question> questions;

    public QuestionPackageResponse(ResponseType responseType, String currentPlayer, String opponentPlayer, List<Question> questions) {
        super(responseType, currentPlayer, opponentPlayer);
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
