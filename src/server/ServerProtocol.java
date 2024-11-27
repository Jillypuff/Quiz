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
                client.readyForNewRound(true);
                client.server.handleStartGame(client);
            }
            case EXIT_GAME -> {

            }
            case LEAVE_QUEUE -> {
                client.server.queue.remove(client);
            }
            case CATEGORY_CHOSEN -> {
                System.out.println("GOT CATEGORY CHOSEN");
                Category selected = request.getChosenCategory();
                GameInstance instance = client.instance;
                instance.categoryChosen(selected);
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
                    instance.updatePlayer1Score(request.answer);
                    System.out.println("added: " + request.answer + " to player1");
                    instance.sendRoundScore(client);
                }
                else {
                    instance.updatePlayer2Score(request.answer);
                    System.out.println("added: " + request.answer + " to player2");
                    instance.sendRoundScore(client);
                }
            }
            case FINAL_RESULT -> {
                if (client.username.equals(client.instance.player1.username)) {
                    client.instance.updatePlayer1Score(request.answer);
                } else {
                    client.instance.updatePlayer2Score(request.answer);
                }
                System.out.println("Waiting for final result");
                client.instance.sendFinalResults(client);
            }
            default -> System.err.println("How did we end up here!?");
        }
    }
}
