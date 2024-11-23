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
    CATEGORIES,         //Skickas till den spelare vars tur det är (efter GAME_STARTED, till den först i kön)
    OTHER_PLAYERS_TURN, //Skickas till den spelare som ska vänta
    QUESTIONS,         //Sickas till den som valt kategori, sen till den som ska köra efter
    ROUND_RESULT,      //Skickas till både spelarna
    GAME_OVER
}
