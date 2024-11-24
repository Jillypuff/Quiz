package server.response;

public class ResultPackageResponse extends Response {

    int yourScore;
    int opponentsScore;

    public ResultPackageResponse(ResponseType responseType, int yourScore, int opponentsScore) {
        super(responseType);
        this.yourScore = yourScore;
        this.opponentsScore = opponentsScore;
    }

    public int getYourScore() {
        return yourScore;
    }

    public int getOpponentsScore() {
        return opponentsScore;
    }
}
