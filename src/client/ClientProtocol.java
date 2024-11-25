package client;

import GUI.panels.GameBoardPanel;
import Modules.Category;
import Modules.QuestionInPanel;
import gamelogic.Question;
import Modules.Response;

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
            case GAME-> {

                QuestionInPanel[][] gameBoard = response.getGameBoard();

                if(isFirstRoundAndImPlayer1(gameBoard,client)){
                    System.out.println("isFirstRoundAndImPlayer1 1 category pannel");
                    client.gameGUI.switchPanel(3);
                    client.gameGUI.categoryPanel.getCategory1().setText(gameBoard[0][0].getRandomCategoryChoices().get(0).name());
                    client.gameGUI.categoryPanel.getCategory2().setText(gameBoard[0][0].getRandomCategoryChoices().get(1).name());
                    client.gameGUI.categoryPanel.getCategory3().setText(gameBoard[0][0].getRandomCategoryChoices().get(2).name());
                }else{
                    System.out.println("isFirstRoundAndImPlayer1 2 (gamebordpannel)");
                    client.gameGUI.switchPanel(6);
                    client.gameGUI.gameBoardPanel.uppdateGameBoardPannel(gameBoard);
                }
            }
            /*
            case YOUR_TURN -> {
                System.out.println("Received your turn");
                List<Category> categoryChoices = response.getSetOfCategories();
                client.gameGUI.categoryPanel.getCategory1().setText(categoryChoices.get(0).name());
                client.gameGUI.categoryPanel.getCategory2().setText(categoryChoices.get(1).name());
                client.gameGUI.categoryPanel.getCategory3().setText(categoryChoices.get(2).name());

                client.gameGUI.switchPanel(3);
            }
            */
            case OTHER_PLAYERS_TURN -> {
                System.out.println("Received other players turn");
                client.gameGUI.switchPanel(5);
                client.gameGUI.waitingPanel.getQueuedLabel().setText("Waiting for other players turn");
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
    //Skriv metoder här *****Joakim***** Till GAME case

    public boolean isFirstRoundAndImPlayer1(QuestionInPanel[][] gameBoard, Client client){
        if(gameBoard[0][0].getValdkategori()==null){
            if(gameBoard[0][0].getPlayer1().equals(client.username)){
                return true;
            }
            else {
                return false;
            }
        } else{
            return false;
        }

    }


}
