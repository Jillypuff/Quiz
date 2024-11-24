package server.response;

public class GamePackageResponse extends Response {

    private final String currentPlayer;
    private final String opponentPlayer;

    public GamePackageResponse(ResponseType responseType, String currentPlayer, String opponentPlayer) {
        super(responseType);
        this.currentPlayer = currentPlayer;
        this.opponentPlayer = opponentPlayer;
    }

}
