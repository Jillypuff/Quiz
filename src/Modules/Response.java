package Modules;

import java.io.Serializable;

public class Response implements Serializable {

    ReponseType response;
    QuestionInPanel[][] gameBoard;


    public Response(ReponseType response) {
        this.response = response;
    }

    public Response(ReponseType response, QuestionInPanel[][] gameBoard){
        this.response = response;
        this.gameBoard = gameBoard;

    }

    public ReponseType getType(){
        return this.response;
    }

    public QuestionInPanel[][] getGameBoard(){
        return gameBoard;
    }
}
