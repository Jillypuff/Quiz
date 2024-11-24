package client;

import gamelogic.Category;
import gamelogic.Question;
import server.response.CategoryPackageResponse;
import server.response.QuestionPackageResponse;
import server.response.Response;
import server.response.ResultPackageResponse;

import java.util.List;

public class ClientProtocol {

    public void processResponse(Client client, Response response){
        switch(response.getResponseType()){
            case CLIENT_CONNECTED -> {
                System.out.println("Client connected");
                client.gameGUI.switchPanel(2);
            }
            case CLIENT_DISCONNECTED -> {
                System.out.println("Client disconnected");
                System.exit(0);
            }
            case QUEUE_JOINED -> {
                System.out.println("In queue");
                client.gameGUI.waitingPanel.setUpQueuePanel();
                client.gameGUI.switchPanel(5);
            }
            case ROUND_STARTED -> {
                System.out.println("Round started");
                if (response instanceof CategoryPackageResponse categoryPackageResponse) {
                    List<Category> categories = categoryPackageResponse.getCategories();
                    client.gameGUI.categoryPanel.setUpCategories(categories);
                    client.gameGUI.switchPanel(3);
                }
            }
            case QUESTIONS -> {
                System.out.println("Questions received");
                if(response instanceof QuestionPackageResponse questionPackageResponse) {
                    List<Question> questions = questionPackageResponse.getQuestions();
                    GameRound round = new GameRound(client, questions);
                    client.gameGUI.switchPanel(4);
                }
            }
            case ROUND_RESULT -> {
                System.out.println("Round result");
                if(response instanceof ResultPackageResponse resultPackageResponse) {
                    int myScore = resultPackageResponse.getYourScore();
                    int opponentScore = resultPackageResponse.getOpponentsScore();
                    client.gameGUI.uglyScorePanel.setScoreDisplay(myScore, opponentScore);
                    client.gameGUI.switchPanel(6);
                }
            }
            case OTHER_PLAYERS_TURN -> {
                System.out.println("Other players turn");
                client.gameGUI.waitingPanel.setUpWaitingForYourTurnPanel();
                client.gameGUI.switchPanel(5);
            }
        }
    }
}
