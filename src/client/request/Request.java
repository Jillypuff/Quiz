package client.request;

import java.io.Serializable;

public class Request implements Serializable {

    private RequestType requestType;
    private String username;

    public Request(RequestType requestType, String username){
        this.requestType = requestType;
        this.username = username;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
