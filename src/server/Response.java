package server;

import gameLogic.CurrentGame;

import java.io.Serializable;

public class Response implements Serializable {

    ReponseType response;
    public CurrentGame game;

    public Response(ReponseType response) {
        this.response = response;
    }

    public Response(ReponseType response, CurrentGame game){
        this.response = response;
        this.game = game;
    }

    public ReponseType getType(){
        return this.response;
    }

}
