package client;

import gamelogic.Category;
import gamelogic.Question;
import server.GameInstance;
import server.response.CategoryPackageResponse;
import server.response.QuestionPackageResponse;
import server.response.Response;

import javax.swing.*;
import java.util.List;

public class ClientProtocol {

    public void processResponse(Response response, Client client){
        switch (response.getResponseType()){
            case CLIENT_CONNECTED -> {
                System.out.println("Received client connected");
                client.gameGUI.switchPanel(2);
                String username = client.gameGUI.loginPanel.usernameTextField.getText();
                client.username = username;
                client.gameGUI.mainPanel.welcomePrompt.setText("Welcome " + username);
            }
            case CLIENT_DISCONNECTED -> {
                System.out.println("Client disconnected");
                client.gameGUI.switchPanel(1);
            }
            case QUEUE_JOINED -> {
                System.out.println("Received queue joined");
                client.gameGUI.switchPanel(5);
            }
            case YOUR_TURN -> {
                System.out.println("Received your turn");
                if (response instanceof CategoryPackageResponse categoryPackageResponse) {
                    List<Category> categories = categoryPackageResponse.getSetOfCategories();
                    client.gameGUI.categoryPanel.category1.setText(categories.get(0).name());
                    client.gameGUI.categoryPanel.category2.setText(categories.get(1).name());
                    client.gameGUI.categoryPanel.category3.setText(categories.get(2).name());
                }
                client.gameGUI.switchPanel(3);
            }
            case OTHER_PLAYERS_TURN -> {
                System.out.println("Received other players turn");
                client.gameGUI.switchPanel(5);
                client.gameGUI.waitingPanel.queuedLabel.setText("Waiting for other players turn");
            }
            case QUESTIONS -> {
                if (response instanceof QuestionPackageResponse questionPackageResponse) {
                    System.out.println("Received questions");
                    client.gameGUI.switchPanel(4);
                    List<Question> questions = questionPackageResponse.getSetOfQuestions();
                    GameRound round = new GameRound(client, questions);
                    client.currentScore = round.score;
                }
            }
        }
    }

}
