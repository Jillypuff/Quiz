package client;

import client.request.RequestType;
import client.request.RoundFinishedRequest;
import gamelogic.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameRound {

    Client client;
    List<JButton> gameButtons;
    List<Question> questions;
    int score = 0;
    int questionNumber = 0;
    Question currentQuestion;

    public GameRound(Client client, List<Question> questions) {
        this.client = client;
        this.questions = questions;
        gameButtons = client.gameGUI.gamePanel.getAllAnswerButtons();
        updateQuestionBoard(questionNumber);
        addActionListenerToNextQuestionButton();
    }

    public void fillBoardWithCurrentAlternatives(Question question) {
        client.gameGUI.gamePanel.getQuestionLabel().setText(question.getQuestion());
        List<String> alternatives = question.getAlternatives();
        for(int i = 0; i < alternatives.size(); i++){
            gameButtons.get(i).setText(alternatives.get(i));
        }
        enableButtons(true);
    }

    public void enableButtons(boolean enabled) {
        if (enabled) {
            for(int i = 0; i < gameButtons.size(); i++){
                gameButtons.get(i).setEnabled(true);
            }
        }
        else{
            for(int i = 0; i < gameButtons.size(); i++){
                gameButtons.get(i).setEnabled(false);
            }
        }
    }

    public void addActionListeners(){
        for(JButton button : gameButtons){
            for(ActionListener actionListener : button.getActionListeners()){
                button.removeActionListener(actionListener);
            }
            button.addActionListener(e -> {
                handleAnswerSelection(button);
            });
        }
    }

    public void handleAnswerSelection(JButton button){
        String answerAlternative = button.getText();
        if(currentQuestion.checkAnswer(answerAlternative)){
            button.setBackground(Color.GREEN);
            score++;
        }
        else{
            button.setBackground(Color.RED);
            for(JButton otherButton : gameButtons){
                if (currentQuestion.checkAnswer(otherButton.getText())) {
                    otherButton.setBackground(Color.GREEN);
                }
            }
        }
        enableButtons(false);
        client.gameGUI.gamePanel.getNextQuestionButton().setVisible(true);
    }

    public void updateQuestionBoard(int questionNumber){
        currentQuestion = questions.get(questionNumber);
        fillBoardWithCurrentAlternatives(currentQuestion);

        for (JButton button : gameButtons) {
            button.setBackground(null);
        }

        addActionListeners();
        client.gameGUI.gamePanel.getNextQuestionButton().setVisible(false);
    }

    public void addActionListenerToNextQuestionButton(){
        for(ActionListener actionListener:client.gameGUI.gamePanel.getNextQuestionButton().getActionListeners()){
            client.gameGUI.gamePanel.getNextQuestionButton().removeActionListener(actionListener);
        }

        client.gameGUI.gamePanel.getNextQuestionButton().addActionListener(e->{
            client.gameGUI.gamePanel.resetButtons();
            questionNumber++;
            if (questionNumber < questions.size()) {
                updateQuestionBoard(questionNumber);
            } else {
                client.gameGUI.switchPanel(5);
                client.gameGUI.waitingPanel.queuedLabel.setText("Waiting for other player to finish round");
                System.out.println(client.username + " sending round finished");
                client.sendRequest(new RoundFinishedRequest(RequestType.ROUND_FINISHED, client.username, score));
            }
        });
    }
}
