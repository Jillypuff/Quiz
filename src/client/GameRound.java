package client;

import client.request.Request;
import client.request.RequestType;
import client.request.RoundFinishedRequest;
import gamelogic.Question;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameRound {

    Client client;
    List<JButton> gameButtons;
    boolean wasCorrect = false;
    List<Question> questions;
    int score = 0;
    int questionNumber = 0;
    Question currentQuestion;

    public GameRound(Client client, List<Question> questions) {
        this.client = client;
        this.questions = questions;
        gameButtons = client.gameGUI.gamePanel.getAllAnswerButtons();
        updateQuestionBoard(questionNumber);
        addActionListeners();
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
        List<JButton> buttons = client.gameGUI.gamePanel.getAllAnswerButtons();

        for(JButton button : buttons){
            button.addActionListener(e -> {
                String answer = button.getText();
                if (currentQuestion.checkAnswer(answer)){
                    button.setBackground(Color.GREEN);
                    wasCorrect = true;
                }
                else{
                    button.setBackground(Color.RED);
                    for (JButton otherButton : gameButtons) {
                        if (currentQuestion.checkAnswer(otherButton.getText())) {
                            otherButton.setBackground(Color.GREEN);
                        }
                    }
                }
                enableButtons(false);
                client.gameGUI.gamePanel.getNextQuestionButton().setVisible(true);
            });
        }
    }

    public void updateQuestionBoard(int questionNumber){
        if (wasCorrect) {
            score++;
        }
        wasCorrect = false;
        currentQuestion = questions.get(questionNumber);
        fillBoardWithCurrentAlternatives(currentQuestion);

        for (JButton button : gameButtons) {
            button.setBackground(null);
        }

        client.gameGUI.gamePanel.getNextQuestionButton().setVisible(false);
    }

    public void addActionListenerToNextQuestionButton(){
        client.gameGUI.gamePanel.getNextQuestionButton().addActionListener(e->{
            client.gameGUI.gamePanel.resetButtons();
            questionNumber++;
            if (questionNumber < questions.size()) {
                updateQuestionBoard(questionNumber);
            } else {
                client.gameGUI.switchPanel(5);
                client.sendRequest(new RoundFinishedRequest(RequestType.ROUND_FINISHED, client.username, score));
            }
        });
    }
}
