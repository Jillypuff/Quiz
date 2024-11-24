package gamelogic;

import server.*;

public class GameInstance {

    private Player playerOne;
    private Player playerTwo;
    private Player currentTurnHolder;
    private QuizPackage quizPackage;
    private int roundsFinished = 0;

    public GameInstance() {
        quizPackage = new QuizPackage();
    }

    public void setPlayers(ClientConnection clientOne, ClientConnection clientTwo) {
        playerOne = new Player(clientOne);
        playerTwo = new Player(clientTwo);
        currentTurnHolder = playerOne;
    }

    public Player getCurrentTurnHolder(){
        return currentTurnHolder;
    }

    public Player getOpponent(Player player){
        return (player == playerOne) ? playerTwo : playerOne;
    }

    public void switchTurn(){
        currentTurnHolder = (currentTurnHolder == playerOne) ? playerTwo : playerOne;
    }

    public void incrementRoundsFinished(){
        roundsFinished++;
    }

    public boolean bothPlayersFinishedRound(){
        return playerOne.hasFinishedRound() && playerTwo.hasFinishedRound();
    }

    public boolean isGameOver(){
        return roundsFinished >= quizPackage.MAX_ROUNDS_PER_GAME;
    }

    public void resetRound(){
        playerOne.roundScore = 0;
        playerTwo.roundScore = 0;
        playerOne.setHasFinishedRound(false);
        playerTwo.setHasFinishedRound(false);
    }

    public QuizPackage getQuizPackage() {
        return quizPackage;
    }

    public void setQuizPackage(QuizPackage quizPackage) {
        this.quizPackage = quizPackage;
    }
}
