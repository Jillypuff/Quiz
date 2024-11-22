package client.request;

import gamelogic.Category;

import java.io.Serializable;

public class Request implements Serializable {

    RequestType requestType;
    public String username;

    public Request(RequestType requestType, String username){
        this.requestType = requestType;
        this.username = username;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
