package Modules;

import gamelogic.Question;

import java.util.List;

public class QuestionInPanel {
    Question question;
    protected int receivedAnswer1;
    protected int receivedAnswer2;
    protected boolean isCorrectPlayer1;
    protected boolean isCorrectPlayer2;
    protected String player1;
    protected String player2;

    // Variables for players to select category from list
    protected Category valdkategori;
    // ska uppdateras efter varje runda, och den valda ska tas ut ur listan med alla möjliga kategorier
    protected List<Category> randomCategoryChoices;

    public Question getQuestion() {
        return question;
    }

    boolean checkAnswer(String answer){
        return checkAnswer(answer);
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getReceivedAnswer1() {
        return receivedAnswer1;
    }

    public void setReceivedAnswer1(int receivedAnswer) {
        this.receivedAnswer1 = receivedAnswer;
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
