package server.response;

public enum ResponseType {
//    CLIENT_CONNECTED,
//    CLIENT_DISCONNECTED,
//    QUEUE_JOINED,
//    GAME_JOINED,
//    YOUR_TURN,
//    OTHER_PLAYERS_TURN,
//    QUESTIONS,


    CLIENT_CONNECTED,
    CLIENT_DISCONNECTED,
    QUEUE_JOINED,
    GAME_STARTED,      // Indicates the game has started
    YOUR_TURN,         // Includes category choices
    OTHER_PLAYERS_TURN,
    QUESTIONS,         // Includes questions for the chosen category
    ROUND_RESULT,      // Sends the result of a round
    GAME_OVER
}
