package server;

import Modules.Category;
import Modules.ResponseType;
import Modules.Request;
import Modules.Response;
import gamelogic.GameInstance;

import java.io.IOException;

public class ServerProtocol {

    Server server;

    public ServerProtocol(Server server) {
        this.server = server;
    }

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
                client.readyForNewRound(true);
                server.handleStartGame(client);
            }
            case EXIT_GAME -> {
                // Säg hejdå till username
            }
            case LEAVE_QUEUE -> {
                server.queue.remove(client);
            }
            case CATEGORY_CHOSEN -> {
                System.out.println("GOT CATEGORY CHOSEN");
                Category selected = request.getChosenCategory();
                GameInstance instance = client.instance;
                instance.categoryChosen(selected);
            }
            case ROUND_FINISHED -> {
                // Mellan-läge, skickas innan next round, hanterar poäng
            }
            case NEXT_ROUND -> {
                client.readyForNewRound(true);
                client.instance.sendRandomizedCategories();
            }
            case GIVE_UP -> {
                // Ta bort spelaren ut spelet
                // Anropa båda spelarna att en spelare har gett upp
                // Avsluta spelet
            }
            case ROUND_SCORE ->{
                System.out.println("GOT ROUND SCORE PLAYER: "+request.username);
                GameInstance instance = client.instance;
                if(client.username.equals(instance.player1.username)){
                    instance.uppdatePlayer1Score(request.answer);
                    System.out.println("added: " + request.answer + " to player1");
                    instance.sendUpdatedScore(client);
                }
                else {
                    instance.uppdatePlayer2Score(request.answer);
                    System.out.println("added: " + request.answer + " to player2");
                    instance.sendUpdatedScore(client);
                }
            }
            default -> System.err.println("How did we end up here!?");
        }
    }
}
