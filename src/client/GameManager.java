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
    int opponentScore = 0;
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
        // Här ska egentligen bara ROUND_FINISHED skickas
        client.sendRequest(new Request(RequestType.NEXT_ROUND, score));
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

    public void addActionListenerToContinueButton(){
        gameGUI.questionPanel.getContinueButton().addActionListener(e->{
            getNextQuestion();
        });
    }

    public int getRoundScore(){
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void setOpponentScore(int opponentScore){
        this.opponentScore = opponentScore;
    }

    void setActionListener(){
        gameGUI.questionPanel.addActionListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
