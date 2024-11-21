package client;

import gamelogic.Category;
import gamelogic.Question;
import server.Response;

import javax.swing.*;
import java.util.List;

public class ClientProtocol {

    public void processResponse(Response response, Client client){
        switch (response.getType()){
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
                List<Category> categoryChoices = response.getSetOfCategories();
                client.gameGUI.categoryPanel.category1.setText(categoryChoices.get(0).name());
                client.gameGUI.categoryPanel.category2.setText(categoryChoices.get(1).name());
                client.gameGUI.categoryPanel.category3.setText(categoryChoices.get(2).name());

                client.gameGUI.switchPanel(3);
            }
            case OTHER_PLAYERS_TURN -> {
                System.out.println("Received other players turn");
                client.gameGUI.switchPanel(5);
                client.gameGUI.waitingPanel.queuedLabel.setText("Waiting for other players turn");
            }
            case QUESTION -> {
                System.out.println("Received question");
                Question questionObj = response.getQuestion();
                List<JButton> buttons = client.gameGUI.gamePanel.getAllAnswerButtons();
                List<String> alternatives = questionObj.getAlternatives();
                buttons.get(0).setText(alternatives.get(0));
                buttons.get(1).setText(alternatives.get(1));
                buttons.get(2).setText(alternatives.get(2));
                buttons.get(3).setText(alternatives.get(3));
                String question = questionObj.getQuestion();
                client.gameGUI.gamePanel.getQuestionLabel().setText(question);
                client.gameGUI.switchPanel(4);
            }
        }
    }

}
