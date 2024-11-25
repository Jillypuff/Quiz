package client;

import GUI.GameGUI;
import Modules.QuestionPackage;

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
    }

    void startNewRound(QuestionPackage questionPackage) {
        this.questionPackage = questionPackage;
        this.amountOfQuestions = questionPackage.getAmountOfQuestions();
        getNextQuestion();
    }

    void getNextQuestion() {
        if (questionPackage.getCurrentQuestion() >= amountOfQuestions){
            getNextRound();
        } else {
            List<String> questionInfo = questionPackage.getQuestionAndAlternatives();
            gameGUI.questionPanel.getQuestion(questionInfo.getFirst(), questionInfo.get(1),
                    questionInfo.get(2), questionInfo.get(4), questionInfo.get(5));
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
    }

    void requestFinalScore() {
        // Ge tillbaka poängen och be om sista scoren
    }

    void setActionListener(){
        gameGUI.questionPanel.addActionListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
