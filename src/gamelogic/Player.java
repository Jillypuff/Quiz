package gamelogic;

public class Player {

    String username;
    int score = 0;

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
