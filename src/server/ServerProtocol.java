package server;

import client.Request;
import gamelogic.Category;
import gamelogic.Question;

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
                // Valde kategorin request.getAnswer();
                // Ge feedback
            }
            case ANSWER -> {
                // Valde svaret request.getAnswer();
                // Ge feedback om korrekt eller inte
            }
            case NEXT_QUESTION -> {
                client.currentGame.setCurrentCategory(request.getChosenCategory());
                System.out.println("Set current category to " + request.getChosenCategory());
                System.out.println("Trying to fetch current question");
                Question question = client.currentGame.getCurrentQuestion();
                System.out.println("Question: " + question.getQuestion());
                client.sendResponse(new Response(ReponseType.QUESTION, question));
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
