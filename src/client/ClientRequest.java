package client;

public enum ClientRequest {
    CONNECT,
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
