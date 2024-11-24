package server;

import client.request.Request;
import client.request.RoundFinishedRequest;
import client.request.StartRoundRequest;
import gamelogic.Category;

import java.io.IOException;

public class ServerProtocol {

    GameManager gameManager;

    public ServerProtocol(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void processRequest(ClientConnection client, Request request) throws IOException {
        switch(request.getRequestType()){
            case CONNECT -> {
                String selectedUsername = request.getUsername();
                gameManager.handleConnectRequest(client, selectedUsername);
            }
            case DISCONNECT -> {
                gameManager.handleDisconnectRequest(client);
            }
            case START_GAME -> {
                gameManager.handleStartGameRequest(client);
            }
            case CATEGORY_CHOSEN -> {
                if (request instanceof StartRoundRequest startRoundRequest) {
                    Category selectedCategory = startRoundRequest.getSelectedCategory();
                    gameManager.handleCategoryChosen(client, selectedCategory);
                }
            }
            case ROUND_FINISHED -> {
                if (request instanceof RoundFinishedRequest roundFinishedRequest) {
                    int score = roundFinishedRequest.getMyScore();
                    gameManager.handleRoundFinished(client, score);
                }
            }
            case LEAVE_QUEUE -> {
                gameManager.handleLeaveQueue(client);
            }
        }
    }

}
