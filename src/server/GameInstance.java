package server;

import gamelogic.CurrentGame;

public class GameInstance  {

    ConnectedClient playerOne;
    ConnectedClient playerTwo;
    ConnectedClient turnHolder;
    CurrentGame game;

    public GameInstance(ConnectedClient clientOne, ConnectedClient clientTwo) {
        this.playerOne = clientOne;
        this.playerTwo = clientTwo;
        turnHolder = playerOne;
        game = new CurrentGame(playerOne.getUsername(), playerTwo.getUsername());
    }

    public void changeTurnHolder(){
        if (turnHolder.equals(playerOne)){
            turnHolder = playerTwo;
        } else {
            turnHolder = playerOne;
        }
    }

    public void awardPointsToTurnHolder(int points) {
        turnHolder.increaseScore(points);
    }

    public void endGameAndDetermineWinner() {
        if (playerOne.getScore() > playerTwo.getScore()) {
            playerOne.incrementMatchesWon();
        } else if (playerTwo.getScore() > playerOne.getScore()) {
            playerTwo.incrementMatchesWon();
        }
        playerOne.resetScore();
        playerTwo.resetScore();
    }

    public CurrentGame getCurrentGame(){
        return game;
    }
}
