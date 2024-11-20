package server;

public class GameInstance  {

    ConnectedClient playerOne;
    ConnectedClient playerTwo;
    public CurrentGame game;

    public GameInstance(ConnectedClient player1, ConnectedClient player2) {
        this.playerOne = player1;
        this.playerTwo = player2;
        game = new CurrentGame(playerOne.username, playerTwo.username);
    }
}
