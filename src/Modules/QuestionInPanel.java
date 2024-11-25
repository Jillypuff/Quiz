package Modules;

import gamelogic.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionInPanel implements Serializable {

    Question question;
    protected String receivedAnswerPlayer1;
    protected String receivedAnswerPlayer2;
    protected boolean isCorrectPlayer1;
    protected boolean isCorrectPlayer2;
    protected String player1;
    protected String player2;

    // Variables for players to select category from list
    protected Category valdkategori;
    // ska uppdateras efter varje runda, och den valda ska tas ut ur listan med alla möjliga kategorier
    protected List<Category> randomCategoryChoices = new ArrayList<>();

    public Question getQuestion() {
        return question;
    }

    boolean checkAnswer(String answer){
        return checkAnswer(answer);
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public List<Category> getRandomCategoryChoices() {
        return randomCategoryChoices;
    }

    public void setRandomCategoryChoices(List<Category> randomCategoryChoices) {
        this.randomCategoryChoices = new ArrayList<>(randomCategoryChoices);
    }

    public String getReceivedAnswerPlayer1() {
        return receivedAnswerPlayer1;
    }

    public void setReceivedAnswerPlayer1(String receivedAnswerPlayer1) {
        this.receivedAnswerPlayer1 = receivedAnswerPlayer1;
    }

    public String getReceivedAnswerPlayer2() {
        return receivedAnswerPlayer2;
    }

    public void setReceivedAnswerPlayer2(String receivedAnswerPlayer2) {
        this.receivedAnswerPlayer2 = receivedAnswerPlayer2;
    }
}
