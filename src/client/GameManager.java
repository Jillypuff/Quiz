package client;

import GUI.GameGUI;
import Modules.QuestionPackage;
import Modules.Request;
import Modules.RequestType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameManager {

    int scoreThisRound = 0;
    int amountOfRounds;
    int currentRound = 1;
    int amountOfQuestions;
    QuestionPackage questionPackage;
    GameGUI gameGUI;
    Client client;
    boolean isActivePlayer;
    boolean gameOver = false;

    public GameManager(int amountOfRounds, boolean isActivePlayer, Client client) {
        this.amountOfRounds = amountOfRounds;
        this.isActivePlayer = isActivePlayer;
        this.client = client;
        this.gameGUI = client.gameGUI;
        addActionListenerToQuestionButtons();
        addActionListenerToContinueButton();
    }

    void startNewRound(QuestionPackage questionPackage) {
        gameGUI.questionPanel.setContinueButton();
        System.out.println("Starting new round");
        this.questionPackage = questionPackage;
        this.amountOfQuestions = questionPackage.getAmountOfQuestions();
        getNextQuestion();
    }

    void getNextQuestion() {
        questionPackage.addOneToQuestionNumber();
        if (questionPackage.getQuestionNumber() >= amountOfQuestions){
            getNextRound();
        } else {
            List<String> questionInfo = questionPackage.getQuestionAndAlternatives();
            gameGUI.questionPanel.getQuestion(questionInfo.getFirst(), questionInfo.get(1),
                    questionInfo.get(2), questionInfo.get(3), questionInfo.get(4));
        }
    }

    void getNextRound() {
        if (currentRound == amountOfRounds) {
            requestFinalScore();
        } else {
            currentRound++;
            requestNextRound();
        }
    }

    void requestNextRound() {
        gameGUI.questionPanel.setWaitingButton();
        System.out.println("Sending round score");
        client.sendRequest(new Request(RequestType.ROUND_SCORE, client.username, scoreThisRound));
        scoreThisRound = 0;
    }

    void requestFinalScore() {
        gameOver = true;
        gameGUI.questionPanel.setWaitingButton();
        client.sendRequest(new Request(RequestType.FINAL_RESULT));
        System.out.println("IN FINAL SCORE");
    }

    public void addActionListenerToQuestionButtons(){
        List<JButton> buttons = gameGUI.questionPanel.getAllAnswerButtons();
        for(JButton button: buttons){
            button.addActionListener(e->{
                String answer = button.getText();
                if (questionPackage.getCurrentQuestion().checkAnswer(answer)){
                    button.setBackground(Color.GREEN);
                    scoreThisRound++;
                }
                else{
                    button.setBackground(Color.RED);
                    for(JButton anotherButton :buttons){
                        if (questionPackage.getCurrentQuestion().checkAnswer(anotherButton.getText())){
                            anotherButton.setBackground(Color.GREEN);
                        }
                    }
                }
                gameGUI.questionPanel.disableButtons();
            });
        }
    }

    public void addActionListenerToContinueButton(){
        gameGUI.questionPanel.getContinueButton().addActionListener(e->{
            if (gameOver){
                gameGUI.switchPanel(2);
            } else {
                getNextQuestion();
            }
        });
    }
}
