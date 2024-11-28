package client;

import GUI.GameGUI;
import Modules.QuestionPackage;
import Modules.Request;
import Modules.RequestType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class GameManager {

    private int scoreThisRound = 0;
    private final int amountOfRounds;
    private int currentRound = 1;
    private int amountOfQuestions;
    private QuestionPackage questionPackage;
    private final GameGUI gameGUI;
    private final Client client;
    private boolean isActivePlayer;

    public GameManager(int amountOfRounds, boolean isActivePlayer, Client client) {
        this.amountOfRounds = amountOfRounds;
        this.isActivePlayer = isActivePlayer;
        this.client = client;
        this.gameGUI = client.getGameGUI();
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
        client.sendRequest(new Request(RequestType.ROUND_SCORE, client.getUsername(), scoreThisRound));
        scoreThisRound = 0;
    }

    void requestFinalScore() {
        gameGUI.questionPanel.setWaitingButton();
        client.sendRequest(new Request(RequestType.FINAL_RESULT, client.getUsername(), scoreThisRound));
        System.out.println("IN FINAL SCORE");
        questionPackage.resetPackage();
    }

    public void addActionListenerToQuestionButtons(){
        List<JButton> buttons = gameGUI.questionPanel.getAllAnswerButtons();
        for(JButton button: buttons){
            resetActionListeners(button);
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
        resetActionListeners(gameGUI.questionPanel.getContinueButton());
        gameGUI.questionPanel.getContinueButton().addActionListener(e->{
            getNextQuestion();
        });
    }

    public void resetActionListeners(JButton button){
        for(ActionListener listener: button.getActionListeners()){
            button.removeActionListener((listener));
        }
    }
}
