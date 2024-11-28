package Modules;

import java.io.Serializable;

public class Request implements Serializable {

    private final RequestType request;
    private String username;
    private Category chosenCategory;
    private int roundScore = -1;

    public Request(RequestType request){
        this(request, "", -1);
    }

    public Request(RequestType request, String username){
        this(request, username, -1);
    }

    public Request(RequestType request, String username, int roundScore) {
        this.request = request;
        this.username = username;
        this.roundScore = roundScore;
    }

    public Request(RequestType request, String username, Category chosenCategory){
        this.request = request;
        this.username = username;
        this.chosenCategory = chosenCategory;
    }

    public RequestType getType() {
        return request;
    }

    public int getRoundScore(){
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public Category getChosenCategory(){
        return chosenCategory;
    }

    public void setChosenCategory(Category chosenCategory) {
        this.chosenCategory = chosenCategory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
