package client.request;

public class RoundFinishedRequest extends Request {

    private int myScore;

    public RoundFinishedRequest(RequestType requestType, String username, int myScore) {
        super(requestType, username);
        this.myScore = myScore;
    }

    public int getMyScore() {
        return myScore;
    }
}
