package server;

import client.request.Request;
import client.request.RoundFinishedRequest;
import client.request.StartRoundRequest;
import gamelogic.Category;
import server.response.QuestionPackageResponse;
import server.response.ResponseType;
import server.response.Response;

import java.io.IOException;


public class ServerProtocol implements RequestHandler{

    GameManager gameManager;

    public ServerProtocol(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void processRequest(Request request, ConnectedClient client) throws IOException {
        switch (request.getRequestType()){
            case CONNECT -> {
                System.out.println("Received connect request; sending connected response");
                client.setUsername(request.username);
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
            }
            case LEAVE_QUEUE -> {
                client.server.queue.remove(client);
            }
            case CATEGORY_CHOSEN -> {
                if(client.equals(client.gameInstance.turnHolder)){
                    if (request instanceof StartRoundRequest startRoundRequest){
                        client.gameInstance.game.setCurrentCategory(startRoundRequest.getChosenCategory());
                        client.gameInstance.game.loadCurrentSetOfQuestions();
                        Category currentCategory = client.gameInstance.game.getCurrentCategory();

                        System.out.println("Sending questions to turn holder: " + client.getUsername());
                        client.sendResponse(new QuestionPackageResponse(ResponseType.QUESTIONS, currentCategory, client.gameInstance.game.getCurrentSetOfQuestions()));
                    }
                }

            }
            case ROUND_FINISHED -> {
                if(request instanceof RoundFinishedRequest roundFinishedRequest){
                    int score = roundFinishedRequest.getScore();
                    client.gameInstance.awardPointsToTurnHolder(score);
                    client.server.handleRoundFinished(client.gameInstance, client);
                }
            }
            case GIVE_UP -> {
                // Ta bort spelaren ut spelet
            }
            default -> System.err.println("How did we end up here!?");
        }
    }
}
