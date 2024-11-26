package server;

import Modules.Category;
import Modules.ResponseType;
import Modules.Request;
import Modules.Response;
import gamelogic.GameInstance;

import java.io.IOException;

public class ServerProtocol {

    public void processRequest(Request request, ConnectedClient client) throws IOException {
        switch (request.getType()){
            case CONNECT -> {
                System.out.println("Received connect request; sending connected response");
                client.username = request.username;
                client.sendResponse(new Response(ResponseType.CLIENT_CONNECTED));
            }
            case DISCONNECT -> {
                System.out.println("Received disconnect request; sending disconnected response");
                client.sendResponse(new Response(ResponseType.CLIENT_DISCONNECTED));
            }
            case START_GAME -> {
                client.server.handleStartGame(client);
            }
            case EXIT_GAME -> {
                // Säg hejdå till username
                // Stäng ner connetions
            }
            case LEAVE_QUEUE -> {
                client.server.queue.remove(client);
                // Plocka ut username ur kön
                // Skicka confirmation?
            }
            case CATEGORY_CHOSEN -> {
                System.out.println("GOT CATEGORY CHOSEN");
                Category selected = request.getChosenCategory();
                GameInstance instance = client.instance;
                instance.categoryChosen(selected);
            }
            case NEXT_ROUND -> {

            }
            case GIVE_UP -> {
                // Ta bort spelaren ut spelet
                // Anropa båda spelarna att en spelare har gett upp
                // Avsluta spelet
            }
            default -> System.err.println("How did we end up here!?");
        }
    }
}
