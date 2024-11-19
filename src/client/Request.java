package client;

import java.io.Serializable;

public class Request implements Serializable {

    private ClientRequest request;
    String username;
    private int answer = -1;

    public Request(ClientRequest request, String username, int answer) {
        this.request = request;
        this.username = username;
        this.answer = answer;
    }

    public ClientRequest getType() {
        return request;
    }

    public int getAnswer(){
        return answer;
    }
}
