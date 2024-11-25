package server.response;

public enum ResponseType {
    CLIENT_CONNECTED,
    CLIENT_DISCONNECTED,
    QUEUE_JOINED,
    GAME_STARTED,
    ROUND_STARTED,
    OTHER_PLAYERS_TURN,
    QUESTIONS,
    ROUND_RESULT,
    FINAL_RESULT,
}
