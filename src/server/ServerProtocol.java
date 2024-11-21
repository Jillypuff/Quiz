package server;

import client.Request;

import java.io.IOException;

public class ServerProtocol {

    public void processRequest(Request request, ConnectedClient client) throws IOException {
        switch (request.getType()){
            case CONNECT -> {
                System.out.println("Received connect request; sending connected response");
                client.username = request.username;
                client.sendResponse(new Response(ReponseType.CLIENT_CONNECTED));
            }
            case DISCONNECT -> {
                System.out.println("Received disconnect request; sending disconnected response");
                client.sendResponse(new Response(ReponseType.CLIENT_DISCONNECTED));
            }
            case START_GAME -> {
                client.server.queue.add(client);
                client.server.createInstance();
                //client.queueClient(client);
                // Skicka tillbaka ett medelande
            }
            case EXIT_GAME -> {
                // Säg hejdå till username
                // Stäng ner connetions
            }
            case LEAVE_QUEUE -> {
                // Plocka ut username ur kön
                // Skicka confirmation
            }
            case CATEGORY_CHOSEN -> {
                // Valde kategorin request.getAnswer();
                // Ge feedback
            }
            case ANSWER -> {
                // Valde svaret request.getAnswer();
                // Ge feedback om korrekt eller inte
            }
            case NEXT_QUESTION -> {
                // Ge nästa fråga
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
