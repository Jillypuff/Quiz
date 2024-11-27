package GUI.panels;

import javax.swing.*;
import java.awt.*;

public class UglyScorePanel extends JPanel {
//    private final JLabel playerScoreLabel;
//    private final JLabel opponentScoreLabel;
//    private final JLabel resultLabel;
//    private final JButton continueButton;
//
//    public UglyScorePanel() {
//        // Set layout and panel properties
//        setLayout(new GridBagLayout());
//        setBackground(Color.LIGHT_GRAY);
//
//        // Initialize components
//        playerScoreLabel = new JLabel("Your Score: 0");
//        opponentScoreLabel = new JLabel("Opponent's Score: 0");
//        resultLabel = new JLabel("");
//        continueButton = new JButton("Continue");
//
//        // Styling labels and button
//        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        opponentScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        resultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
//        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        continueButton.setFont(new Font("Arial", Font.BOLD, 14));
//
//        // Use GridBagLayout for proper alignment
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//
//        add(playerScoreLabel, gbc);
//
//        gbc.gridy = 1;
//        add(opponentScoreLabel, gbc);
//
//        gbc.gridy = 2;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        add(resultLabel, gbc);
//
//        gbc.gridy = 3;
//        gbc.fill = GridBagConstraints.NONE;
//        add(continueButton, gbc);
//    }
//
//    public void setScoreDisplay(int playerScore, int opponentScore, boolean gameOver) {
//        playerScoreLabel.setText("Your Score: " + playerScore);
//        opponentScoreLabel.setText("Opponent's Score: " + opponentScore);
//        if (playerScore > opponentScore){
//            String resultText = (gameOver) ? "YOU WON THE GAME!" : "You won this round!";
//            resultLabel.setText(resultText);
//        }
//        else if (opponentScore > playerScore){
//            String resultText = (gameOver) ? "YOU LOST" : "You lost this round.";
//            resultLabel.setText(resultText);
//        }
//        else{
//            String resultText = (gameOver) ? "Game ended on a draw" : "It was a draw.";
//            resultLabel.setText(resultText);
//        }
//        String buttonText = (gameOver) ? "EXIT GAME" : "CONTINUE";
//        continueButton.setText(buttonText);
//    }
//
//
//    // Method to update the player score
//    public void setPlayerScore(int score) {
//        playerScoreLabel.setText("Your Score: " + score);
//    }
//
//    // Method to update the opponent score
//    public void setOpponentScore(int score) {
//        opponentScoreLabel.setText("Opponent's Score: " + score);
//    }
//
//    // Method to set the result text
//    public void setResultText(String resultText) {
//        resultLabel.setText(resultText);
//    }
//
//    public JButton getContinueButton() {
//        return continueButton;
//    }





    private final JLabel playerNameLabel;
    private final JLabel playerScoreLabel;
    private final JLabel playerTotalScoreLabel;
    private final JLabel opponentNameLabel;
    private final JLabel opponentScoreLabel;
    private final JLabel opponentTotalScoreLabel;
    private final JLabel resultLabel;
    private final JButton continueButton;

    public UglyScorePanel() {
        // Set layout and panel properties
        setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        // Initialize components
        playerNameLabel = new JLabel();
        playerScoreLabel = new JLabel("Your Score: 0");
        playerTotalScoreLabel = new JLabel("Your Total Score: 0");
        opponentNameLabel = new JLabel();
        opponentScoreLabel = new JLabel("Opponent's Score: 0");
        opponentTotalScoreLabel = new JLabel("Opponent's Total Score: 0");
        resultLabel = new JLabel("");
        continueButton = new JButton("Continue");

        // Styling labels and button
        Font nameFont = new Font("Arial", Font.BOLD, 16);
        playerNameLabel.setFont(nameFont);
        opponentNameLabel.setFont(nameFont);

        playerScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerTotalScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        opponentScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        opponentTotalScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Use GridBagLayout for proper alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add player name and score
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(playerNameLabel, gbc);

        gbc.gridy = 1;
        add(playerScoreLabel, gbc);

        gbc.gridx = 2;
        add(playerTotalScoreLabel, gbc);

        // Add opponent name and score
        gbc.gridy = 3;
        add(opponentNameLabel, gbc);

        gbc.gridy = 4;
        add(opponentScoreLabel, gbc);

        gbc.gridy = 5;
        add(opponentScoreLabel, gbc);

        // Add result label
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(resultLabel, gbc);

        // Add continue button
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        add(continueButton, gbc);
    }

    public void setScoreDisplay(int playerScoreLastRound, int playerTotalScore, int opponentScoreLastRound, int opponentTotalScore, boolean gameOver) {
        playerScoreLabel.setText("Your Score Last Round: " + playerScoreLastRound);
        opponentScoreLabel.setText("Opponent's Score Last Round: " + opponentScoreLastRound);
        String resultText;
        if(gameOver) {
            if(playerTotalScore>opponentTotalScore) {
                resultText = "YOU WON THE GAME!";
            }
            else if (playerTotalScore<opponentTotalScore) {
                resultText = "YOU LOST!";
            }
            else {
                resultText = "Game ended on a draw";
            }
        }
        else {
            if (playerScoreLastRound>opponentScoreLastRound) {
                resultText = "You won this round!";
            }
            else if (playerScoreLastRound<opponentScoreLastRound) {
                resultText = "You lost this round!";
            }
            else {
                resultText = "It was a draw.";
            }
        }
//        if (playerScore > opponentScore){
//            String resultText = (gameOver) ? "YOU WON THE GAME!" : "You won this round!";
//            resultLabel.setText(resultText);
//        }
//        else if (opponentScore > playerScore){
//            String resultText = (gameOver) ? "YOU LOST" : "You lost this round.";
//            resultLabel.setText(resultText);
//        }
//        else{
//            String resultText = (gameOver) ? "Game ended on a draw" : "It was a draw.";
//            resultLabel.setText(resultText);
//        }
        resultLabel.setText(resultText);
        String buttonText = (gameOver) ? "EXIT GAME" : "CONTINUE";
        continueButton.setText(buttonText);
    }

    // Method to update the player score
    public void setPlayerScore(int score) {
        playerScoreLabel.setText("Your Score: " + score);
    }

    // Method to update the opponent score
    public void setOpponentScore(int score) {
        opponentScoreLabel.setText("Opponent's Score: " + score);
    }

    // Method to set the result text
    public void setResultText(String resultText) {
        resultLabel.setText(resultText);
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public void setPlayerNames(String playerName, String opponentName){
        playerNameLabel.setText(playerName);
        opponentNameLabel.setText(opponentName);
    }



}
