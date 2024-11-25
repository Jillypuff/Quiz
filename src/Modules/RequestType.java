package client;

public enum RequestType {
    CONNECT,
    DISCONNECT,
    START_GAME,
    EXIT_GAME,
    LEAVE_QUEUE,

    // CHANGE_USERNAME,
    // CHECK_HIGHSCORE,

    CATEGORY_CHOSEN,

    ANSWER,
    NEXT_QUESTION,
    GIVE_UP,
}
