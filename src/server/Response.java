package server;

import java.io.Serializable;

public class Response implements Serializable {

    ResponseType response;
    public CurrentGame game;

    public Response(ResponseType response) {
        this.response = response;
    }

    public Response(ResponseType response, CurrentGame game){
        this.response = response;
        this.game = game;
    }

    public ResponseType getType(){
        return this.response;
    }

}
