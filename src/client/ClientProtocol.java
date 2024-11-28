package client;

import Modules.Category;
import Modules.Response;

import javax.swing.*;
import java.util.List;

public class ClientProtocol {

    private GameManager gameManager;

    public void processResponse(Response response, Client client){
        switch (response.getResponse()){
            case CLIENT_CONNECTED -> {
                System.out.println("Received client connected");
                client.getGameGUI().switchPanel(2);
                String username = client.getGameGUI().loginPanel.getUsernameTextField().getText();
                client.setUsername(username);
                client.getGameGUI().welcomePanel.getWelcomePrompt().setText("Welcome " + username + "!");
            }
            case CLIENT_DISCONNECTED -> {
                System.out.println("Client disconnected");
                client.getGameGUI().switchPanel(1);
            }
            case QUEUE_JOINED -> {
                System.out.println("Received queue joined");
                client.getGameGUI().waitingPanel.setUpWaitingPanel(false);
                client.getGameGUI().switchPanel(5);
            }
            case GAME_JOINED -> {
                System.out.println("Game joined");
                client.inGame(true);
                gameManager = new GameManager
                        (response.getAmountOfRounds(), response.isActivePlayer(), client);
            }
            case CHOSE_CATEGORY -> {
                System.out.println("Choose category");
                List<Category> categoryChoices = response.getCategories();
                client.getGameGUI().categoryPanel.getCategory1().setText(categoryChoices.get(0).name());
                client.getGameGUI().categoryPanel.getCategory2().setText(categoryChoices.get(1).name());
                client.getGameGUI().categoryPanel.getCategory3().setText(categoryChoices.get(2).name());

                client.getGameGUI().switchPanel(3);
            }
            case WAITING_FOR_CATEGORY_CHOICE -> {
                System.out.println("Waiting for opponent to select category");
                client.getGameGUI().switchPanel(5);
                client.getGameGUI().waitingPanel.setUpWaitingPanel(true);
            }
            case GAME_STARTED -> {
                System.out.println("Game started");
                client.getGameGUI().switchPanel(4);
                gameManager.startNewRound(response.getQuestionPackage());
            }
            case SEND_SCORE -> {
                client.getGameGUI().scorePanel.setButtonToContinue();
                System.out.println("new score value received");
                client.getGameGUI().switchPanel(6);
                client.getGameGUI().scorePanel.setScoreDisplay(response.getYourScore(), response.getOpponentScore(), false);
            }
            case SEND_FINAL_RESULT -> {
                client.getGameGUI().scorePanel.setButtonToContinue();
                client.inGame(false);
                System.out.println("Final result received");
                client.getGameGUI().switchPanel(6);
                client.getGameGUI().scorePanel.setScoreDisplay(response.getYourScore(), response.getOpponentScore(), true);
            }
            case GAME_OVER ->{
                System.out.println("Other player gave up");
                client.inGame(false);
                client.getGameGUI().switchPanel(2);
                JOptionPane.showMessageDialog(client.getGameGUI(), "Other player gave up!");
            }
        }
    }
}
