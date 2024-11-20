package client;

import java.io.Serializable;

public class Request implements Serializable {

    private final RequestType request;
    public String username;
    private final int answer;

    public Request(RequestType request){
        this(request, "", -1);
    }

    public Request(RequestType request, String username){
        this(request, username, -1);
    }

    public Request(RequestType request, int answer){
        this(request, "", answer);
    }

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
