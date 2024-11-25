package server.response;

public class ResultPackageResponse extends Response {

    int yourScore;
    int opponentsScore;
    String opponentName;

    public ResultPackageResponse(ResponseType responseType, int yourScore, int opponentsScore, String opponentName) {
        super(responseType);
        this.yourScore = yourScore;
        this.opponentsScore = opponentsScore;
        this.opponentName = opponentName;
    }

    public int getYourScore() {
        return yourScore;
    }

    public int getOpponentsScore() {
        return opponentsScore;
    }

    public String getOpponentName() {
        return opponentName;
    }
}
