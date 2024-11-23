package client;

import gamelogic.Category;
import gamelogic.Question;
import server.GameInstance;
import server.response.CategoryPackageResponse;
import server.response.QuestionPackageResponse;
import server.response.Response;
import server.response.ResultResponse;

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
                System.out.println("Client: " + username);
                client.gameGUI.mainPanel.welcomePrompt.setText("Welcome " + username);
            }
            case CLIENT_DISCONNECTED -> {
                System.out.println("Client disconnected");
                client.gameGUI.switchPanel(1);
            }
            case QUEUE_JOINED -> {
                System.out.println(client.username + " received queue joined");
                client.gameGUI.switchPanel(5);
            }
            case CATEGORIES -> {
                System.out.println(client.username + " received your turn");
                client.myTurn = true;
                if (response instanceof CategoryPackageResponse categoryPackageResponse) {
                    List<Category> categories = categoryPackageResponse.getSetOfCategories();
                    client.gameGUI.categoryPanel.category1.setText(categories.get(0).name());
                    client.gameGUI.categoryPanel.category2.setText(categories.get(1).name());
                    client.gameGUI.categoryPanel.category3.setText(categories.get(2).name());
                }
                client.gameGUI.switchPanel(3);
            }
            case OTHER_PLAYERS_TURN -> {
                System.out.println(client.username + " received other players turn");
                client.myTurn = false;
                client.gameGUI.switchPanel(5);
                client.gameGUI.waitingPanel.queuedLabel.setText("Waiting for other players turn");
            }
            case QUESTIONS -> {
                if (response instanceof QuestionPackageResponse questionPackageResponse) {
                    System.out.println(client.username + " received questions");
                    client.myTurn = true;
                    client.gameGUI.switchPanel(4);
                    List<Question> questions = questionPackageResponse.getSetOfQuestions();
                    GameRound round = new GameRound(client, questions, client.currentScore);
//                    client.currentScore += round.score;
                }
            }
            case ROUND_RESULT -> {
                if (response instanceof ResultResponse resultResponse){
                    int playersScore = resultResponse.getPlayerResult();
                    int opponentsScore = resultResponse.getOpponentResult();
                    client.gameGUI.uglyScorePanel.setOpponentScore(opponentsScore);
                    client.gameGUI.uglyScorePanel.setPlayerScore(playersScore);
                    if (playersScore > opponentsScore){
                        client.gameGUI.uglyScorePanel.setResultText("You won this round!");
                    }
                    else if (opponentsScore > playersScore){
                        client.gameGUI.uglyScorePanel.setResultText("You lost this round.");
                    }
                    else{
                        client.gameGUI.uglyScorePanel.setResultText("It was a draw.");
                    }
                    client.gameGUI.switchPanel(6);
                }
            }
            case GAME_OVER -> {
                if (response instanceof ResultResponse resultResponse){
                    System.out.println("Received game over");
                    int playersScore = resultResponse.getPlayerResult();
                    System.out.println(playersScore);
                    int opponentsScore = resultResponse.getOpponentResult();
                    System.out.println(opponentsScore);
                    client.gameGUI.uglyScorePanel.setOpponentScore(playersScore);
                    client.gameGUI.uglyScorePanel.setPlayerScore(opponentsScore);
                    if (playersScore > opponentsScore){
                        client.gameGUI.uglyScorePanel.setResultText("YOU WON THE GAME!");
                    }
                    else if (opponentsScore > playersScore){
                        client.gameGUI.uglyScorePanel.setResultText("You lost.");
                    }
                    else{
                        client.gameGUI.uglyScorePanel.setResultText("The game ended on a draw.");
                    }
                    client.gameGUI.switchPanel(6);
                }
            }
        }
    }
}
