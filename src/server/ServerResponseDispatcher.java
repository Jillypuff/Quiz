package server;

import java.io.IOException;

//DENNA AGERAR SOM MELLANHAND MELLAN CONNECTEDCLIENT OCH SERVER;
//CONNECTEDCLIENT SKICKAR IN ETT EVENT I DETTA OBJEKT, HÄR SKICKAR OBJEKTET VIDARE TILL SERVERN
//UTAN TIGHT COUPLING; FUNGERAR LIKNANDE PROTOKOLLET VI HADE INNAN
public class ServerResponseDispatcher {
    Server server;

    public ServerResponseDispatcher(Server server) {
        this.server = server;
    }

    public void dispatch(Event event) throws IOException {
        switch(event.getRequestType()){
            case CONNECT ->{
                server.handleConnect(event);
            }
            case DISCONNECT -> {
                server.handleDisconnect(event);
            }
            case START_GAME -> {
                server.handleStartGame(event);
            }
            case EXIT_GAME -> {

            }
            case LEAVE_QUEUE -> {

            }
        }
    }
}
