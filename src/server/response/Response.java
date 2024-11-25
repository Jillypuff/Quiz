package server.response;

import java.io.Serializable;

public class Response implements Serializable {

    ResponseType response;
    boolean myTurn;

    public Response(ResponseType response){
        this.response = response;
    }

    public Response(ResponseType response, boolean myTurn){
        this.response = response;
        this.myTurn = myTurn;
    }

    public ResponseType getResponseType(){
        return this.response;
    }

    public boolean isMyTurn(){
        return this.myTurn;
    }
}
