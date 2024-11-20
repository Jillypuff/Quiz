package server;

public enum ResponseType {
    CLIENT_CONNECTED,
    CLIENT_DISCONNECTED,

    QUEUE_JOINED,
    GAME_JOINED,

    SEND_QUESTION,
    SEND_ANSWER,

    SEND_SCORE,
    SEND_FINAL_RESULT,
}
