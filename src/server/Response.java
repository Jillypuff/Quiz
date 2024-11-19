package server;

import java.io.Serializable;

public class Response implements Serializable {

    ServerResponse response;

    public Response(ServerResponse response) {
        this.response = response;
    }

    public ServerResponse getType(){
        return this.response;
    }

}
