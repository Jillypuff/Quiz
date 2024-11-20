package server;

public class GameInstance  {

    private ConnectedClient playerOne;
    private ConnectedClient playerTwo;
    private CurrentGame game;

    public GameInstance(ConnectedClient player1, ConnectedClient player2) {
        this.playerOne = player1;
        this.playerTwo = player2;
        game = new CurrentGame(playerOne.getUsername(), playerTwo.getUsername());
    }

    public ConnectedClient getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(ConnectedClient playerOne) {
        this.playerOne = playerOne;
    }

    public ConnectedClient getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(ConnectedClient playerTwo) {
        this.playerTwo = playerTwo;
    }

    public CurrentGame getGame() {
        return game;
    }

    public void setGame(CurrentGame game) {
        this.game = game;
    }
}
