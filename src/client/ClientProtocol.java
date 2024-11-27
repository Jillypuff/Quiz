package client;

import Modules.Category;
import Modules.Response;

import java.util.List;

public class ClientProtocol {

    GameManager gameManager;

    public void processResponse(Response response, Client client){
        switch (response.getResponse()){
            case CLIENT_CONNECTED -> {
                System.out.println("Received client connected");
                client.gameGUI.switchPanel(2);
                String username = client.gameGUI.loginPanel.usernameTextField.getText();
                client.username = username;
                client.gameGUI.welcomePanel.getWelcomePrompt().setText("Welcome " + username + "!");
            }
            case CLIENT_DISCONNECTED -> {
                System.out.println("Client disconnected");
                client.gameGUI.switchPanel(1);
            }
            case QUEUE_JOINED -> {
                System.out.println("Received queue joined");
                client.gameGUI.switchPanel(2);
            }
            case GAME_JOINED -> {
                System.out.println("Game joined");
                // sätter in client istället för client.gamegui
                gameManager = new GameManager
                        (response.getAmountOfRounds(), response.isActivePlayer(), client);
            }
            case CHOSE_CATEGORY -> {
                System.out.println("Choose category");
                List<Category> categoryChoices = response.getCategories();
                client.gameGUI.categoryPanel.getCategory1().setText(categoryChoices.get(0).name());
                client.gameGUI.categoryPanel.getCategory2().setText(categoryChoices.get(1).name());
                client.gameGUI.categoryPanel.getCategory3().setText(categoryChoices.get(2).name());

                client.gameGUI.switchPanel(3);
            }
            case WAITING_FOR_CATEGORY_CHOICE -> {
                System.out.println("Waiting for opponent to select category");
                client.gameGUI.switchPanel(5);
                client.gameGUI.waitingPanel.getQueuedLabel().setText("Waiting for other players turn");
            }
            case GAME_STARTED -> {
                System.out.println("Game started");
                client.gameGUI.switchPanel(4);
                gameManager.startNewRound(response.getQuestionPackage());
            }
            case SEND_SCORE -> {
                System.out.println("new score value received");
                GameManager gameManager = client.gameManager;
                gameManager.setScore(response.getYourScore());
                gameManager.setOpponentScore(response.getOpponentScore());
            }
        }
    }
}
