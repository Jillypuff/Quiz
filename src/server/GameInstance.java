package server;

import gamelogic.CurrentGame;

public class GameInstance  {

    ConnectedClient playerOne;
    ConnectedClient playerTwo;
    ConnectedClient turnHolder;
    CurrentGame game;
    boolean playerOneFinishedRound = false;
    boolean playerTwoFinishedRound = false;
    public int roundsFinished = 0;

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

    public boolean allRoundsFinished(){
        return roundsFinished == 3;
    }


    public void setPlayerFinishedRound(ConnectedClient player) {
        if (player.equals(playerOne)) {
            playerOneFinishedRound = true;
        } else if (player.equals(playerTwo)) {
            playerTwoFinishedRound = true;
        }
    }

    public boolean bothPlayersFinishedRound() {
        return playerOneFinishedRound && playerTwoFinishedRound;
    }

    public void resetRoundState() {
        playerOneFinishedRound = false;
        playerTwoFinishedRound = false;
    }

    public boolean isCurrentTurnHolder(ConnectedClient client) {
        return client.equals(turnHolder);
    }

    public CurrentGame getCurrentGame(){
        return game;
    }
}
