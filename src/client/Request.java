package client;

import java.io.Serializable;

public class Request implements Serializable {

    private RequestType request;
    String username;
    private int answer = -1;

    public Request(RequestType request, String username, int answer) {
        this.request = request;
        this.username = username;
        this.answer = answer;
    }

    public RequestType getType() {
        return request;
    }

    public int getAnswer(){
        return answer;
    }
}
