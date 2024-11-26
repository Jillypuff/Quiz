package Modules;

import java.io.Serializable;

public class Request implements Serializable {

    private final RequestType request;
    public String username;
    Category chosenCategory;
    public int answer = -1;
    public boolean playerIdentifier=false;

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

    public Request(RequestType request, String username, Category chosenCategory){
        this.request = request;
        this.username = username;
        this.chosenCategory = chosenCategory;
    }

    public Request(RequestType request, String username, int answer, Boolean playerIdentifier) {
        this.request = request;
        this.username = username;
        this.answer = answer;
        this.playerIdentifier = playerIdentifier;
    }

    public RequestType getType() {
        return request;
    }

    public int getAnswer(){
        return answer;
    }
    public Category getChosenCategory(){
        return chosenCategory;
    }
}
