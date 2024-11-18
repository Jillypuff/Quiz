public class Request {

    RequestType type;
    String playerAnswer;
    Question question;

    public Request(RequestType type) {
        this.type = type;
    }

    public Request(RequestType type, String playerAnswer, Question question) {
        this.type = type;
        this.playerAnswer = playerAnswer;
        this.question = question;
    }
}
