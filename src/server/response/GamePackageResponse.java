package server.response;

public class GamePackageResponse extends Response {

    private final String currentPlayerUsername;
    private final String opponentPlayerUsername;

    public GamePackageResponse(ResponseType responseType, String currentPlayerUsername, String opponentPlayerUsername) {
        super(responseType);
        this.currentPlayerUsername = currentPlayerUsername;
        this.opponentPlayerUsername = opponentPlayerUsername;
    }

    public String getCurrentPlayerUsername() {
        return currentPlayerUsername;
    }

    public String getOpponentPlayerUsername() {
        return opponentPlayerUsername;
    }

}
