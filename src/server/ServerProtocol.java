package server;

import Modules.Category;
import Modules.ResponseType;
import Modules.Request;
import Modules.Response;
import gamelogic.GameInstance;

import java.io.IOException;

public class ServerProtocol {

    private final GameQueueManager gameQueueManager;

    public ServerProtocol(GameQueueManager gameQueueManager) {
        this.gameQueueManager = gameQueueManager;
    }

    public void processRequest(Request request, ConnectedClient client) throws IOException {
        switch (request.getType()){
            case CONNECT -> {
                System.out.println("Received connect request; sending connected response");
                client.setUsername(request.getUsername());
                client.sendResponse(new Response(ResponseType.CLIENT_CONNECTED));
            }
            case DISCONNECT -> {
                System.out.println("Received disconnect request; sending disconnected response");
                client.sendResponse(new Response(ResponseType.CLIENT_DISCONNECTED));
            }
            case START_GAME -> {
                client.readyForNewRound(true);
                gameQueueManager.handleStartGame(client);
            }
            case EXIT_GAME -> {
                client.closeEverything();
            }
            case LEAVE_QUEUE -> {
                gameQueueManager.removeFromQueue(client);
            }
            case CATEGORY_CHOSEN -> {
                System.out.println("GOT CATEGORY CHOSEN");
                Category selected = request.getChosenCategory();
                GameInstance instance = client.getInstance();
                instance.categoryChosen(selected);
            }
            case NEXT_ROUND -> {
                client.readyForNewRound(true);
                client.getInstance().sendRandomizedCategories();
            }
            case GIVE_UP -> {
                client.getInstance().sendGameOver(client);
            }
            case ROUND_SCORE ->{
                System.out.println("GOT ROUND SCORE PLAYER: "+request.getUsername());
                GameInstance instance = client.getInstance();
                if(client.getUsername().equals(instance.getPlayer1().getUsername())){
                    instance.updatePlayer1Score(request.getRoundScore());
                    System.out.println("added: " + request.getRoundScore() + " to player1");
                    instance.sendRoundScore(client);
                }
                else {
                    instance.updatePlayer2Score(request.getRoundScore());
                    System.out.println("added: " + request.getRoundScore() + " to player2");
                    instance.sendRoundScore(client);
                }
            }
            case FINAL_RESULT -> {
                if (client.getUsername().equals(client.getInstance().getPlayer1().getUsername())) {
                    client.getInstance().updatePlayer1Score(request.getRoundScore());
                } else {
                    client.getInstance().updatePlayer2Score(request.getRoundScore());
                }
                System.out.println("Waiting for final result");
                client.getInstance().sendFinalResults(client);
            }
            default -> System.err.println("How did we end up here!?");
        }
    }
}
