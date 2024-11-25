package client;

import Modules.Category;
import Modules.Response;

import java.util.List;

public class ClientProtocol {

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
                client.gameManager = new GameManager
                        (response.getAmountOfRounds(), response.isActivePlayer(), client.gameGUI);
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
                client.gameManager.startNewRound(response.getQuestionPackage());
            }
            /*
            case QUESTION -> {
                System.out.println("Received question");
                Question questionObj = response.getQuestion();
                List<JButton> buttons = client.gameGUI.questionPanel.getAllAnswerButtons();
                List<String> alternatives = questionObj.getAlternatives();
                buttons.get(0).setText(alternatives.get(0));
                buttons.get(1).setText(alternatives.get(1));
                buttons.get(2).setText(alternatives.get(2));
                buttons.get(3).setText(alternatives.get(3));
                String question = questionObj.getQuestion();
                client.gameGUI.questionPanel.getQuestionLabel().setText(question);
                client.gameGUI.switchPanel(4);
            }
            */
        }
    }
}
