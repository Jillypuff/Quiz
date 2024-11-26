package client;

import GUI.GameGUI;
import Modules.QuestionPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameManager implements ActionListener {

    int score = 0;
    int amountOfRounds;
    int currentRound = 1;
    int amountOfQuestions;
    int currentQuestion = 1;
    QuestionPackage questionPackage;
    GameGUI gameGUI;
    boolean isActivePlayer;

    public GameManager(int amountOfRounds, boolean isActivePlayer, GameGUI gameGUI) {
        this.amountOfRounds = amountOfRounds;
        this.isActivePlayer = isActivePlayer;
        this.gameGUI = gameGUI;
        addActionListenerToQuestionButtons();
        addActionListenerToNextQuestionButton();
    }

    void startNewRound(QuestionPackage questionPackage) {
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
        // Ber om ny runda
        // Ger bara om båda spelarna requestar ny runda
    }

    void requestFinalScore() {
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

    public void addActionListenerToNextQuestionButton(){
        gameGUI.questionPanel.nextQuestionButton.addActionListener(e->{
            getNextQuestion();

            /*
            if (currentQuestion < questionPackage.getAnswerAlternatives().size()){
                getNextQuestion();
            }
            else{
                gameGUI.switchPanel(waitingpanel);
                //client skickar round finished request?
            }*/
        });
    }

    void setActionListener(){
        gameGUI.questionPanel.addActionListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
