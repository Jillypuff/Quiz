package server.response;

import java.io.Serializable;

public class Response implements Serializable {

    ResponseType response;

    public Response(ResponseType response){
        this.response = response;
    }

    public ResponseType getResponseType(){
        return this.response;
    }


}
