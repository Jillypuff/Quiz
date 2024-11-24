package gamelogic;

import server.ConnectedClient;

public class Player {

    ConnectedClient player;
    int roundScore;
    int totalScore;
    public boolean finishedRound = false;
    public boolean isTurnHolder = false;

    public Player(ConnectedClient player) {
        this.player = player;
    }

    public String getUsername(){
        return player.getUsername();
    }

    public ConnectedClient getConnection() {
        return player;
    }

    public boolean hasFinishedRound() {
        return finishedRound;
    }

    public void setHasFinishedRound(boolean hasFinishedRound) {
        this.finishedRound = hasFinishedRound;
    }

    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setTurnHolder(){
        isTurnHolder = true;
    }

    public void addToTotalScore(int roundScore){
        totalScore += roundScore;
    }
}
