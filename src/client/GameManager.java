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

public class GameManager implements ActionListener {

    int score = 0;
    int scoreLastRound = 0;
    int totalScore = 0;
    int opponentScoreLastRound = 0;
    int opponentTotalScore = 0;
    int amountOfRounds;
    int currentRound = 1;
    int amountOfQuestions;
    int currentQuestion = 1;
    QuestionPackage questionPackage;
    GameGUI gameGUI;
    Client client;
    boolean isActivePlayer;

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
        client.sendRequest(new Request(RequestType.ROUND_SCORE, client.username, score));
//        client.sendRequest(new Request(RequestType.NEXT_ROUND, score));
        // Ber om ny runda
        // Ger bara om båda spelarna requestar ny runda
    }

    void requestFinalScore() {
        gameGUI.questionPanel.setWaitingButton();
        System.out.println("IN FINAL SCORE");
        // Ge tillbaka poängen och be om sista scoren
        // Ge bara tillbaka när båda spelarna requestar sista scoren
    }

    public void addActionListenerToQuestionButtons(){
        List<JButton> buttons = gameGUI.questionPanel.getAllAnswerButtons();
        for(JButton button: buttons){
            button.addActionListener(e->{
                String answer = button.getText();
                if (questionPackage.getCurrentQuestion().checkAnswer(answer)){
                    button.setBackground(Color.GREEN);
                    score++;
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
            getNextQuestion();
        });
    }

    public void setScoreLastRound(int scoreLastRound) {
        this.scoreLastRound = scoreLastRound;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setOpponentScoreLastRound(int opponentScoreLastRound) {
        this.opponentScoreLastRound = opponentScoreLastRound;
    }

    public void setOpponentTotalScore(int opponentTotalScore) {
        this.opponentTotalScore = opponentTotalScore;
    }

    void setActionListener(){
        gameGUI.questionPanel.addActionListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
