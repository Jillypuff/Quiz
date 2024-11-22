package client.request;

public class RoundFinishedRequest extends Request {

    int score;

    public RoundFinishedRequest(RequestType requestType, String username, int score) {
        super(requestType, username);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
