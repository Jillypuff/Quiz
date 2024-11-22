package server.response;

import java.io.Serializable;

public class ResultResponse extends Response implements Serializable {

    private int playerResult;
    private int opponentResult;

    public ResultResponse(ResponseType responseType, int playerResult, int opponentResult) {
        super(responseType);
        this.playerResult = playerResult;
        this.opponentResult = opponentResult;
    }

    public int getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(int playerResult) {
        this.playerResult = playerResult;
    }

    public int getOpponentResult() {
        return opponentResult;
    }

    public void setOpponentResult(int opponentResult) {
        this.opponentResult = opponentResult;
    }
}
