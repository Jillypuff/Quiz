package gamelogic;

import server.ClientConnection;

public class Player {

    ClientConnection player;
    int roundScore;
    int totalScore;
    public boolean finishedRound = false;
    public boolean isTurnHolder = false;

    public Player(ClientConnection player) {
        this.player = player;
    }

    public String getUsername(){
        return player.getClientUsername();
    }

    public ClientConnection getConnection() {
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
