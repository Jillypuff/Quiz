package Modules;

import gamelogic.Question;

public class QuestionInPanel {
    Question question;
    protected int recivedAwnser1;
    protected int recivedAwnser2;
    protected boolean isCorrectPlayer1;
    protected boolean isCorrectPlayer2;
    protected String player1;
    protected String player2;

    // Variables for players to select category from list
    protected Category valdkategori;

    public Question getQuestion() {
        return question;
    }

    boolean checkAnswer(String answer){
        return checkAnswer(answer);
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getRecivedAwnser() {
        return recivedAwnser1;
    }

    public void setRecivedAwnser(int recivedAwnser) {
        this.recivedAwnser1 = recivedAwnser;
    }

    public boolean isCorrectPlayer1() {
        return isCorrectPlayer1;
    }

    public void setCorrectPlayer1(boolean correctPlayer1) {
        isCorrectPlayer1 = correctPlayer1;
    }

    public boolean isCorrectPlayer2() {
        return isCorrectPlayer2;
    }

    public void setCorrectPlayer2(boolean correctPlayer2) {
        isCorrectPlayer2 = correctPlayer2;
    }

    public Category getValdkategori() {
        return valdkategori;
    }

    public void setValdkategori(Category valdkategori) {
        this.valdkategori = valdkategori;
    }
}
