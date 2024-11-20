package server;

import java.io.Serializable;

public class Response implements Serializable {

    ServerResponse response;
    public CurrentGame game;

    public Response(ServerResponse response) {
        this.response = response;
    }

    public Response(ServerResponse response, CurrentGame game){
        this.response = response;
        this.game = game;
    }

    public ServerResponse getType(){
        return this.response;
    }

}
