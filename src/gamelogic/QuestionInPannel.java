package gamelogic;

public class QuestionInPannel {
    Question question;
    protected int recivedAwnser;
    protected boolean isCorrectPlayer1;
    protected boolean isCorrectPlayer2;
    protected Category valdkategori;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getRecivedAwnser() {
        return recivedAwnser;
    }

    public void setRecivedAwnser(int recivedAwnser) {
        this.recivedAwnser = recivedAwnser;
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
