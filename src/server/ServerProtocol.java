package server;

import client.request.Request;
import client.request.RoundFinishedRequest;
import client.request.StartRoundRequest;
import gamelogic.Category;
import gamelogic.Question;
import server.response.QuestionPackageResponse;
import server.response.ResponseType;
import server.response.Response;

import java.io.IOException;
import java.util.List;


public class ServerProtocol {

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
                if (request instanceof StartRoundRequest startRoundRequest){
                    client.gameInstance.game.setCurrentCategory(startRoundRequest.getChosenCategory());

                    System.out.println("Received get questions request, fetching questions for current category");
                    List<Question> questions = client.gameInstance.game.getQuestionsForCurrentCategory();
                    Category currentCategory = client.gameInstance.game.getCurrentCategory();

                    System.out.println("Sending them");
                    client.sendResponse(new QuestionPackageResponse(ResponseType.QUESTIONS, currentCategory, questions));
                }
            }
            case ROUND_FINISHED -> {
                if(request instanceof RoundFinishedRequest roundFinishedRequest){
                    int score = roundFinishedRequest.getScore();
                    client.gameInstance.awardPointsToTurnHolder(score);
                    client.server.handleTurnSwitch(client.gameInstance);
                }
            }
            case GIVE_UP -> {
                // Ta bort spelaren ut spelet
            }
            default -> System.err.println("How did we end up here!?");
        }
    }
}
